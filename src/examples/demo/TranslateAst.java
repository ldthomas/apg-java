/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.demo;

import java.io.PrintStream;
import apg.Ast;
import apg.Parser;
import apg.Ast.AstCallback;
import apg.Parser.Result;
import apg.Trace;
import apg.UdtLib;
import apg.Utilities;
import examples.RunTests;

/**
 * A demonstration of how to use AST callback functions to translate the AST.
 * The source code should be consulted for details.
 */
public class TranslateAst extends RunTests {

    /**
     * Constructor for the test. Parameters are supplied explicitly or by
     * default from the command line to the driver function.
     *
     * @param testName the name of the test
     * @param out the output device
     */
    public TranslateAst(String testName, PrintStream out) {
        super(testName, out);
    }

    @Override
    protected void run() throws Exception {
        // display the title and an abstract
        if (testName != null) {
            outputTestName();
        } else {
            throw new Exception("testName may not be null");
        }
        outputAbstract("Use the Expressions grammar to demonstrate translation "
                + "of the AST with AST callback functions.");
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
        out.println("INPUT STRING: (" + testName + ")");
        out.println(Utilities.charArrayToXml(inputString.toCharArray(), 0, inputString.length()));
        out.println();

        // create a parser from the UDT grammar
        Parser parser = new Parser(UExpressions.getInstance());

        // tell the parser what the start rule is
        int startRule = UExpressions.RuleNames.E.ruleID();
        parser.setStartRule(startRule);

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
        if (result.success()) {
            // display the AST in APG format
            outputAst(false);
            ast.display(out);
            out.println();

            // display the AST in XML format
            outputAst(true);
            ast.display(out, true);
            out.println();

            // tell the AST what callback functions to use during translation
            MyData myData = new MyData(out);
            ast.setMyData(myData);
            ast.setRootCallback(new AstTranslator(ast, "Ast_Root"));
            ast.setRuleCallback(startRule, new AstTranslator(ast, UExpressions.RuleNames.E.ruleName()));
            ast.setRuleCallback(UExpressions.RuleNames.T.ruleID(),
                    new AstTranslator(ast, UExpressions.RuleNames.T.ruleName()));
            ast.setRuleCallback(UExpressions.RuleNames.F.ruleID(),
                    new AstTranslator(ast, UExpressions.RuleNames.F.ruleName()));
            AstTranslator eprime = new AstTranslator(ast, UExpressions.RuleNames.EPRIME.ruleName());
            ast.setRuleCallback(UExpressions.RuleNames.EPRIME.ruleID(), eprime);
            ast.setRuleCallback(UExpressions.RuleNames.TPRIME.ruleID(),
                    new AstTranslator(ast, UExpressions.RuleNames.TPRIME.ruleName()));
            ast.setUdtCallback(UExpressions.UdtNames.U_ID.udtID(),
                    new AstTranslator(ast, UExpressions.UdtNames.U_ID.udtName()));

            // translate the AST
            out.println("AST TRANSLATION: (" + testName + ")");
            ast.translateAst();
            out.println();

            // demonstrate how a callback function
            // can tell the AST to skip a branch during translation
            out.println("AST TRANSLATION WITH BRANCH SKIPPING: (" + testName + ")");
            myData.depth = 0;
            eprime.enableSkip(true);
            ast.setRuleCallback(UExpressions.RuleNames.EPRIME.ruleID(), eprime);
            ast.translateAst();
            out.println();
        } else {
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

    class MyData {

        int depth;
        PrintStream out;

        MyData(PrintStream out) {
            this.out = out;
            this.depth = 0;
        }
    }

    class AstTranslator extends AstCallback {

        String nodeName;
        boolean skip;

        AstTranslator(Ast ast, String name) {
            super(ast);
            this.nodeName = name;
            this.skip = false;
        }

        void enableSkip(boolean enable) {
            skip = enable;
        }

        private void indent(int in) {
            for (int i = 0; i < in; i++) {
                out.print(" ");
            }
        }

        @Override
        public boolean preBranch(int offset, int length) {
            PrintStream out = ((MyData) callbackData.myData).out;
            indent(((MyData) callbackData.myData).depth);
            out.print(nodeName + ": down: string: ");
            if (length == 0) {
                out.println("<empty>");
            } else {
                out.println(Utilities.charArrayToXml(callbackData.inputString, offset, length));
            }
            ((MyData) callbackData.myData).depth++;
            return skip;
        }

        @Override
        public void postBranch(int offset, int length) {
            PrintStream out = ((MyData) callbackData.myData).out;
            ((MyData) callbackData.myData).depth--;
            indent(((MyData) callbackData.myData).depth);
            out.print(nodeName + ": up: string: ");
            if (length == 0) {
                out.println("<empty>");
            } else {
                out.println(Utilities.charArrayToXml(callbackData.inputString, offset, length));
            }
        }
    }
}
