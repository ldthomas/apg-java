/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.testudtlib;

import java.io.PrintStream;

import apg.Parser;
import apg.Parser.UdtCallback;
import apg.UdtLib;
import examples.Main;
import examples.RunTests;
/** Driver function for time comparison testing of all UdtLib UDT functions
 * vs the CFG versions of the grammars.
 */
public class RunUdtTest extends RunTests {
	/**
	 * Constructor for the test. Parameters are supplied
	 * explicitly or by default from the command line to the driver function.
	 * @param testName the name of the test
	 * @param reps the number of repetitions of the parser 
	 * @param out the output device
	 */
	public RunUdtTest(String testName, int reps, PrintStream out){
		super(testName, reps, out);
	}

	@Override
	protected void run() throws Exception {
		if(testName.equals(Main.Tests.UDTLIB_ALPHANUM.getName())){setupAlphanum();}
		else if(testName.equals(Main.Tests.UDTLIB_ANY.getName())){setupAny();}
		else if(testName.equals(Main.Tests.UDTLIB_COMMENT.getName())){setupCommentSemi();}
		else if(testName.equals(Main.Tests.UDTLIB_COMMENT_SEMI.getName())){setupCommentSemi();}
		else if(testName.equals(Main.Tests.UDTLIB_COMMENT_CPP.getName())){setupCommentCpp();}
		else if(testName.equals(Main.Tests.UDTLIB_COMMENT_C.getName())){setupCommentC();}
		else if(testName.equals(Main.Tests.UDTLIB_DECNUM.getName())){setupDecNum();}
		else if(testName.equals(Main.Tests.UDTLIB_HEXNUM.getName())){setupHexNum();}
		else if(testName.equals(Main.Tests.UDTLIB_LINEEND.getName())){setupLineEndForgiving();}
		else if(testName.equals(Main.Tests.UDTLIB_LINEEND_FORGIVING.getName())){setupLineEndForgiving();}
		else if(testName.equals(Main.Tests.UDTLIB_LINEEND_LF.getName())){setupLineEndLf();}
		else if(testName.equals(Main.Tests.UDTLIB_LINEEND_CRLF.getName())){setupLineEndCrlf();}
		else if(testName.equals(Main.Tests.UDTLIB_QUOTED_STRING.getName())){setupQuotedString();}
		else if(testName.equals(Main.Tests.UDTLIB_WSP.getName())){setupWhiteSpaceOnly();}
		else if(testName.equals(Main.Tests.UDTLIB_WSP_COMMENTS.getName())){setupWhiteSpaceComment();}
		else if(testName.equals(Main.Tests.UDTLIB_WSP_FOLDING.getName())){setupWhiteSpaceFolding();}
		else if(testName.equals(Main.Tests.UDTLIB_WSP_CF.getName())){setupWhiteSpaceFoldingComment();}
		else if(testName.equals(Main.Tests.UDTLIB_WSP_FC.getName())){setupWhiteSpaceFoldingComment();}
		else{throw new Exception("RunUdtTest: run: unrecognized testName \""+testName+"\" selected");}
		runComparisionOfCfgAndUdt();
	}

	private void setupAlphanum() throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the alphanum + underscore grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		Alphanum.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UNonEmpty.display(out);
		out.println();

		inputStringCount = 3;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "A";
		inputStrings[1] = "Average_";
		inputStrings[2] = "long_Alsdkjflkdjflkdj_8953987593875__lskdjflskjflaskdjflkdsfja_934857938479_oriuwoieru";

		// CFG setup
		cfgIn = new RunInput("cfg", new Parser(Alphanum.getInstance()),
				Alphanum.RuleNames.ALPHANUM.ruleID());

		// UDT setup
		Parser parser = new Parser(UNonEmpty.getInstance());
		int startRule = UNonEmpty.RuleNames.UDT.ruleID();
		int udtId = UNonEmpty.UdtNames.U_UDT.udtID();
		UdtCallback callback = new UdtLib.Alphanum(parser, '_');
		parser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", parser, startRule);
	}

	private void setupAny() throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the \"any character string\" grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		AnyString.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UEmpty.display(out);
		out.println();

		inputStringCount = 3;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "a";
		inputStrings[1] = " comment ";
		inputStrings[2] = "123456  	7890abcdefABCDEF~!@#$%^&*()_+=-`[]\\{}|;':\",./<>?";
		
		// CFG setup
		cfgIn = new RunInput("cfg", new Parser(AnyString.getInstance()),
				AnyString.RuleNames.ANYSTRING.ruleID());

		// UDT setup
		Parser parser = new Parser(UEmpty.getInstance());
		int startRule = UEmpty.RuleNames.UDT.ruleID();
		int udtId = UEmpty.UdtNames.E_UDT.udtID();
		UdtCallback callback = new UdtLib.Any(parser, true);
		parser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", parser, startRule);
	}
	

	private void setupDecNum() throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the decimal number grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		DecNum.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UEmpty.display(out);
		out.println();

		inputStringCount = 3;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "1";
		inputStrings[1] = "001";
		inputStrings[2] = "0123456789";
		
		// CFG setup
		cfgIn = new RunInput("cfg", new Parser(DecNum.getInstance()),
				DecNum.RuleNames.DECNUM.ruleID());

		// UDT setup
		Parser parser = new Parser(UNonEmpty.getInstance());
		int startRule = UNonEmpty.RuleNames.UDT.ruleID();
		int udtId = UNonEmpty.UdtNames.U_UDT.udtID();
		UdtCallback callback = new UdtLib.DecNum(parser);
		parser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", parser, startRule);
	}

	private void setupCommentSemi() throws Exception{
		setupComment(1);
	}
	private void setupCommentCpp() throws Exception{
		setupComment(2);
	}
	private void setupCommentC() throws Exception{
		setupComment(3);
	}
	private void setupComment(int blId) throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the semi-comment grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		Comment.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UNonEmpty.display(out);
		out.println();
		
		int blSemi = 1; 
		int blCpp  = 2; 
		int blC    = 3; 
		int cfgStartRule = Comment.RuleNames.SEMI_COMMENT.ruleID();
		Parser udtParser = new Parser(UEmpty.getInstance());
		Parser cfgParser = new Parser(Comment.getInstance());
		UdtCallback callback = null;
		if(blId == blSemi){
			cfgStartRule = Comment.RuleNames.SEMI_COMMENT.ruleID();
			callback = new UdtLib.SemiComment(udtParser);
			inputStringCount = 3;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "; comment";
			inputStrings[1] = "; looooooooonger comment";
			inputStrings[2] = "; very long comment long comment long comment long comment long comment";
		} else if(blId == blCpp){
			cfgStartRule = Comment.RuleNames.CPP_COMMENT.ruleID();
			callback = new UdtLib.CppComment(udtParser);
			inputStringCount = 3;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "// C++ comment";
			inputStrings[1] = "// looooonger \t\t\t\t C++ comment";
			inputStrings[2] = "// much longer C++ comment  C++ comment  C++ comment  C++ comment  C++ comment  C++ comment  C++ comment ";
		} else if(blId == blC){
			cfgStartRule = Comment.RuleNames.C_COMMENT.ruleID();
			callback = new UdtLib.CComment(udtParser);
			inputStringCount = 3;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "/* C Comment */";
			inputStrings[1] = "/* C Comment \n 	with line ends \r comment */";
			inputStrings[2] = "/* Longer C Comment 123456  	7890abcdefABCDEF~!@#$%^&*()_+\r\n=-`[]\\{}|;':\",./<>?\ncomment*/";
		}

		// CFG setup
		cfgIn = new RunInput("cfg", cfgParser, cfgStartRule);

		// UDT setup
		int startRule = UEmpty.RuleNames.UDT.ruleID();
		int udtId = UEmpty.UdtNames.E_UDT.udtID();
		udtParser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", udtParser, startRule);
	}

	private void setupHexNum() throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the hexidecimal number grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		HexNum.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UNonEmpty.display(out);
		out.println();

		inputStringCount = 4;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "a";
		inputStrings[1] = "A";
		inputStrings[2] = "00ff";
		inputStrings[3] = "1234567890abcdefABCDEF";
		
		// CFG setup
		cfgIn = new RunInput("cfg", new Parser(HexNum.getInstance()),
				HexNum.RuleNames.HEXNUM.ruleID());

		// UDT setup
		Parser parser = new Parser(UNonEmpty.getInstance());
		int count = 1;
		int[] ids = new int[count];
		ids[0] = UNonEmpty.UdtNames.U_UDT.udtID();
		UdtLib.HexNum num = new UdtLib.HexNum(parser, false);
		parser.setUdtCallback(ids[0], num);
		udtIn = new RunInput("udt", parser, UNonEmpty.RuleNames.UDT.ruleID());
	}
	
	private void setupLineEndForgiving() throws Exception{
		setupLineEnd(1);
	}
	private void setupLineEndLf() throws Exception{
		setupLineEnd(2);
	}
	private void setupLineEndCrlf() throws Exception{
		setupLineEnd(3);
	}
	private void setupLineEnd(int le) throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the forgiving line end grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		LineEnd.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UNonEmpty.display(out);
		out.println();

		int leForgiving = 1; 
		int leLF        = 2; 
		int leCRLF      = 3; 
		int cfgStartRule = LineEnd.RuleNames.FORGIVING_LINEEND.ruleID();
		Parser udtParser = new Parser(UEmpty.getInstance());
		Parser cfgParser = new Parser(LineEnd.getInstance());
		UdtCallback callback = null;
		if(le == leForgiving){
			cfgStartRule = LineEnd.RuleNames.FORGIVING_LINEEND.ruleID();
			callback = new UdtLib.ForgivingLineEnd(udtParser);
			inputStringCount = 3;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "\n";
			inputStrings[1] = "\r";
			inputStrings[2] = "\r\n";
		} else if(le == leLF){
			cfgStartRule = LineEnd.RuleNames.LF_LINEEND.ruleID();
			callback = new UdtLib.LFLineEnd(udtParser);
			inputStringCount = 1;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "\n";
		} else if(le == leCRLF){
			cfgStartRule = LineEnd.RuleNames.CRLF_LINEEND.ruleID();
			callback = new UdtLib.CRLFLineEnd(udtParser);
			inputStringCount = 1;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "\r\n";
		}
		
		// CFG setup
		cfgIn = new RunInput("cfg", cfgParser, cfgStartRule);

		// UDT setup
		int udtStartRule = UNonEmpty.RuleNames.UDT.ruleID();
		int udtId = UNonEmpty.UdtNames.U_UDT.udtID();
		udtParser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", udtParser, udtStartRule);
	}
	private void setupWhiteSpaceOnly() throws Exception{
		setupWhiteSpace(WhiteSpace.RuleNames.WSP.ruleID());
	}
	private void setupWhiteSpaceComment() throws Exception{
		setupWhiteSpace(WhiteSpace.RuleNames.WSP_COMMENT.ruleID());
	}
	private void setupWhiteSpaceFolding() throws Exception{
		setupWhiteSpace(WhiteSpace.RuleNames.FWSP.ruleID());
	}
	private void setupWhiteSpaceFoldingComment() throws Exception{
		setupWhiteSpace(WhiteSpace.RuleNames.FWSP_COMMENT.ruleID());
	}
	private void setupWhiteSpace(int wspId) throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the white space grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		WhiteSpace.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UEmpty.display(out);
		out.println();

		// determine which test
		Parser cfgParser = new Parser(WhiteSpace.getInstance());
		Parser udtParser = new Parser(UNonEmpty.getInstance());
		int wspOnly = WhiteSpace.RuleNames.WSP.ruleID();
		int wspComment = WhiteSpace.RuleNames.WSP_COMMENT.ruleID();
		int wspFwsp = WhiteSpace.RuleNames.FWSP.ruleID();
		int wspCommentFwsp = WhiteSpace.RuleNames.FWSP_COMMENT.ruleID();
		UdtLib.SemiComment comment = new UdtLib.SemiComment(udtParser);
		UdtLib.LineEnd lineend = new UdtLib.LineEnd(udtParser);
		UdtCallback udtCallback = null;
		if(wspId == wspOnly){
			// set up for white space only
			out.println("*** white space only ***");
			udtCallback = new UdtLib.WhiteSpace(udtParser);
			inputStringCount = 4;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "   ";
			inputStrings[1] = " \t ";
			inputStrings[2] = "          ";
			inputStrings[3] = "\t\t\t\t\t\t\t\t\t\t";
		} else if(wspId == wspComment){
			// set up for white space with comments
			out.println("*** white space with comments ***");
			udtCallback = new UdtLib.WhiteSpace(udtParser, false, comment, null);
			inputStringCount = 4;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = "   ";
			inputStrings[1] = " \t ; an average comment ";
			inputStrings[2] = "          ; a longer and longer comment";
			inputStrings[3] = "\t\t\t\t\t\t\t\t\t\t ; a longer and longer and  longer even much longer comment";
		} else if(wspId == wspFwsp){
			// set up for white space with folding white space
			out.println("*** white space folding white space ***");
			udtCallback = new UdtLib.WhiteSpace(udtParser, false, null, lineend);
			inputStringCount = 4;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = " \t ";
			inputStrings[1] = " \t \n \t";
			inputStrings[2] = "          \r\n     \r     \n     ";
			inputStrings[3] = "\t\t\t\t\t\t\t\t\t\t\n\t\t";
		} else if(wspId == wspCommentFwsp){
			// set up for white space comments and folding white space
			out.println("*** white space with comments and folding white space ***");
			udtCallback = new UdtLib.WhiteSpace(udtParser, false, comment, lineend);
			inputStringCount = 4;
			inputStrings = new String[inputStringCount];
			inputStrings[0] = " \t ";
			inputStrings[1] = " \t \n \t";
			inputStrings[2] = "          \r\n ;comment 1    \r     ; comment comment 2\n     ; final comment";
			inputStrings[3] = "\t\t\t\t\t\t\t\t\t\t\n\t;comment\t";
		} else{
			// abort with error message
			throw new Exception("RunUdtTest: setupWhiteSpace: unrecognized command line arguments");
		}
		
		udtParser.setUdtCallback(UNonEmpty.UdtNames.U_UDT.udtID(), udtCallback);
		udtIn = new RunInput("udt", udtParser, UNonEmpty.RuleNames.UDT.ruleID());
		cfgIn = new RunInput("cfg", cfgParser, wspId);
	}

	private void setupQuotedString() throws Exception{
		// display the title and an abstract
		if(testName != null){out.println("TEST NAME: "+testName);}
		else{throw new Exception("testName may not be null");}
		out.println(" ABSTRACT: CFG/UDT time and statistics comparison for the double quoted string grammar");
		out.println();
		
		// display the grammars
		out.println("GRAMMAR: CFG");
		QuotedString.display(out);
		out.println();
		out.println("GRAMMAR: UDT");
		UNonEmpty.display(out);
		out.println();

		inputStringCount = 3;
		inputStrings = new String[inputStringCount];
		inputStrings[0] = "\"A\"";
		inputStrings[1] = "\"Average_\"";
		inputStrings[2] = "\"long_Alsdkjflkdjflkdj_8953987593875__lskdjflskjflaskdjflkdsfja_934857938479_oriuwoieru\"";

		
		// CFG setup
		Parser parser = new Parser(UNonEmpty.getInstance());
		int startRule = UNonEmpty.RuleNames.UDT.ruleID();
		cfgIn = new RunInput("cfg", parser, startRule);

		// UDT setup
		int udtId = UNonEmpty.UdtNames.U_UDT.udtID();
		UdtCallback callback = new UdtLib.DoubleQuotedString(parser);
		parser.setUdtCallback(udtId, callback);
		udtIn = new RunInput("udt", parser, startRule);
	}
}
//String clComment = "comments";
//String clFolding = "folding";
//if(args.length == 0){
//	// white space only
//	wspId = wspOnly;
//} else if(args[0].equalsIgnoreCase(clComment)){
//	if(args.length > 1){
//		if(args[1].equalsIgnoreCase(clFolding)){
//			// white space includes both comments and folding white space
//			wspId = wspCommentFwsp;
//		} else{
//			// display warning message
//			out.println("\nWarning: unrecognized command line parameter: "+args[1]);
//			  out.println("       : should be: "+clFolding);
//			
//			// white space includes comments
//			wspId = wspComment;
//		}
//	} else{
//		// white space includes comments
//		wspId = wspComment;
//	}
//} else if(args[0].equalsIgnoreCase(clFolding)){
//	// white space includes folding white space
//	wspId = wspFwsp;
//} else{
//	// display warning message
//	out.println("\nWarning: unrecognized command line parameter: "+args[0]);
//	  out.println("       : should be: "+clComment);
//	// white space only
//	wspId = wspOnly;
//}

