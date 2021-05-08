/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.anbncn;

import java.io.PrintStream;

import apg.Parser;
import examples.RunTests;

/**
 * Uses the grammar for the strings a<sup>n</sup>b<sup>n</sup>c<sup>n</sup>, n
 * &gt; 0, for a comparison of timing and node hits between the normal CFG
 * grammar and the use of UDT functions.
 */
public class RunAnBnCn extends RunTests {

    /**
     * Constructor for the test. Parameters are supplied explicitly or by
     * default from the command line to the driver function.
     *
     * @param testName the name of the test
     * @param reps the number of repetitions of the parser
     * @param out the output device
     */
    public RunAnBnCn(String testName, int reps, PrintStream out) {
        super(testName, reps, out);
    }

    @Override
    protected void run() throws Exception {
        setup();
        runComparisionOfCfgAndUdt();
    }

    void setup() throws Exception {
        // display the title and an abstract
        if (testName != null) {
            outputTestName();
        } else {
            throw new Exception("testName may not be null");
        }
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
        if (inputStrings == null
                || inputStrings.length == 0
                || inputStrings[0] == null
                || inputStrings[0].length() == 0) {
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
