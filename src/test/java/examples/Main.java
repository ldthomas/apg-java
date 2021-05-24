/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples;

import java.io.PrintStream;
import apg.Utilities;
import examples.Utils.CommandLineParser;
import examples.Utils.CommandLineParser.Flag;
import examples.Utils.CommandLineParser.Parameter;
import examples.demo.*;
import examples.anbn.RunAnBn;
import examples.anbncn.RunAnBnCn;
import examples.expressions.RunExpressions;
import examples.inifile.RunIniFile;
import examples.mailbox.RunMailbox;
import examples.testudtlib.RunUdtTest;

/**
 * This class provides a static main() function which is the driver for
 * selecting from among the several available tests. Interested persons should
 * look at the example code for details of how to use Java APG parsers for the
 * examples given. Some of the examples provide simple demonstrations of usage.
 * Most of the examples give a comparison between the normal CFG parsers and the
 * same problem using UDT functions for greater efficiency. The comparisons are
 * done with the standardized functions in {@link RunTests RunTests}
 * <table style="border:1px solid black;border-collapse:collapse;float:right;" >
 * <caption>Some typical time factors.</caption>
 * <tr><th  style="border:1px solid black;">Timing Test
 * Name</th><th  style="border:1px solid black;">factor</th></tr>
 * <tr><td  style="border:1px solid black;">anbn</td><td  style="border:1px solid black;">7.754</td></tr>
 * <tr><td  style="border:1px solid black;">anbncn</td><td  style="border:1px solid black;">21.18</td></tr>
 * <tr><td  style="border:1px solid black;">expressions</td><td  style="border:1px solid black;">1.836</td></tr>
 * <tr><td  style="border:1px solid black;">inifile</td><td  style="border:1px solid black;">3.468</td></tr>
 * <tr><td  style="border:1px solid black;">mailbox</td><td  style="border:1px solid black;">4.896</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-alphanum</td><td  style="border:1px solid black;">6.962</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-any</td><td  style="border:1px solid black;">7.861</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-comment-semi</td><td  style="border:1px solid black;">12.90</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-comment-cpp</td><td  style="border:1px solid black;">17.98</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-comment-c</td><td  style="border:1px solid black;">25.15</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-decnum</td><td  style="border:1px solid black;">3.175</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-hexnum</td><td  style="border:1px solid black;">11.49</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-lineend-forgiving</td><td  style="border:1px solid black;">1.897</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-lineend-lf</td><td  style="border:1px solid black;">2.408</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-lineend-crlf</td><td  style="border:1px solid black;">1.096</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-quoted-string</td><td  style="border:1px solid black;">1.398</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-wsp</td><td  style="border:1px solid black;">7.259</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-wsp-comments</td><td  style="border:1px solid black;">10.89</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-wsp-folding</td><td  style="border:1px solid black;">11.44</td></tr>
 * <tr><td  style="border:1px solid black;">udtlib-wsp-folding-comments</td><td  style="border:1px solid black;">10.74</td></tr>
 * </table>
 * <p>
 * A quick comparison of the average time factors for the examples is given in
 * the table to the right. The factor is CFG time / UDT time. Results will vary
 * in different environments. (A factor of 10, for example, means that the CFG
 * time is 10 times greater than the UDT time.)
 * <p>
 * Command line usage of the driver function main() is given below.
 * <pre>
 * command line: java -jar examples.jar [args]
 * args: flags and/or parameters
 *
 * flags
 * ?          if true, print the help screen
 * /?         if true, print the help screen
 * help       if true, print the help screen
 * /help      if true, print the help screen
 * -help      if true, print the help screen
 * --help     if true, print the help screen
 *
 * parameters
 * /reps=     number of times to repeat the parse of the input string (default: 1000000)
 * /test=     the test name (required) (default: null)
 * /out=      file name of the output device(if default uses System.out) (default: null)
 *
 * All flags are false by default. Specifying a flag one or more times sets it to true.
 * Parameters are of the form "arg value" (no space). eg. /test=demo-ast.
 * Parameter values may not be empty.
 * Parameter values containing spaces must be quoted.
 * All command line arguments must be a valid flag or parameter.
 *
 * valid test names:
 * test name                     : description
 * ---------                     : -----------
 * demo-ast                      : demonstration of creating and displaying the AST
 * demo-ast-callback             : demonstration of translating the AST
 * demo-udt-ast                  : demonstration of a UDT adding nodes to the AST
 * demo-udt-alt                  : demonstration of a UDT acting as a specialized ALT operator
 * demo-trace                    : demonstration of displaying the trace
 * anbn                          : timing comparison for a^nb^n grammar
 * anbncn                        : timing comparison for a^nb^nc^n grammar
 * expressions                   : timing comparison for Expressions grammar
 * inifile                       : timing comparison for the "ini" file format
 * mailbox                       : timing comparison for the email address grammar
 * udtlib-alphanum               : timing comparison for UdtLib.Alphanum
 * udtlib-any                    : timing comparison for UdtLib.Any
 * udtlib-comment                : timing comparison for UdtLib.SemiComment
 * udtlib-comment-semi           : timing comparison for UdtLib.SemiComment
 * udtlib-comment-cpp            : timing comparison for UdtLib.CppComment
 * udtlib-comment-c              : timing comparison for UdtLib.CComment
 * udtlib-decnum                 : timing comparison for UdtLib.DecNum
 * udtlib-hexnum                 : timing comparison for UdtLib.HexNum
 * udtlib-lineend                : timing comparison for UdtLib.ForgivingLineEnd
 * udtlib-lineend-forgiving      : timing comparison for UdtLib.ForgivingLineEnd
 * udtlib-lineend-lf             : timing comparison for UdtLib.LFLineEnd
 * udtlib-lineend-crlf           : timing comparison for UdtLib.CRLFLineEnd
 * udtlib-quoted-string          : timing comparison for UdtLib.DoubleQuotedString
 * udtlib-wsp                    : timing comparison for UdtLib.WhiteSpace
 * udtlib-wsp-comments           : timing comparison for UdtLib.WhiteSpace including comments
 * udtlib-wsp-folding            : timing comparison for UdtLib.WhiteSpace including folding white space
 * udtlib-wsp-comments-folding   : timing comparison for UdtLib.WhiteSpace including both folding and comments
 * udtlib-wsp-folding-comments   : timing comparison for UdtLib.WhiteSpace including both folding and comments
 * demos                         : run all demonstrations
 * timing                        : run all timing tests (may take up to 10 or more minutes)
 * all                           : run all tests (may take up to 10 or more minutes)
 * </pre>
 * <i>
 * Disclaimer: These examples should not be considered a part of the Java APG
 * API. Backward compatibility or even the existence of these examples or
 * packages from version to version is not guaranteed.
 * </i>
 */
//</table>
//<table>
//<tr><th>UdtLib Timing Tests</th><th>factor</th></tr>
public class Main {

    public enum Tests {
        DEMO_AST("demo-ast", "demonstration of creating and displaying the AST", 0),
        DEMO_AST_CALLBACK("demo-ast-callback", "demonstration of translating the AST", 0),
        DEMO_UDT_AST("demo-udt-ast", "demonstration of a UDT adding nodes to the AST", 0),
        DEMO_UDT_ALT("demo-udt-alt",
                "demonstration of a UDT acting as a specialized ALT operator", 0),
        DEMO_TRACE("demo-trace", "demonstration of displaying the trace", 0),
        ANBN("anbn", "timing comparison for a^nb^n grammar", 1000000),
        ANBNCN("anbncn", "timing comparison for a^nb^nc^n grammar", 500000),
        EXPRESSIONS("expressions", "timing comparison for Expressions grammar", 50000),
        INIFILE("inifile", "timing comparison for the \"ini\" file format", 50000),
        MAILBOX("mailbox", "timing comparison for the email address grammar", 100000),
        UDTLIB_ALPHANUM("udtlib-alphanum", "timing comparison for UdtLib.Alphanum", 1500000),
        UDTLIB_ANY("udtlib-any", "timing comparison for UdtLib.Any", 1500000),
        UDTLIB_COMMENT("udtlib-comment", "timing comparison for UdtLib.SemiComment", 100000),
        UDTLIB_COMMENT_SEMI("udtlib-comment-semi",
                "timing comparison for UdtLib.SemiComment", 100000),
        UDTLIB_COMMENT_CPP("udtlib-comment-cpp",
                "timing comparison for UdtLib.CppComment", 100000),
        UDTLIB_COMMENT_C("udtlib-comment-c", "timing comparison for UdtLib.CComment", 100000),
        UDTLIB_DECNUM("udtlib-decnum", "timing comparison for UdtLib.DecNum", 1900000),
        UDTLIB_HEXNUM("udtlib-hexnum", "timing comparison for UdtLib.HexNum", 1500000),
        UDTLIB_LINEEND("udtlib-lineend", "timing comparison for UdtLib.ForgivingLineEnd", 2000000),
        UDTLIB_LINEEND_FORGIVING("udtlib-lineend-forgiving",
                "timing comparison for UdtLib.ForgivingLineEnd", 2000000),
        UDTLIB_LINEEND_LF("udtlib-lineend-lf", "timing comparison for UdtLib.LFLineEnd", 2000000),
        UDTLIB_LINEEND_CRLF("udtlib-lineend-crlf",
                "timing comparison for UdtLib.CRLFLineEnd", 2000000),
        UDTLIB_QUOTED_STRING("udtlib-quoted-string",
                "timing comparison for UdtLib.DoubleQuotedString", 1500000),
        UDTLIB_WSP("udtlib-wsp", "timing comparison for UdtLib.WhiteSpace", 1500000),
        UDTLIB_WSP_COMMENTS("udtlib-wsp-comments",
                "timing comparison for UdtLib.WhiteSpace including comments", 1000000),
        UDTLIB_WSP_FOLDING("udtlib-wsp-folding",
                "timing comparison for UdtLib.WhiteSpace including folding white space", 1000000),
        UDTLIB_WSP_CF("udtlib-wsp-comments-folding",
                "timing comparison for UdtLib.WhiteSpace including both folding and comments",
                1000000),
        UDTLIB_WSP_FC("udtlib-wsp-folding-comments",
                "timing comparison for UdtLib.WhiteSpace including both folding and comments",
                1000000),
        ALL("all", "run all tests", 0),
        DEMOS("demos", "run all demonstrations", 0),
        TIMING("timing", "run all timing tests", 0);
        String name;
        String desc;
        int reps;

        Tests(String name, String desc, int reps) {
            this.name = name;
            this.desc = desc;
            this.reps = reps;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return desc;
        }

        public int getReps() {
            return reps;
        }
    }

    private static class TestData {

        String testName = null;
        int reps = 0;
        PrintStream out = System.out;
    }

    /**
     * The driver for test selection. Selects a test from the first command line
     * parameter and runs the test. Catches all Exceptions thrown by the test
     * and displays the Exception message and a stack trace of where it was
     * thrown from.
     *
     * @param args the command line parameters for test selection. Request help
     * (arg=?) to see all command line parameters and valid test names.
     */
    public static void main(String[] args) {
        RunTests theTest = null;
        TestData data = new TestData();
        try {
            getCommandlineParameters(args, data);
            if (data.testName.equalsIgnoreCase(Tests.ALL.getName())) {
                runAll(data);
            } else if (data.testName.equalsIgnoreCase(Tests.DEMOS.getName())) {
                runDemos(data);
            } else if (data.testName.equalsIgnoreCase(Tests.TIMING.getName())) {
                runTiming(data);
            } else {
                theTest = selectTest(data.testName, data.reps, data.out);
                if (theTest == null) {
                    throw new Exception("unrecognized test name \"" + data.testName + "\"");
                }
                theTest.run();
                System.out.println("test complete: " + data.testName);
            }
        } catch (Exception e) {
            System.out.println(Utilities.displayException(e));
            displayOptions(data.out);
        } catch (Error e) {
            System.out.println(Utilities.displayError(e));
            displayOptions(data.out);
        }
    }

    private static void runAll(TestData data) throws Exception {
        RunTests theTest = null;
        for (Tests test : Tests.values()) {
            switch (test) {
                case ALL:
                case DEMOS:
                case TIMING:
                case UDTLIB_COMMENT:
                case UDTLIB_LINEEND:
                case UDTLIB_WSP_FC:
                    break;
                default:
                    theTest = selectTest(test.getName(), test.getReps(), data.out);
                    if (theTest != null) {
                        theTest.run();
                        System.out.println("test complete: " + test.getName());
                    } else {
                        throw new Exception("Main: runAll: test " + test.getName() + " not found");
                    }
                    break;
            }
        }
    }

    private static void runDemos(TestData data) throws Exception {
        RunTests theTest = null;
        for (Tests test : Tests.values()) {
            switch (test) {
                case DEMO_AST:
                case DEMO_AST_CALLBACK:
                case DEMO_UDT_AST:
                case DEMO_UDT_ALT:
                case DEMO_TRACE:
                    theTest = selectTest(test.getName(), test.getReps(), data.out);
                    if (theTest != null) {
                        theTest.run();
                        System.out.println("test complete: " + test.getName());
                    } else {
                        throw new Exception("Main: runAll: test " + test.getName() + " not found");
                    }
                    break;
            }
        }
    }

    private static void runTiming(TestData data) throws Exception {
        RunTests theTest = null;
        for (Tests test : Tests.values()) {
            switch (test) {
                case DEMO_AST:
                case DEMO_AST_CALLBACK:
                case DEMO_UDT_AST:
                case DEMO_UDT_ALT:
                case DEMO_TRACE:
                case UDTLIB_COMMENT:
                case UDTLIB_LINEEND:
                case UDTLIB_WSP_FC:
                case ALL:
                case DEMOS:
                case TIMING:
                    break;
                default:
                    theTest = selectTest(test.getName(), test.getReps(), data.out);
                    if (theTest != null) {
                        theTest.run();
                        System.out.println("test complete: " + test.getName());
                    } else {
                        throw new Exception("Main: runAll: test " + test.getName() + " not found");
                    }
                    break;
            }
        }
    }

    private static RunTests selectTest(String testRequested, int reps, PrintStream out) throws Exception {
        RunTests ret = null;
        if (testRequested.equalsIgnoreCase(Tests.DEMO_AST.getName())) {
            ret = new DisplayAst(Tests.DEMO_AST.getName(), out);
        } else if (testRequested.equalsIgnoreCase(Tests.DEMO_AST_CALLBACK.getName())) {
            ret = new TranslateAst(Tests.DEMO_AST_CALLBACK.getName(), out);
        } else if (testRequested.equalsIgnoreCase(Tests.DEMO_UDT_AST.getName())) {
            ret = new UdtToAst(Tests.DEMO_UDT_AST.getName(), out);
        } else if (testRequested.equalsIgnoreCase(Tests.DEMO_UDT_ALT.getName())) {
            ret = new UdtAsAlt(Tests.DEMO_UDT_ALT.getName(), out);
        } else if (testRequested.equalsIgnoreCase(Tests.DEMO_TRACE.getName())) {
            ret = new DisplayTrace(Tests.DEMO_TRACE.getName(), out);
        }
        if (testRequested.equalsIgnoreCase(Tests.ANBN.getName())) {
            ret = new RunAnBn(Tests.ANBN.getName(), reps, out);
        }
        if (testRequested.equalsIgnoreCase(Tests.ANBNCN.getName())) {
            ret = new RunAnBnCn(Tests.ANBNCN.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.EXPRESSIONS.getName())) {
            ret = new RunExpressions(Tests.EXPRESSIONS.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.INIFILE.getName())) {
            ret = new RunIniFile(Tests.INIFILE.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.MAILBOX.getName())) {
            ret = new RunMailbox(Tests.MAILBOX.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_ALPHANUM.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_ALPHANUM.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_ANY.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_ANY.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_COMMENT.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_COMMENT_SEMI.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_COMMENT_SEMI.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_COMMENT_SEMI.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_COMMENT_CPP.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_COMMENT_CPP.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_COMMENT_C.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_COMMENT_C.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_DECNUM.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_DECNUM.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_DECNUM.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_DECNUM.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_HEXNUM.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_HEXNUM.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_LINEEND.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_LINEEND.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_LINEEND_FORGIVING.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_LINEEND_FORGIVING.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_LINEEND_LF.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_LINEEND_LF.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_LINEEND_CRLF.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_LINEEND_CRLF.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_QUOTED_STRING.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_QUOTED_STRING.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_WSP.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_WSP.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_WSP_COMMENTS.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_WSP_COMMENTS.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_WSP_FOLDING.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_WSP_FOLDING.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_WSP_CF.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_WSP_CF.getName(), reps, out);
        } else if (testRequested.equalsIgnoreCase(Tests.UDTLIB_WSP_FC.getName())) {
            ret = new RunUdtTest(Tests.UDTLIB_WSP_FC.getName(), reps, out);
        }
        return ret;
    }

    private static void displayOptions(PrintStream out) {
        out.println("examples: Main: main(): valid test names");
        out.println();
        out.println(String.format("%-30s: %s", "test name", "description"));
        out.println(String.format("%-30s: %s", "---------", "-----------"));
        for (Tests test : Tests.values()) {
            out.println(String.format("%-30s: %s", test.getName(), test.getDescription()));
        }
    }

    private static void getCommandlineParameters(String[] args, TestData data) throws Exception {
        CommandLineParser cl = null;
        final int REPETITIONS = 0;
        final int TEST_NAME = 1;
        final int OUT = 2;
        final int PARAM_COUNT = 3;
        Parameter[] params;
        Flag[] flags;
        String cmd;

        // set the command for this example
        cmd = "java -jar examples.jar";

        // set the valid command line flags
        flags = CommandLineParser.getDefaultHelpFlags();

        // set the valid command line parameters
        params = new Parameter[PARAM_COUNT];
        params[REPETITIONS] = new Parameter("REPETITIONS", "/reps=",
                "number of times to repeat the parse of the input string", "1000000");
        params[TEST_NAME] = new Parameter("TEST_NAME", "/test=",
                "the test name (required)", null);
        params[OUT] = new Parameter("SYS_OUT", "/out=",
                "file name of the output device(default uses System.out)", null);

        // parse the command line
        cl = new CommandLineParser(cmd, flags, params);
        if (!cl.parse(args)) {
            throw new Exception("command line error detected");
        }
        boolean help = false;
        if (args.length == 0) {
            help = true;
        } else {
            for (Flag f : flags) {
                if (f.getValue()) {
                    help = true;
                    break;
                }
            }
        }
        if (help) {
            cl.displayHelpScreen("help screen requested", args);
            throw new Exception("help screen requested");
        }

        // get the reps
        if (params[REPETITIONS].getValue() != null) {
            try {
                int r = Integer.parseInt(params[REPETITIONS].getValue());
                data.reps = r;
            } catch (Exception x) {
                throw new Exception("repetitions: \""
                        + params[REPETITIONS].getValue() + "\" must be an integer >= 0");
            }
        } // else use provisions default

        // get the output device
        if (params[OUT].getValue() != null) {
            // try to open the output device
            data.out = new PrintStream(params[OUT].getValue());
            String cwd = System.getProperty("user.dir");
            String sep = System.getProperty("file.separator");
            System.out.println("Main: output device set to " + cwd
                    + sep + params[OUT].getValue());
        } // else use provisions default

        // get the test name
        if (params[TEST_NAME].getValue() != null) {
            data.testName = params[TEST_NAME].getValue();
        } else {
            // test name required
            cl.displayHelpScreen(params[TEST_NAME].getArg() + " parameter required", args);
            throw new Exception("main(): " + params[TEST_NAME].getArg() + " parameter required");
        }
    }
}
///**	Selector for testing the a<sup>n</sup>b<sup>n</sup> grammar. 
//* <p>
//* Use command line parameters, anbn [/reps=#],
//* where #=number of repetitions required for accurate timing comparisons.*/
//public static final String ANBN = "anbn";
///**	Selector for testing the a<sup>n</sup>b<sup>n</sup>c<sup>n</sup> grammar.
//* <p>
//* Use command line parameters, anbncn [/reps=#],
//* where #=number of repetitions required for accurate timing comparisons.*/
//public static final String ANBNCN = "anbncn";
///**	Selector for a demonstration of using AST callback functions.
//* <p>
//* Use command line parameter, astcallbacks.
//*/
//public static final String ASTCALLBACKS = "astcallbacks";
///**
//* Selector for testing the expressions grammar given as grammar (4.2) in 
//* Aho, Lam, Sethi and Ullman, 2nd ed. (the Dragon Book) 
//* <p>
//* Use command line parameters, expressions [/reps=#],
//* where #=number of repetitions required for accurate timing comparisons.*/
//public static final String EXPRESSIONS = "expressions";
///**
//* Selector for testing a grammar for the common initialization file (ini file) format
//* with sections of key=value pairs.
//* <p>
//* Use command line parameters, inifile [/reps=#],
//* where #=number of repetitions required for accurate timing comparisons.*/
//public static final String INIFILE = "inifile";
///**
//* Selector for testing the UDT callback classes provided in UdtLib.
//* <p>
//* Use command line parameters, udtlib testname.<br>
//* See {@link testudtlib.RunUdtTest RunUdtTest}
//* for valid testname parameters.
//*/
//public static final String UDTLIB = "udtlib";
///**
//* Selector for testing the common email address format given in RFC 5321.
//* <p>
//* Use command line parameters, mailbox [/reps=#],
//* where #=number of repetitions required for accurate timing comparisons.*/
//public static final String MAILBOX = "mailbox";
///**
//* Selector for a demonstration of how to add rule/UDT name nodes
//* to the AST from a UDT callback function.
//* <p>
//* Use command line parameter, udtaddstoast. */
//public static final String UDTADDSTOAST = "udtaddstoast";
///**
//* Selector for a demonstration of how a UDT 
//* callback function might be used as a specialized alternative function.
//* An alternative that needs to be more complicated than a simple "prioritized choice"
//* or "longest-match choice", for example.
//* <p>
//* Use command line parameter, udtasalt.
//*/
//public static final String UDTASALT = "udtasalt";
//if(data.testName.equalsIgnoreCase(Tests.DEMO_AST.getName())){
//theTest = new DisplayAst(Tests.DEMO_AST.getName(), data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.DEMO_AST_CALLBACK.getName())){
//theTest = new TranslateAst(Tests.DEMO_AST_CALLBACK.getName(), data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.DEMO_UDT_AST.getName())){
//theTest = new UdtToAst(Tests.DEMO_UDT_AST.getName(), data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.DEMO_UDT_ALT.getName())){
//theTest = new UdtAsAlt(Tests.DEMO_UDT_ALT.getName(), data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.DEMO_TRACE.getName())){
//theTest = new DisplayTrace(Tests.DEMO_TRACE.getName(), data.out);
//}
//if(data.testName.equalsIgnoreCase(Tests.ANBN.getName())){
//theTest = new RunAnBn(Tests.ANBN.getName(), data.reps, data.out);
//}
//if(data.testName.equalsIgnoreCase(Tests.ANBNCN.getName())){
//theTest = new RunAnBnCn(Tests.ANBNCN.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.EXPRESSIONS.getName())){
//theTest = new RunExpressions(Tests.EXPRESSIONS.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.INIFILE.getName())){
//theTest = new RunIniFile(Tests.INIFILE.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.MAILBOX.getName())){
//theTest = new RunMailbox(Tests.MAILBOX.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_ALPHANUM.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_ALPHANUM.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_ANY.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_ANY.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_COMMENT.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_COMMENT_SEMI.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_COMMENT_SEMI.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_COMMENT_SEMI.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_COMMENT_CPP.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_COMMENT_CPP.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_COMMENT_C.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_COMMENT_C.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_DECNUM.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_DECNUM.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_DECNUM.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_DECNUM.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_HEXNUM.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_HEXNUM.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_LINEEND.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_LINEEND.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_LINEEND_FORGIVING.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_LINEEND_FORGIVING.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_LINEEND_LF.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_LINEEND_LF.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_LINEEND_CRLF.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_LINEEND_CRLF.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_QUOTED_STRING.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_QUOTED_STRING.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_WSP.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_WSP.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_WSP_COMMENTS.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_WSP_COMMENTS.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_WSP_FOLDING.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_WSP_FOLDING.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_WSP_CF.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_WSP_CF.getName(), data.reps, data.out);
//}
//else if(data.testName.equalsIgnoreCase(Tests.UDTLIB_WSP_FC.getName())){
//theTest = new RunUdtTest(Tests.UDTLIB_WSP_FC.getName(), data.reps, data.out);
//}
