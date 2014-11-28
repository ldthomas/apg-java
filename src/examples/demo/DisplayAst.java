package examples.demo;

import java.io.PrintStream;
import apg.Ast;
import apg.Parser;
import apg.Parser.Result;
import apg.Trace;
import apg.UdtLib;
import apg.Utilities;
import examples.RunTests;

/** A simple demonstration of how to generate and display an Abstract Syntax Tree (AST).
The source code should be consulted for details.
 */
public class DisplayAst extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param out the output device
	 */
	public DisplayAst(String testName, PrintStream out){super(testName, out);}

	@Override protected void run() throws Exception{
		// display the title and an abstract
		if(testName != null){outputTestName();}
		else{throw new Exception("testName may not be null");}
		outputAbstract("Use the Expressions grammar to " +
				"demonstrate the generation and display the AST.");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		Expressions.display(out);
		out.println();
		outputUdtGrammar();
		UExpressions.display(out);
		out.println();

		// display the input string
		String inputString = "(a+b)*(c+(d+e*(topA+topB)))";
		out.println("INPUT STRING: ("+testName+")");
		out.println(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
		out.println();
		
		// create a parser from the UDT grammar
		Parser parser = new Parser(UExpressions.getInstance());
		
		// tell the parser what the start rule is
		parser.setStartRule(UExpressions.RuleNames.E.ruleID());

		// tell the parser what the input string is
		parser.setInputString(inputString);
		
		// tell the parser to use Alphanum from UdtLib as the "u_id" UDT function 
		UdtLib.Alphanum idCallback = new UdtLib.Alphanum(parser);
		parser.setUdtCallback(UExpressions.UdtNames.U_ID.udtID(), idCallback);
		
		// tell the parser to generate an AST
		Ast ast = parser.enableAst(true);
		
		// tell the AST which rule name nodes to keep
		ast.enableRuleNode(UExpressions.RuleNames.E.ruleID(), true);
		ast.enableRuleNode(UExpressions.RuleNames.T.ruleID(), true);
		ast.enableRuleNode(UExpressions.RuleNames.F.ruleID(), true);
		ast.enableRuleNode(UExpressions.RuleNames.EPRIME.ruleID(), true);
		ast.enableRuleNode(UExpressions.RuleNames.TPRIME.ruleID(), true);
		
		// tell the AST which UDT nodes to keep
		ast.enableUdtNode(UExpressions.UdtNames.U_ID.udtID(), true);
		
		// parse the input string
		Result result = parser.parse();
		
		// handle the parser result
		if(result.success()){
			// display the AST in APG format
			outputAst(false);
			ast.display(out);

			// display the AST in XML format
			out.println();
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
}
