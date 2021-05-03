/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.mailbox;

import java.io.PrintStream;

import apg.Parser;
import apg.UdtLib.*;
import examples.RunTests;
/** Uses the "mailbox" grammar from RFC 5321 for
a comparison of timing and node hits between the normal 
CFG grammar and the use of UDT functions.
The mailbox grammar is a generally accepted standard for the email addresses.
 */
public class RunMailbox extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunMailbox(String testName, int reps, PrintStream out){
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
		outputAbstract("CFG/UDT time & statistics comparison for the email address grammar");
		out.println();
		
		// display the grammars
		outputCfgGrammar();
		Mailbox.display(out);
		out.println();
		outputUdtGrammar();
		UMailbox.display(out);
		out.println();

		// set up input strings
		if(inputStrings == null          ||
				inputStrings.length == 0 ||
				inputStrings[0] == null  ||
				inputStrings[0].length() == 0){
			inputStringCount = 14;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "test@domain.com";
			inputStrings[1] = "test.middle.last@domain.com";
			inputStrings[2] = "test@dom-ain.com";
			inputStrings[3] = "test@dom--ain.com";
			inputStrings[4] = "test@n.com";
			inputStrings[5] = "test_two@domain.com";
			inputStrings[6] = "\"test_two\"@domain.com";
			inputStrings[7] = "test@[1111:2222:3333:4444:5555:6666:7777:8888]";
			inputStrings[8] = "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]";
			inputStrings[9] = "test@[IPv6:1111:2222:3333:4444:5555:6666::8888]";
			inputStrings[10] = "test@[IPv6:1111:2222:3333:4444:5555:255.255.255.255]";
			inputStrings[11] = "test@[1.2.3.4]";
			inputStrings[12] = "test@[01.002.0.000]";
			inputStrings[13] = "test@[301.2.3.4]";
		}
		
		// CFG grammar-dependent setup
		int startRule = Mailbox.RuleNames.MAILBOX.ruleID(); // grammar.RuleNames.RULE.ruldID();
		cfgIn = new RunInput("cfg", new Parser(Mailbox.getInstance()), startRule);

		// UDT grammar-dependent setup
		Parser udtParser = new Parser(UMailbox.getInstance());
		startRule = UMailbox.RuleNames.MAILBOX.ruleID();
		
		// UDT ids
		int[] ids = new int[UMailbox.udtCount];
		ids[0] = UMailbox.UdtNames.U_SNUM.udtID();
		ids[1] = UMailbox.UdtNames.U_IPV6_HEX.udtID();
		ids[2] = UMailbox.UdtNames.U_ATOM.udtID();
		ids[3] = UMailbox.UdtNames.U_DCONTENT.udtID();
		ids[4] = UMailbox.UdtNames.U_LET_DIG.udtID();
		ids[5] = UMailbox.UdtNames.U_STANDARDIZED_TAG.udtID();
		ids[6] = UMailbox.UdtNames.E_LDH_STR.udtID();
		
		// UDT callback functions
		udtParser.setUdtCallback(ids[0], new DecNum(udtParser, 1, 3, true));
		udtParser.setUdtCallback(ids[1], new HexNum(udtParser, 1, 3));
		udtParser.setUdtCallback(ids[2], new Atom(udtParser));
		udtParser.setUdtCallback(ids[3], new DContent(udtParser));
		udtParser.setUdtCallback(ids[4], new ULet_dig(udtParser));
		udtParser.setUdtCallback(ids[5], new Ldh_str(udtParser, false));
		udtParser.setUdtCallback(ids[6], new Ldh_str(udtParser, true));
		udtIn = new RunInput("udt", udtParser, startRule);
	}
}
