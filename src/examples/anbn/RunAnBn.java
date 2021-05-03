/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.anbn;

import apg.Parser;
import examples.RunTests;

import java.io.PrintStream;

/** Uses the grammar for the strings a<sup>n</sup>b<sup>n</sup>, n &gt; 0, for
a comparison of timing and node hits between the normal 
CFG grammar and the use of UDT functions.
 */
public class RunAnBn extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunAnBn(String testName, int reps, PrintStream out){
		super(testName, reps, out);
	}
	
	@Override protected void run() throws Exception{
		setupForCompare();
		runComparisionOfCfgAndUdt();
	}

	// Sets up the parser, input strings, CFG and UDT grammars for the 
	// time and node statistics comparisons. 
	private void setupForCompare() throws Exception{
		// display the title and an abstract
		if(testName != null){outputTestName();}
		else{throw new Exception("testName may not be null");}
		outputAbstract("CFG/UDT time & statistics comparison for the a^nb^n grammar");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		AnBn.display(out);
		out.println();
		outputUdtGrammar();
		UAnBn.display(out);
		out.println();

		// set up input strings
		inputStringCount = 10;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "ab";
		inputStrings[1] = "aabb";
		inputStrings[2] = "aaabbb";
		inputStrings[3] = "aaaabbbb";
		inputStrings[4] = "aaaaabbbbb";
		inputStrings[5] = "aaaaaabbbbbb";
		inputStrings[6] = "aaaaaaabbbbbbb";
		inputStrings[7] = "aaaaaaaabbbbbbbb";
		inputStrings[8] = "aaaaaaaaabbbbbbbbb";
		inputStrings[9] = "aaaaaaaaaabbbbbbbbbb";

		// CFG grammar-dependent setup
		int startRule = AnBn.RuleNames.ANBN.ruleID(); // grammar.RuleNames.RULE.ruldID();
		cfgIn = new RunInput("cfg", new Parser(AnBn.getInstance()), startRule);

		// UDT grammar-dependent setup
		Parser parser = new Parser(UAnBn.getInstance());
		startRule = UAnBn.RuleNames.ANBN.ruleID();
		parser.setUdtCallback(UAnBn.UdtNames.U_ANBN.udtID(), new HandwrittenAnBn(parser));
		udtIn = new RunInput("udt", parser, startRule);
	}
}
//private void runDemo() throws Exception{
//// print the test title and an abstract of what it does
//title = "***    Title: "+testName;
//out.println(title);
//out.println("*** Abstract: Using the a^nb^n grammar, " +
//		"to demonstrate how to write and use AST callback functions.");
//
//// display the grammars
//out.println("*** Display the grammars used.");
//out.println("\nthe AnBn CFG grammar:");
//AnBn.display(out);
//out.println("\nthe AnBn UDT grammar:");
//UAnBn.display(out);
//out.println();
//
//// get the UDT parser
//Parser parser = new Parser(UAnBn.getInstance());
//
//// set up & display the input string
//inputStringCount = 1;
//inputStrings = new String[1];
//inputStrings[0] = "aaabbb";
//out.println("*** Display the input string used.");
//out.print("input string: \"");
//out.print(Utilities.charArrayToXml(inputStrings[0].toCharArray(),
//		0, inputStrings[0].length()));
//out.println("\"");
//
//// set the parser's input string
//parser.setInputString(inputStrings[0]);
//
//// set the parser's start rule
//int startRule = AnBn.RuleNames.ANBN.ruleID();
//parser.setStartRule(startRule);
//
//// set the parser's UDT callback function
//parser.setUdtCallback(UAnBn.UdtNames.U_ANBN.udtID(), new HandwrittenAnBn(parser));
//
//// set up the parser to generate an AST
//Ast ast = parser.enableAst(true);
//
//// tell the AST which rule/UDT nodes to keep on the tree
//ast.enableRuleNode(startRule, true);
//ast.enableUdtNode(UAnBn.UdtNames.U_ANBN.udtID(), true);
//
//// parse the input string
//Result result = parser.parse();
//if(result.success()){
//	// the parse succeeded: display the AST in native format
//	out.println();
//	out.println("*** Display the AST in native APG format.");
//	ast.display(out);
//
//	// display the AST in XML format
//	out.println();
//	out.println("*** Display the AST in XML format.");
//	ast.display(out, true);
//} else{
//	// the parse failed: re-parse with a default trace enabled 
//	Trace trace = parser.enableTrace(true);
//	
//	// display the trace in native format
//	out.println();
//	out.println("*** The parser failed.");
//	out.println("*** Display the trace in native APG format.");
//	parser.parse();
//
//	// display the trace in XML format
//	out.println();
//	out.println("*** Display the trace in XML format.");
//	trace.enableXml(true);
//	parser.parse();
//}
//}
