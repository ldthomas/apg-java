/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.demo;

import java.io.PrintStream;
import apg.Ast;
import apg.Parser;
import apg.Parser.Result;
import apg.Parser.UdtCallback;
import apg.Trace;
import apg.Utilities;
import examples.RunTests;

/**
 * This is a demonstration of how to use a UDT as a specialized 
 * alternate (ALT) operation. 
 * Despite the name, User Defined Terminal (UDT), UDTs can be used as non-terminal operators
 * as well. See the source code for details.
 */
public class UdtAsAlt extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param out the output device
	 */
	public UdtAsAlt(String testName, PrintStream out){super(testName, out);}

	@Override protected void run() throws Exception{
		// display the title and an abstract
		if(testName != null){outputTestName();}
		else{throw new Exception("testName may not be null");}
		outputAbstract("Demonstration of a UDT that performs a specialized ALT operation.");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		Hostname.display(out);
		out.println();
		outputUdtGrammar();
		UHostname.display(out);
		out.println();

		// display the input string
		String inputString = " my.domain.com. ";
		out.println("INPUT STRING: ("+testName+")");
		out.println(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
		out.println();
		
		// create a parser from the UDT grammar
		Parser parser = new Parser(UHostname.getInstance());
		
		// tell the parser what the start rule is
		int startRule = UHostname.RuleNames.CONTEXT.ruleID();
		parser.setStartRule(startRule);

		// tell the parser what the input string is
		parser.setInputString(inputString);
		
		// tell the parser to use the specialized UDT to chose between domainlabel and toplabel 
		int udtId = UHostname.UdtNames.U_UDT_AS_ALT.udtID();
		UdtCallback udtCallback = new UDomainOrTop(parser);
		parser.setUdtCallback(udtId, udtCallback);
		
		// tell the parser to generate an AST
		Ast ast = parser.enableAst(true);

		// tell the parser to pass user data to the UDT callback functions
		int hostnameId = UHostname.RuleNames.HOSTNAME.ruleID();
		int labelId = UHostname.RuleNames.LABEL.ruleID();
		int domainLabelId = UHostname.RuleNames.DOMAINLABEL.ruleID();
		int topLabelId = UHostname.RuleNames.TOPLABEL.ruleID();
		parser.setMyData(new MyData(labelId, domainLabelId, topLabelId));
		
		// tell the AST which rule name nodes to keep
		ast.enableRuleNode(startRule, true);
		ast.enableRuleNode(hostnameId, true);
		ast.enableRuleNode(domainLabelId, true);
		ast.enableRuleNode(topLabelId, true);
		
		// parse the input string
		Result result = parser.parse();
		
		// handle the parser result
		if(result.success()){
			// display the AST in APG format
			outputAst(false);
			ast.display(out);
			out.println();

			// display the AST in XML format
			outputAst(true);
			ast.display(out, true);
			out.println();
		} else{
			Trace trace = parser.enableTrace(true);
			trace.setOut(out);
			
			// display the trace in APG format
			outputTrace(false);
			parser.parse();
			out.println();

			// display the trace in XML format
			outputTrace(true);
			trace.enableXml(true);
			parser.parse();
			out.println();
		}
	}
	class MyData{
		final int domainLabelId; 
		final int topLabelId;
		final int labelId;
		MyData(int labelId, int domainLabelId, int topLabelId){
			this.labelId = labelId;
			this.domainLabelId = domainLabelId;
			this.topLabelId = topLabelId;
		}
	}
	/**
	 * 
	 * This is UDT callback functions as "custom ALT" operator to semantically
	 * choose whether the parsed label is a domainlabel or a toplabel. It is not
	 * always possible to do this syntactically, since a domainlabel which begins
	 * with ALPHA and a toplabel followed by the optional "." are syntactially identical.
	 * The handwritten, custom ALT operator uses the following 3 differences to
	 * distinguish between them.
	 * 1. toplabel must always begin with ALPHA whereas domainlabel may begin with DIGIT
	 * 2. toplabel may not be followed by a period, "."
	 * 3. if toplabel is followed by period, the period must be followed by a space
	 *
	 */
	class UDomainOrTop extends UdtCallback{
		private final int SPACE = (int)' ';
		private final int PERIOD = (int)'.';
		UDomainOrTop(Parser parser){super(parser);}
		@Override public int callback(int offset) throws Exception{
			int i = offset;
			char[] in = callbackData.inputString;
			int inlen = callbackData.inputString.length;
			int len = 0;
			boolean topLabel = false;
			
			// get the user-defined data set in the parser
			MyData myData = (MyData)callbackData.myData;
			if(myData == null){throw new Exception(
				"RunUdtAsAlt: UDomainOrTop: data.myData may not be null");}
			
			// while not at end of input string, find the next label
			int beg = i;
			int end = 0;
			while(i < inlen){
				// get the next label (beg, end)
				topLabel = false;
				beg = i;
				end = parser.executeRule(myData.labelId, beg);
				if(end <= 0){break;} // not a hostname, let the parse fail
				len += end;
				i += end;
				
				if(i < inlen){
					// get the first & follow chars
					int firstChar = (int)in[beg];
					int followChar = (int)in[beg + end];
					if(followChar == PERIOD){
						len++;
						i++;
						if(firstChar >= 48 && firstChar <= 57){
							// domainlabel, put it on the AST
							parser.addAstRuleNode(myData.domainLabelId, beg, end, true);
							continue;
						} else if(i < inlen && in[i] == SPACE){
								// first char is ALPHA & follow char is space, top label
								topLabel = true;
								break;
						} else{
							// domainlabel, put it on the AST
							parser.addAstRuleNode(myData.domainLabelId, beg, end, true);
							continue;
						}
					}
					if(followChar == SPACE){
						if(!(firstChar >= 48 && firstChar <= 57)){
							// top label
							topLabel = true;
							break;
						} else{break;} // not a hostname, let the parse fail
					} else{break;} // not a hostname, let the parse fail
				} else{break;} // not a hostname, let the parse fail
				// should never be here
			}
			if(topLabel){
				// toplabel, put it on the AST
				parser.addAstRuleNode(myData.topLabelId, beg, end, true);
			} else{len = -1;} // parse failed
			return len;
		}
	}
}
