/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples;

import java.io.PrintStream;

import apg.*;
import apg.Parser.*;
import examples.Utils.Timer;
/**
 * This abstract class is the base class for all examples. 
 * It provides many functions for standardizing the tests and their output format. 
 */
public abstract class RunTests {
	protected RunInput     cfgIn            = null;
	protected RunInput     udtIn            = null;
	protected int          reps             = 1000;
	protected int          inputStringCount = 0;
	protected String[]     inputStrings     = null;
	protected PrintStream  out              = System.out;
	protected String       title            = null;
	protected String       testName         = null;
	protected String       testAbstract     = null;
	protected Grammar      grammar          = null;

	// each test must override this function
	protected abstract void run() throws Exception;

	// constructor
	protected RunTests(String testName, int reps, PrintStream out){
		this.testName = testName;
		this.reps = reps;
		this.out = out;
	}

	protected RunTests(String testName, PrintStream out){
		this.testName = testName;
		this.reps = 0;
		this.out = out;
	}

	protected static class RunInput{
		public Parser parser;
		protected String title;
		protected int startRule;
		public RunInput(String title, Parser parser, int startRule) throws Exception{
			if(parser == null){throw new Exception("RunInput: parser may not be null");}
			if(startRule < 0){throw new Exception("RunInput: start rule id may not be < 0");}
			if(title == null){this.title = "<no title>";}
			else if(title.length() > 70){this.title = title.substring(67)+"...";}
			else{this.title = title;}
			this.parser = parser;
			this.startRule = startRule;
			this.parser.setStartRule(this.startRule);
		}
	}
	
	protected void runComparisionOfCfgAndUdt() throws Exception{
		// display the input string
		outputInputStrings();
		int index = 0;
		for(String str : inputStrings){
			out.println("["+index+"] "+Utilities.charArrayToXml(str.toCharArray(), 0, str.length()));
			index++;
		}
		out.println();

		// test all input strings
		outputCfgTestStrings();
		boolean test = true;
		if(!(testStrings(cfgIn, inputStrings, true))){test = false;}
		out.println();
		outputUdtTestStrings();
		if(!(testStrings(udtIn, inputStrings, true))){test = false;}
		out.println();
		if(test){
			// compare cfg and udt parser timing
			outputTimeComparison();
			compareTimesAllStrings(cfgIn, udtIn, inputStrings, reps);
			out.println();
			
			// compare cfg and udt node statistics
			compareAllNodeStats(cfgIn, udtIn, inputStrings);
		}
	}
	
	protected void compareTimesAllStrings(RunInput lhsIn,
			RunInput rhsIn, String[] strings, int reps) throws Exception{
		double avg = 0.0;
		String  format = "%6d: %9d %9d %9.4g\n";
		String formatS = "string: %9s %9s %9s\n";
		
		// make sure stats, trace and ast are disabled for timing test
		lhsIn.parser.enableAst(false);
		lhsIn.parser.enableTrace(false);
		lhsIn.parser.enableStatistics(false);
		rhsIn.parser.enableAst(false);
		rhsIn.parser.enableTrace(false);
		rhsIn.parser.enableStatistics(false);
		
		TimeCompare[] times = new TimeCompare[strings.length];
		for(int i = 0; i < strings.length; i++){
			times[i] = new TimeCompare();
		}
		out.print(String.format(formatS, "cfg", "udt", "cfg/udt"));
		for(int i = 0; i < strings.length; i++){
			compareTimes(cfgIn, udtIn, strings[i], reps, times[i]);
			avg += times[i].fraction;
			out.print(String.format(format, i, times[i].lhs, times[i].rhs, times[i].fraction));
		}
		avg = avg/(double)strings.length;
		out.print(String.format("                   average: %9.4g\n", avg));
	}

	protected void compareAllNodeStats(RunInput lhsIn, RunInput rhsIn, String[] strings) throws Exception{
		Result result;
		Statistics lhsStats, rhsStats;
		boolean ok = true;
		
		// lhs
		lhsStats = lhsIn.parser.enableStatistics(true);
		lhsStats.enableCumulate(true);
		for(String string : strings){
			lhsIn.parser.setInputString(string.toCharArray());
			result = lhsIn.parser.parse();
			if(!result.success()){
				out.print(lhsIn.title + ": parser failed: string: "+string);
				out.print("\n");
				ok = false;
			}
		}
		lhsIn.parser.enableStatistics(false);
		
		// rhs
		rhsStats = rhsIn.parser.enableStatistics(true);
		rhsStats.enableCumulate(true);
		for(String string : strings){
			rhsIn.parser.setInputString(string.toCharArray());
			result = rhsIn.parser.parse();
			if(!result.success()){
				out.print(rhsIn.title + ": parser failed: string: "+string);
				out.print("\n");
				ok = false;
			}
		}

		if(ok){
			outputNodeHits();
			String format = "%8s: %9d %9d %9.4g\n";
			out.print(String.format("%9s %9s %9s %9s\n", " ", "cfg", "udt", "cfg/udt"));
			int lhs = lhsStats.getHits("total");
			int rhs = rhsStats.getHits("total");
			double fraction = rhs != 0 ? (double)lhs/(double)rhs : 0.0;
			out.print(String.format(format, "total", lhs, rhs, fraction));
			lhs = lhsStats.getHits("match");
			rhs = rhsStats.getHits("match");
			fraction = rhs != 0 ? (double)lhs/(double)rhs : 0.0;
			out.print(String.format(format, "match", lhs, rhs, fraction));
			lhs = lhsStats.getHits("nomatch");
			rhs = rhsStats.getHits("nomatch");
			fraction = rhs != 0 ? (double)lhs/(double)rhs : 0.0;
			out.print(String.format(format, "nomatch", lhs, rhs, fraction));
			lhs = lhsStats.getHits("empty");
			rhs = rhsStats.getHits("empty");
			fraction = rhs != 0 ? (double)lhs/(double)rhs : 0.0;
			out.print(String.format(format, "empty", lhs, rhs, fraction));
			out.println();
			
			outputCfgNodeStats();
			lhsStats.displayStats(out, "operators");
			lhsStats.displayStats(out, "rules");
			lhsStats.displayStats(out, "udts");
			out.println();
			outputUdtNodeStats();
			rhsStats.displayStats(out, "operators");
			rhsStats.displayStats(out, "rules");
			rhsStats.displayStats(out, "udts");
			out.println();
		}
		rhsIn.parser.enableStatistics(false);
	}

	
	protected boolean testStrings(RunInput in, String[] strings, boolean traceOnError) throws Exception{
		boolean ret = true;
		int index;
		char[] input;
		Result result;
		index = 0;
		for(String str : strings){
			input = str.toCharArray();
			in.parser.setInputString(input);
			result = in.parser.parse();
			if(result.success()){out.println("["+index+"] success");}
			else{out.println("["+index+"] *** parse failed");}
			if(traceOnError && !result.success()){
				ret = false;
				Trace trace = in.parser.enableTrace(true);
				trace.enableAllNodes(true);
				in.parser.parse();
				in.parser.enableTrace(false);
			}
			index++;
		}
		return ret;
	}

	// private functions and classes
	private class TimeCompare{
		public long lhs;
		public long rhs;
		public double fraction;
		public void setFraction(long lhs, long rhs){fraction = (rhs == 0) ?  0.0 :(double)lhs/(double)rhs;}
		public TimeCompare(){
			lhs = 0;
			rhs = 0;
			fraction = 0.0;
		}
	}
	
	private String compareTimes(RunInput lhsIn, RunInput rhsIn, String inputString,
		int reps, TimeCompare times) throws Exception{
		StringBuffer buf = new StringBuffer();
		Result result;
		Timer timer = new Timer();
		long lhsTime, rhsTime;
		boolean ok = true;
		
		// lhs
		timer.start();
		lhsIn.parser.setInputString(inputString.toCharArray());
		for(int i = 0; i < reps; i++){
			result = lhsIn.parser.parse();
			if(!result.success()){
				buf.append("\ncompare times:");
				buf.append(lhsIn.title + ": parser failed at rep: "+i);
				buf.append("\n");
				ok = false;
				break;
			}
		}
		timer.stop();
		lhsTime = timer.getDurationMillis();
		
		// rhs
		rhsIn.parser.setInputString(inputString.toCharArray());
		timer.start();
		for(int i = 0; i < reps; i++){
			result = rhsIn.parser.parse();
			if(!result.success()){
				buf.append("\ncompare times:");
				buf.append(rhsIn.title + ": parser failed at rep: "+i);
				buf.append("\n");
				ok = false;
				break;
			}
		}
		timer.stop();
		rhsTime = timer.getDurationMillis();
		if(ok){
			double lhs = (double)lhsTime;
			double rhs = (double)rhsTime;
			double fraction = (rhs == 0) ? 0.0: lhs/rhs;
			buf.append("\ncompare times:");
			buf.append("lhs=" + lhsIn.title);
			buf.append(", ");
			buf.append("rhs=" + rhsIn.title);
			buf.append("\n");
			buf.append("reps: " + reps);
			buf.append("\n");
			String format = "%9.4g %9.4g %9.4g\n";
			buf.append(String.format("%9s %9s %9s\n", "lhs", "rhs", "lhs/rhs"));
			buf.append(String.format(format, lhs, rhs, fraction));
			if(times != null){
				times.lhs = lhsTime;
				times.rhs = rhsTime;
				times.setFraction(lhsTime, rhsTime);
			}
		}
		return buf.toString();
	}
	protected void outputTestName(){out.println("TEST NAME: "+testName);}
	protected void outputAbstract(String text){out.println(" ABSTRACT: "+text);}
	protected void outputCfgGrammar(){out.println("CFG GRAMMAR: ("+testName+")");}
	protected void outputUdtGrammar(){out.println("UDT GRAMMAR: ("+testName+")");}
	protected void outputInputStrings(){out.println("INPUT STRINGS: ("+testName+")");}
	protected void outputCfgTestStrings(){out.println("CFG PARSER: TEST ALL STRINGS: ("+testName+")");}
	protected void outputUdtTestStrings(){out.println("UDT PARSER: TEST ALL STRINGS: ("+testName+")");}
	protected void outputTimeComparison(){out.println("TIME COMPARISON: ("+testName+", reps="+reps+")");}
	protected void outputNodeHits(){out.println("NODE HIT COMPARISON: ("+testName+", reps="+reps+")");}
	protected void outputCfgNodeStats(){out.println("CFG: NODE STATISTICS: ("+testName+",reps="+reps+")");}
	protected void outputUdtNodeStats(){out.println("UDT: NODE STATISTICS: ("+testName+", reps="+reps+")");}
	protected void outputAst(boolean xml){
		if(xml){out.println("AST IN XML FORMAT: ("+testName+")");}
		else{out.println("AST IN APG FORMAT: ("+testName+")");}
	}
	protected void outputTrace(boolean xml){
		if(xml){out.println("PARSE FAILED: TRACE IN XML FORMAT: ("+testName+")");}
		else{out.println("PARSE FAILED: TRACE IN APG FORMAT: ("+testName+")");}
	}
}
//protected RunInput     cfgIn            = null;
//protected RunInput     udtIn            = null;
//protected int          reps             = 1000;
//protected int          inputStringCount = 0;
//protected String[]     inputStrings     = null;
//protected PrintStream  out              = System.out;
//protected String       title            = "title";
//protected String[]     testParams       = null;
//protected class RunInput{
//	public Parser parser;
//	protected String title;
//	protected int startRule;
//	public RunInput(String title, Parser parser, int startRule) throws Exception{
//		if(parser == null){throw new Exception("RunInput: parser may not be null");}
//		if(startRule < 0){throw new Exception("RunInput: start rule id may not be < 0");}
//		if(title == null){this.title = "<no title>";}
//		else if(title.length() > 70){this.title = title.substring(67)+"...";}
//		else{this.title = title;}
//		this.parser = parser;
//		this.startRule = startRule;
//		this.parser.setStartRule(this.startRule);
//	}
//}
//
//protected boolean hasHelp(Flag[] helpFlags){
//	boolean ret = false;
//	for(Flag f : helpFlags){
//		if(f.getValue()){
//			ret = true;
//			break;
//		}
//	}
//	return ret;
//}

//protected void getCommandlineParameters(String[] args) throws Exception{
//	getCommandlineParameters(args, true);
//}
//
//protected void getCommandlineParameters(String[] args, boolean removeTrailingNewlines) throws Exception{
//	CommandLineParser cl = null;
//	final int REPETITIONS = 0;
//	final int INPUT_STRING = 1;
//	final int INPUT_FILE = 2;
//	final int SYS_OUT = 3;
//	final int PARAM_COUNT = 4;
//	Parameter[] params;
//	Flag[] flags;
//	String cmd;
//	String input = null;
//	
//		
//	// set the command for this example
//	cmd = "java -jar examples.jar";
//	
//	// set the valid command line flags
//	flags = CommandLineParser.getDefaultHelpFlags();
//	
//	// set the valid command line parameters
//	params = new Parameter[PARAM_COUNT];
//	params[REPETITIONS] = new Parameter("REPETITIONS", "/reps=",
//			"number of times to repeat the parse of the input string", "1000");
//	params[INPUT_STRING] = new Parameter("INPUT_STRING", "/inString=",
//			"the input string", "");
//	params[INPUT_FILE] = new Parameter("INPUT_FILE", "/inFile=",
//			"the input string file name (overrides the inputString)", null);
//	params[SYS_OUT] = new Parameter("SYS_OUT", "/out=",
//			"the output file name (overrides the inputString)", null);
//	
//	// parse the command line
//	cl = new CommandLineParser(cmd, flags, params);
//	if(!cl.parse(args)){throw new Exception("command line error detected");} 
//	if(hasHelp(flags)){{throw new Exception("help screen requested");}}
//	
//	// get the reps
//	String repsStr = params[REPETITIONS].getValue();
//	try{reps = Integer.parseInt(repsStr);}
//	catch(Exception x){throw new Exception("repetitions: \""+repsStr+"\" must be a positive or zero integer");}
//	if(reps < 0){throw new Exception("reps: "+Integer.parseInt(params[REPETITIONS].getValue())+" must be >= 0");}
//	// get the input string
//	String fileName = params[INPUT_FILE].getValue();
//	if(fileName != null && !fileName.equals("")){
//		input = Utilities.getFileAsString(fileName);
//		if(input.length() > 0 && removeTrailingNewlines){input = Utils.trimTrailingNewlines(input);}
//	} else{
//		input = params[INPUT_STRING].getValue();
//	}
//	inputStringCount = 1;
//	inputStrings = new String[inputStringCount];
//	inputStrings[0] = input;
//}
//protected void testAst(RunInput in, int[] ruleIds, int[] udtIds, String string) throws Exception{
//in.parser.setInputString(string);
//Ast ast = in.parser.enableAst(true);
//if(ruleIds != null && ruleIds.length > 0){
//	for(int id : ruleIds){
//		ast.enableRuleNode(id, true);
//	}
//}
//if(udtIds != null && udtIds.length > 0){
//	for(int id : udtIds){
//		ast.enableUdtNode(id, true);
//	}
//}
//in.parser.parse();
//
//// display native AST
//out.println("\n*** "+title+" AST(native format )***");
//ast.display(out);
//
//// display XML AST
//out.println("\n*** "+title+" AST(XML format) ***");
//ast.display(out, true);
//
//// clean up parser
//ast = null;
//in.parser.enableAst(false);
//}

//protected void testTrace(RunInput in, String string) throws Exception{
//in.parser.setInputString(string);
//
//// configure the trace
//Trace trace = in.parser.enableTrace(true);
//trace.setOut(out);
//trace.enableAllNodes(true);
//
//// trace with native display
//out.println("\n*** TRACE, native format ***");
//trace.enableXml(false);
//in.parser.parse();
//
//// trace with XML display
//out.println("\n*** TRACE, XML format ***");
//trace.enableXml(true);
//in.parser.setInputString(string);
//in.parser.parse();
//
//// clean up parser
//trace = null;
//in.parser.enableTrace(false);
//}

//protected void compareNodeStats(RunInput lhsIn, RunInput rhsIn, String inputString) throws Exception{
//String[] strings = {inputString};
//compareAllNodeStats(lhsIn, rhsIn, strings);
//}

//protected boolean testString(RunInput in, String string, boolean traceOnError) throws Exception{
//String[] strings = new String[1];
//strings[0] = string;
//return testStrings(in, strings, traceOnError);
//}
//protected boolean testString(RunInput in, String string) throws Exception{
//String[] strings = new String[1];
//strings[0] = string;
//return testStrings(in, strings, false);
//}
//protected boolean testStrings(RunInput in, String[] strings) throws Exception{
//return testStrings(in, strings, false);
//}
