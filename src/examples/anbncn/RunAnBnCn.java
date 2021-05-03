/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.anbncn;

import java.io.PrintStream;

import apg.Parser;
import examples.RunTests;

/** Uses the grammar for the strings a<sup>n</sup>b<sup>n</sup>c<sup>n</sup>, n &gt; 0, for
a comparison of timing and node hits between the normal 
CFG grammar and the use of UDT functions.
 */
public class RunAnBnCn extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunAnBnCn(String testName, int reps, PrintStream out){
		super(testName, reps, out);
	}
	
	@Override protected void run() throws Exception{
		setup();
		runComparisionOfCfgAndUdt();
	}
	
	void setup() throws Exception{
		// display the title and an abstract
		if(testName != null){outputTestName();}
		else{throw new Exception("testName may not be null");}
		outputAbstract("CFG/UDT time & statistics comparison for the a^nb^nc^n grammar");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		AnBnCn.display(out);
		out.println();
		outputUdtGrammar();
		UAnBnCn.display(out);
		out.println();

		// set up input strings
		if(inputStrings == null          ||
				inputStrings.length == 0 ||
				inputStrings[0] == null  ||
				inputStrings[0].length() == 0){
			inputStringCount = 10;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "abc";
			inputStrings[1] = "aabbcc";
			inputStrings[2] = "aaabbbccc";
			inputStrings[3] = "aaaabbbbcccc";
			inputStrings[4] = "aaaaabbbbbccccc";
			inputStrings[5] = "aaaaaabbbbbbcccccc";
			inputStrings[6] = "aaaaaaabbbbbbbccccccc";
			inputStrings[7] = "aaaaaaaabbbbbbbbcccccccc";
			inputStrings[8] = "aaaaaaaaabbbbbbbbbccccccccc";
			inputStrings[9] = "aaaaaaaaaabbbbbbbbbbcccccccccc";
		}

		// CFG grammar-dependent setup
		int startRule = AnBnCn.RuleNames.ANBNCN.ruleID(); // grammar.RuleNames.RULE.ruldID();
		cfgIn = new RunInput("cfg", new Parser(AnBnCn.getInstance()), startRule);

		// UDT setup
		Parser parser = new Parser(UAnBnCn.getInstance());
		startRule = UAnBnCn.RuleNames.ANBNCN.ruleID();
		parser.setUdtCallback(UAnBnCn.UdtNames.U_ANBNCN.udtID(), new HandwrittenAnBnCn(parser));
		udtIn = new RunInput("udt", parser, startRule);
	}
}
//void runDemo() throws Exception{
//// print the test title and an abstract of what it does
//title = "*** Test Name: "+testName;
//out.println(title);
//out.println("*** Abstract: Using the a^nb^nc^n grammar, " +
//		"to demonstrate how to write and use AST callback functions.");
//
//// display the grammars
//out.println("\nthe AnBnCn CFG grammar:");
//AnBnCn.display(out);
//out.println("\nthe AnBnCn UDT grammar:");
//UAnBnCn.display(out);
//out.println();
//
//// get the UDT parser
//Parser parser = new Parser(UAnBnCn.getInstance());
//
//// set the input string
//String inputString = "aaabbbccc";
//parser.setInputString(inputString);
//out.println(title);
//out.print("input string: \"");
//out.print(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
//out.println("\"");
//
//// set the start rule
//int startRule = AnBnCn.RuleNames.ANBNCN.ruleID();
//parser.setStartRule(startRule);
//
//// set the UDT callback function
//parser.setUdtCallback(UAnBnCn.UdtNames.U_ANBNCN.udtID(), new HandwrittenAnBnCn(parser));
//
//// set up the AST
//Ast ast = parser.enableAst(true);
//ast.enableRuleNode(startRule, true);
//ast.enableUdtNode(UAnBnCn.UdtNames.U_ANBNCN.udtID(), true);
//
//// parse the input string
//Result result = parser.parse();
//if(result.success()){
//	// display the AST in native format
//	out.println("\ndisplay the AST, native format:");
//	ast.display(out);
//
//	// display the AST in XML format
//	out.println("\ndisplay the AST, XML format:");
//	ast.display(out, true);
//
//} else{
//	Trace trace = parser.enableTrace(true);
//	
//	// display the trace in native format
//	out.println("\nparse failed: display the trace:");
//	out.println("TRACE, native format:");
//	parser.parse();
//
//	// display the trace in XML format
//	out.println("\nTRACE, XML format:");
//	trace.enableXml(true);
//	parser.parse();
//
//}
//}
