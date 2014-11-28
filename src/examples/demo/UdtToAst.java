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
 * This example demonstrates how to add nodes to the AST from a UDT.
 * The source code should be consulted for details.
 */
public class UdtToAst extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param out the output device
	 */
	public UdtToAst(String testName, PrintStream out){super(testName, out);}

	@Override protected void run() throws Exception{
		// display the title and an abstract
		if(testName != null){outputTestName();}
		else{throw new Exception("testName may not be null");}
		outputAbstract("Demonstrate with the IPv4 grammar how a UDT can add nodes to the AST.");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		IPv4.display(out);
		out.println();
		outputUdtGrammar();
		UIPv4.display(out);
		out.println();

		// display the input string
		String inputString = "123.12.012.000";
		out.println("INPUT STRING: ("+testName+")");
		out.println(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
		out.println();
		
		// create a parser from the UDT grammar
		Parser parser = new Parser(UIPv4.getInstance());
		
		// tell the parser what the start rule is
		int startRule = UIPv4.RuleNames.IPV4.ruleID();
		int byteRule = UIPv4.RuleNames.BYTE.ruleID();
		parser.setStartRule(startRule);

		// tell the parser what the input string is
		parser.setInputString(inputString);
		
		// tell the parser to use the hand-written UDTIPv4 UDT
		int udtId = UIPv4.UdtNames.U_IPV4.udtID();
		int ruleByteId = UIPv4.RuleNames.BYTE.ruleID();
		UdtCallback udtCallback = new UDTIPv4(parser, ruleByteId);
		parser.setUdtCallback(udtId, udtCallback);
		
		// tell the parser to generate an AST
		Ast ast = parser.enableAst(true);
		
		// tell the AST which rule name nodes to keep
		ast.enableRuleNode(startRule, true);
		ast.enableRuleNode(byteRule, true);
		ast.enableUdtNode(udtId, true);
		
		// tell the AST which UDT nodes to keep
		ast.enableUdtNode(UExpressions.UdtNames.U_ID.udtID(), true);
		
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
	class UDTIPv4 extends UdtCallback{
		private final int ruleByteId;
		UDTIPv4(Parser parser, int ruleByteId){
			super(parser);
			this.ruleByteId = ruleByteId;
		}
		@Override public int callback(int offset) throws Exception{
			int i = offset;
			char[] in = callbackData.inputString;
			int inlen = callbackData.inputString.length;
			int len = 0;
			int[] byteOffset = new int[4];
			int[] byteLength = new int[4];
			int beg, end, c;
			boolean success = true;
			for(int j = 0; j < 4; j++){
				// find the phrase offset and length for each byte integer in the address
				for(beg = i, end = 0 ; i < inlen; i++, end++){
					c = (int)in[i];
					if(c >= 48 && c <= 57){
					} else{break;}
				}
				if(end >= 1 && end <= 3){ // 3-digit constraint
					len += end;
					byteOffset[j] = beg;
					byteLength[j] = end;
					// NOTE: could add a check here for the specification
					//       that byte value must be less than 256
					if(j < 3){
						// check for trailing period
						if(in[i] == '.'){
							i++;
							len++;
						}
						else{
							success = false;
							break;
						}
					}
				} else{
					success = false;
					break;
				}
				if(!success){break;}
			}
			if(success){
				// put the four byte integer phrases on the AST
				for(int j = 0; j < 4; j++){
					parser.addAstRuleNode(ruleByteId, byteOffset[j], byteLength[j], true);
				}
				
			} else{len = -1;}
			return len;
		}
	}
}
