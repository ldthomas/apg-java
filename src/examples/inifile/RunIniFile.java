/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.inifile;

import java.io.PrintStream;

import apg.Parser;
import apg.Parser.UdtCallback;
import apg.UdtLib.*;
import examples.RunTests;

/** Uses a grammar for the common "ini" file format for
a comparison of timing and node hits between the normal 
CFG grammar and the use of UDT functions.
 */
public class RunIniFile extends RunTests{
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunIniFile(String testName, int reps, PrintStream out){
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
		outputAbstract("CFG/UDT time & statistics comparison for the \"ini\" file grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		IniFile.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UIniFile.display(out);
		out.println();

		// set up input strings
		if(inputStrings == null          ||
				inputStrings.length == 0 ||
				inputStrings[0] == null  ||
				inputStrings[0].length() == 0){
			inputStringCount = 3;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "[SECTION]\nkey=value\n";
			inputStrings[1] = "[ section1]\r\nkey = value\rkey2 = loooongvalue\n[section2]\n\n\n    	;comment\nxkey = xvalue\r\n";
			inputStrings[2] = "[anotherSection_name];section comment\nanotherkey = annothervalue\n";
		}
		
		// CFG setup
		int startRule = IniFile.RuleNames.INIFILE.ruleID(); // grammar.RuleNames.RULE.ruldID();
		cfgIn = new RunInput("cfg", new Parser(IniFile.getInstance()), startRule);

		// UDT setup
		Parser parser = new Parser(UIniFile.getInstance());
		startRule = UIniFile.RuleNames.INIFILE.ruleID();
		SemiComment comment = new SemiComment(parser);
		LineEnd lineend = new LineEnd(parser);
		parser.setUdtCallback(UIniFile.UdtNames.U_LINEEND.udtID(), lineend);
		parser.setUdtCallback(UIniFile.UdtNames.U_COMMENT.udtID(), comment);
		parser.setUdtCallback(UIniFile.UdtNames.E_ANY.udtID(), new Any(parser));
		parser.setUdtCallback(UIniFile.UdtNames.U_ALPHADIGIT.udtID(), new AlphaDigit(parser));
		parser.setUdtCallback(UIniFile.UdtNames.U_ALPHADIGITUNDER.udtID(), new AlphaDigitUnder(parser));
		parser.setUdtCallback(UIniFile.UdtNames.E_WSP.udtID(), new WhiteSpace(parser));
		parser.setUdtCallback(UIniFile.UdtNames.U_DQSTRING.udtID(), new DoubleQuotedString(parser));
		parser.setUdtCallback(UIniFile.UdtNames.U_SQSTRING.udtID(), new SingleQuotedString(parser));
		parser.setUdtCallback(UIniFile.UdtNames.E_COMMENT_WSP.udtID(), new WhiteSpace(parser, true, comment, null));
		udtIn = new RunInput("udt", parser, startRule);
	}

	class AlphaDigit extends UdtCallback{
		AlphaDigit(Parser p){super(p);}
		@Override public int callback(int offset){
			int len = 0;
			int i = offset;
			char c;
			for(; i < callbackData.inputString.length; i++){
				c = callbackData.inputString[i];
				if((c >= 'a' && c <= 'z') ||
						(c >= 'A' && c <= 'Z') ||
						(c >= '0' && c <= '9')){len++;}
				else{break;}
			}
			return (len > 0) ? len : -1;
		}
	}
	
	class AlphaDigitUnder extends UdtCallback{
		AlphaDigitUnder(Parser p){super(p);}
		@Override public int callback(int offset){
			int len = 0;
			int i = offset;
			char c;
			for(; i < callbackData.inputString.length; i++){
				c = callbackData.inputString[i];
				if((c >= 'a' && c <= 'z') ||
						(c >= 'A' && c <= 'Z') ||
						(c >= '0' && c <= '9') ||
						(c == '_')){len++;}
				else{break;}
			}
			return (len > 0) ? len : -1;
		}
	}
}
