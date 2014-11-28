package examples.expressions;

import java.io.PrintStream;

import apg.Parser;
import apg.UdtLib.*;
import examples.RunTests;

/** Uses the the Expressions grammar (4.2)
from Aho, Lam, Sethi and Ullman, 2nd ed. (the Dragon Book) 
for a comparison of timing and node hits between the normal 
CFG grammar and the use of UDT functions.
 */
public class RunExpressions extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunExpressions(String testName, int reps, PrintStream out){
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
		outputAbstract("CFG/UDT time & statistics comparison for the Expressions grammar");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		Expressions.display(out);
		out.println();
		outputUdtGrammar();
		UExpressions.display(out);
		out.println();

		// set up input strings
		if(inputStrings == null          ||
				inputStrings.length == 0 ||
				inputStrings[0] == null  ||
				inputStrings[0].length() == 0){
			inputStringCount = 5;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "a+b";
			inputStrings[1] = "a+b*c";
			inputStrings[2] = "(a+b)*(c+d)";
			inputStrings[3] = "a+b*c+(LevelOne*(LevelTwoA+LevelTwoB))";
			inputStrings[4] = "(a+b)*(c+(d+e*(topA+topB)))";
		}
		
		// CFG grammar-dependent setup
		int startRule = Expressions.RuleNames.E.ruleID(); // grammar.RuleNames.RULE.ruldID();
		cfgIn = new RunInput("cfg", new Parser(Expressions.getInstance()), startRule);

		// UDT grammar-dependent setup
		Parser udtParser = new Parser(UExpressions.getInstance());
		startRule = UExpressions.RuleNames.E.ruleID();
		
		// UDT callback functions
		udtParser.setUdtCallback(UExpressions.UdtNames.U_ID.udtID(), new Alphanum(udtParser));
		udtIn = new RunInput("udt", udtParser, startRule);
	}
}
//void runDemo() throws Exception{
//title = "*** Expressions, simple demo ***";
//out = System.out;
//
//// display the grammars
//out.println("*** Expressions Grammars ***");
//System.out.println("Display the CFG grammar:");
//Expressions.display(out);
//System.out.println("\nDisplay the UDT grammar:");
//UExpressions.display(out);
//out.println();
//
//// parser setup
//Parser parser = new Parser(UExpressions.getInstance());
//
//// set the input string
//String inputString = "(a+b)*(c+(d+e*(topA+topB)))";
//parser.setInputString(inputString);
//out.println(title);
//out.print("input string: \"");
//out.print(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
//out.println("\"");
//
//// set the start rule
//int startRule = UExpressions.RuleNames.E.ruleID();
//parser.setStartRule(startRule);
//
//// set the UDT callback function
//parser.setUdtCallback(UExpressions.UdtNames.U_ID.udtID(), new Alphanum(parser));
//
//// set up the AST
//Ast ast = parser.enableAst(true);
//ast.enableRuleNode(UExpressions.RuleNames.E.ruleID(), true);
//ast.enableRuleNode(UExpressions.RuleNames.T.ruleID(), true);
//ast.enableRuleNode(UExpressions.RuleNames.F.ruleID(), true);
//ast.enableRuleNode(UExpressions.RuleNames.EPRIME.ruleID(), true);
//ast.enableRuleNode(UExpressions.RuleNames.TPRIME.ruleID(), true);
//ast.enableUdtNode(UExpressions.UdtNames.U_ID.udtID(), true);
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
