package apg;

import java.io.PrintStream;
/**
 * The Parser class is used to construct a parser for a specific grammar. 
 * It is initialized with a generated Grammar class object
 * and optional UDT and rule callback functions.
 * Optionally, the Parser class can be configured to generate an AST,
 * parsing statistics,
 * and/or a trace of the syntax tree nodes processed during the parse.
 */
public class Parser {
	private SystemData sysData = new SystemData();
	private Opcode.Rnm[] rnms;
	private Opcode.Udt[] udts;
	private Opcode[] opcodes;
	private Grammar grammar;
	
	// public API
	/**
	 * The Parser constructor.
	 * @param grammar an instance of a Generator-generated Grammar class.
	 */
	public Parser(Grammar grammar){initInstance(grammar);}
	/**
	 * Enables or disables the generation of an Abstract Syntax Tree (AST).
	 * The Ast class has no public constructor
	 * and this function is the only way to generate an instance.
	 * @param enable if true, an AST will be generated, if false, not.
	 * By default enable is false.
	 * @return an instance of the Ast class.
	 * @see Ast
	 */
	public Ast enableAst(boolean enable){
		if(enable){
			if(sysData.ast == null){sysData.ast = new Ast(this, grammar);}
			sysData.ast.setInputString(sysData.inputString);
		} else{sysData.ast = null;}
		return sysData.ast;
	}
	/**
	 * Enables or disables the generation of a Trace.
	 * Trace has no public constructor and this function is the only way to generate an instance.
	 * @param enable if true, an trace will be generated, if false, not.
	 * By default enable is false.
	 * @return an instance of the Trace class.
	 * @see Trace
	 */
	public Trace enableTrace(boolean enable){
		if(enable){
			if(sysData.trace == null){sysData.trace = new Trace(this, grammar);}
		} else{sysData.trace = null;}
		return sysData.trace;
	}
	/**
	 * Enables or disables the generation of a parsing statistics.
	 * Statistics has no public constructor and this function is the only way to generate an instance.
	 * @param enable if true, statistics will be generated, if false, not.
	 * By default enable is false.
	 * @return an instance of the Statistics class.
	 * @see Statistics
	 */
	public Statistics enableStatistics(boolean enable){
		if(enable){
			if(sysData.stats == null){sysData.stats = new Statistics(grammar);}
		} else{sysData.stats = null;}
		return sysData.stats;
	}
	/**
	 * Sets the start rule.
	 * By default <code>start = 0</code>
	 * which identifies the first rule in the grammar definition.
	 * @param start the identifier for the desired start rule.
	 * @see GeneratorGrammar.RuleNames
	 * @throws IllegalArgumentException thrown if the <code>start</code> identifier is out of range.
	 */
	public void setStartRule(int start) throws IllegalArgumentException{
		if(start < 0 || start >= rnms.length){
			throw new IllegalArgumentException("start rule ID out of bounds");
		}
		sysData.startRule = start;
	}
	/**
	 * Sets the input string to be parsed.
	 * A call to this function 
	 * is required prior to calling {@link #parse parse()}
	 * @param input the input string
	 */
	public void setInputString(String input){
		if(input == null || input.length() == 0){setInputString(new char[0]);}
		else{setInputString(input.toCharArray());}
	}
	/**
	 * Sets the input string to be parsed.
	 * A call to this function
	 * is required
	 * prior to calling {@link #parse parse()}
	 * @param input the input string
	 */
	public void setInputString(char[] input){
		if(input == null) {input = new char[0];}
		sysData.inputString = input;
		sysData.callbackData.inputString = input.clone();
		if(sysData.ast != null){sysData.ast.setInputString(input);}
	}
	/**
	 * Sets the user-defined callback function to be called when processing the
	 * identified rule nodes.
	 * @param id identifier of the rule for which this callback is to be called.
	 * @param callback an instance of a user-written extension of the RuleCallback class.
	 * @see GeneratorGrammar.RuleNames
	 */
	public void setRuleCallback(int id, RuleCallback callback){
		if(id < 0 || id >= rnms.length){
			throw new IllegalArgumentException("callback rule ID out of bounds");
		}
		rnms[id].callback = callback;
	}
	/**
	 * Sets the user-defined callback function to be called when processing the
	 * identified UDT nodes.
	 * @param id identifier of the UDT for which this callback is to be called.
	 * @param callback an instance of a user-written extension of the UdtCallback class.
	 * @see GeneratorGrammar.UdtNames
	 */
	public void setUdtCallback(int id, UdtCallback callback){
		if(id < 0 || id >= udts.length){
			throw new IllegalArgumentException("callback UDT ID out of bounds");
		}
		udts[id].callback = callback; 
	}
	/**
	 * Called to provision the callback functions with a user-defined data class.
	 * <code>null</code> by default.
	 * @param data any object of the user's choice.
	 */
	public void setMyData(Object data){sysData.callbackData.myData = data;}
	/**
	 * Call this function to parser the input string.
	 * A call to {@link #setInputString(String) setInputString}
	 * is required prior to calling this function.
	 * @return a Result class with information about the parser results.
	 * @throws Exception thrown if any callback function returns an illegal value.
	 * Illegal values are phrase lengths longer than the length of the remaining input string
	 * or a zero (empty) length by a UDT designated as non-empty.
	 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
	 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
	 * @see Result
	 */
	public Result parse() throws Exception{
		// validate
		for(Opcode.Udt udt : udts){
			if(udt.callback == null){
				throw new Exception("not all UDT callback functions have been set");
			}
		}
		if(sysData.inputString == null){
			throw new Exception("input string has not been set");
		}
		
		// clear the dynamic system data
		sysData.clear();
		
		// parse the start rule
		if(sysData.ast != null){sysData.ast.beginAst();}
		if(sysData.trace != null){sysData.trace.beginTrace(sysData.inputString);}
		rnms[sysData.startRule].execute(sysData);
		if(sysData.ast != null){sysData.ast.endAst();}
		if(sysData.trace != null){sysData.trace.endTrace(sysData.inputString);}
		
		// analyze the results
		return new Result(sysData.inputString, 
				sysData.match, 
				sysData.offset, 
				sysData.maxMatchedLength);
	}

	// public API
	/**
	 * Provisioned by the Parser for rule and UDT callback functions.
	 */
	public class CallbackData{
		private CallbackData(){}
		/**
		 * Optional user-defined data for use by all rule and UDT callback functions.
		 * @see #setMyData(Object)
		 */
		public Object myData = null;
		/**
		 * The input string being parsed.
		 * @see #setInputString
		 */
		public char[] inputString = null;
	}
	/**
	 * The base class for all rule callback functions.
	 */
	public static class RuleCallback{
		/**
		 * Parser-provisioned data available to all rule callback functions
		 */
		public final CallbackData callbackData;
		/**
		 * Base class constructor for the rule callback functions.
		 * @param parser the Parser object to attach this callback function to.
		 */
		public RuleCallback(Parser parser){
			this.callbackData = parser.sysData.callbackData;
		}
		/**
		 * Called by the parser just prior to parsing the syntax tree branch below
		 * this rule node. This callback function can do nothing, perform semantic actions or
		 * can function fully as a terminal node, skipping the parser's processing
		 * of the branch below entirely.
		 * @param offset offset into the input string of the 
		 * first character of the phrase to be matched. 
		 * @return the return value can be a phrase length, <code>L</code>, 
		 * indicating to the parser
		 * that this is a terminal node, or <code>-1</code> to continue normally.
		 * If acting as a terminal node the parser will skip normal processing of
		 * the branch below and
		 * the phrase length must be in the range <code>0 &lt;= L &lt;=(n-1)</code>,
		 * where <code>n</code> is the remaining number of characters in the input string.
		 * @throws Exception thrown if the callback function returns an illegal value.
		 * An illegal value would be a phrase length <code>L &gt;= n</code>, for example.
		 */
		public int preBranch(int offset) throws Exception{return -1;}
		/**
		 * Called by the parser just following the parsing of the syntax tree branch below
		 * this rule node. This callback function can do nothing, perform semantic actions
		 * or override the parser's result, acting as a terminal node.
		 * @param offset offset into the input string of the 
		 * first character of the matched phrase, if any. 
		 * @param length length of the parser's matched phrase or 
		 * <code>-1</code> if no phrase was matched.
		 * @return the return value can be a phrase length, <code>L</code>, 
		 * indicating to the parser that this is a terminal node overriding the parser's result, 
		 * or <code>-1</code> to continue normally.
		 * If acting as a terminal node, the phrase length must be 
		 * in the range <code>0 &lt;= L &lt;=(n-1)</code>,
		 * where <code>n</code> is the remaining number of characters in the input string.
		 * @throws Exception thrown if the callback function returns an illegal value.
		 * An illegal value would be a phrase length <code>L &gt;= n</code>, for example.
		 */
		public int postBranch(int offset, int length) throws Exception{return -1;}
	}

	/**
	 * Base class for all User-Defined Terminals (UDTs).
	 */
	public static abstract class UdtCallback {
		protected final Parser parser;
		/**
		 * Parser-provisioned data available to all UDT callback functions
		 */
		public final CallbackData callbackData;
		/**
		 * Base class constructor for the UDT callback functions.
		 * @param parser the instance of the Parser to attach this callback function to.
		 */
		public UdtCallback(Parser parser){
			this.parser = parser;
			this.callbackData = parser.sysData.callbackData;
		}
		/**
		 * The UDT function.
		 * @param offset offset into the input string to the first character of the parsed phrase. 
		 * @return the matched phrase length, <code>L</code>, 
		 * or <code>-1</code> if no phrase was matched.
		 * The phrase length must be in the range <code>0 &lt;= L &lt;=(n-1)</code>,
		 * where <code>n</code> is the remaining number of characters in the input string.
		 * @throws Exception thrown if the callback function returns an illegal value.
		 * An illegal value would be a phrase length <code>L &gt;= n</code>, or
		 * Illegal values are phrase lengths longer than the length of the remaining input string
		 * or a zero (empty) length by a UDT designated as non-empty.
		 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
		 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
		 */
		public abstract int callback(int offset) throws Exception;
	}
	
	// reports parsed results
	/**
	 * Defines the Parser's results.
	 * @see #parse() 
	 */
	public class Result{
		private final boolean state;
		private final int matchedPhraseLength;
		private final int maxMatchedPhraseLength;
		private final char[] inputString;
		Result(char[] inputString, boolean state, int matched, int maxMatched){
			this.inputString = inputString;
			this.state = state;
			this.matchedPhraseLength = matched;
			this.maxMatchedPhraseLength = maxMatched;
		}
		/**
		 * Called to get a copy of the string that was just parsed.
		 * @return a clone of the parsed string.
		 */
		public char[] getInputString(){return inputString.clone();}
		/**
		 * Called to determine the success of the Parser.
		 * @return if <code>true</code> the parser state is <code>true</code>
		 * and the entire input string was consumed.
		 * If <code>false</code> the parse was unsuccessful.
		 * This could mean that the state was <code>false</code>
		 * or that the entire input string was not consumed or both.
		 */
		public boolean success(){return state && (matchedPhraseLength == inputString.length);}
		/**
		 * Called to get the final state of the parser.
		 * Note that if the matched phrase is not the entire input string 
		 * the parse will not succeed even though the state is <code>true</code>
		 * @return if <code>true</code> a phrase was successfully matched, 
		 * if <code>false</code> no phrase was successfully matched.
		 * Note that if <code>true</code> the phrase matched may not be the
		 * entire input string.
		 */
		public boolean getState(){return state;}
		/**
		 * Called to get the length of the matched phrase.
		 * @return length of the matched phrase.
		 * This will be zero if <code>getState()</code> returns <code>false</code>.
		 * If if <code>getState()</code> returns <code>true</code>
		 * the length returned may or may not be the full length of the input string.
		 */
		public int getMatchedPhraseLength(){return matchedPhraseLength;}
		/**
		 * Called to get the maximum matched phrase length.
		 * @return the maximum phrase length matched during the parse.
		 * May be <code>0</code> even if <code>getState()</code> returns <code>false</code>.
		 * Can be useful in locating the point at which the Parser failed.
		 */
		public int getMaxMatchedPhraseLength(){return maxMatchedPhraseLength;}
		/**
		 * Displays the Parser's results on the output device.
		 * @param out the PrintStream to display the results on.
		 */
		public void displayResult(PrintStream out){
			if(success()){out.print("   parser result: success\n");}
			else{
				out.print(String.format("%25s: %s\n", "parser result", "failure"));
				out.print(String.format("%25s: %s\n", "phrase matched", (state ? "true": "false")));
				out.print(String.format("%25s: %d\n", "input length", inputString.length));
				out.print(String.format("%25s: %d\n", "matched phrase length", matchedPhraseLength));
				out.print(String.format("%25s: %d\n", "max matched phrase length", maxMatchedPhraseLength));
			}
		}
		/**
		 * Returns a string describing the Parser's results.
		 */
		@Override public String toString(){
			StringBuffer buf = new StringBuffer();
			buf.append("           state: ");
			buf.append(state);
			buf.append("\n");
			buf.append("     totalPhrase: ");
			buf.append(inputString.length);
			buf.append("\n");
			buf.append("   matchedPhrase: ");
			buf.append(inputString.length);
			buf.append("\n");
			buf.append("maxMatchedPhrase: ");
			buf.append(maxMatchedPhraseLength);
			buf.append("\n");
			return buf.toString();
		}
	}

	/**
	 * <i>NOTE: This function should only be called from UdtCallback or RuleCallback
	 * callback functions. Results will be unpredictable if called otherwise.</i><p>
	 * Parses the requested rule as if it were a child node of the calling function.
	 * A call to this function alters the grammar being parsed and
	 * should be used with care.
	 * @param id grammar id of the rule to parse.
	 * Must be in the range <code>0 - (rule count-1)</code>.
	 * @param offset offset of the first character in the input string of the phrase to match.
	 * @return the length of the matched phrase if the parse is successful.
	 * <code>-1</code> otherwise.
	 * Phrase lengths must in the range <code>0 - (n-1)</code>,
	 * where <code>n</code> is the remaining string length.
	 * @throws IllegalArgumentException thrown if id is out of range (see above).
	 * @throws Exception thrown if the callback function returns an illegal value.
	 * An illegal value would be a phrase length <code>L &gt;= n</code> 
	 * or a zero (empty) length by a UDT designated as non-empty.
	 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
	 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
	 */
	public int executeRule(int id, int offset) throws IllegalArgumentException, Exception{
		int ret = -1;
		if( id < 0 || id >= rnms.length){
			throw new IllegalArgumentException(
					"Parser: udtExecuteRule: rule id="+id+": offset = "+offset+
					": id must be >= 0 and less than number of rules="+rnms.length);
		}
		if(offset < sysData.offset || offset > sysData.inputString.length){
			throw new IllegalArgumentException(
					"Parser: udtExecuteRule: rule id="+id+": offset = "+offset+
					"; offset must be >= "+sysData.offset+
					" and less than the input string length "+
					sysData.inputString.length);
		}
		Opcode.Rnm ubie = rnms[id];
		int saveOffset = sysData.offset;
		sysData.offset = offset;
		ubie.execute(sysData);
		if(sysData.match){ret = sysData.offset - offset;}
		sysData.offset = saveOffset;
		return ret;
	}
	/**
	 * <i>NOTE: This function should only be called from UdtCallback or RuleCallback
	 * callback functions. Results will be unpredictable if called otherwise.</i><p>
	 * Parses the requested UDT as if it were a child node of the calling function.
	 * A call to this function alters the grammar being parsed and
	 * should be used with care.
	 * @param id grammar id of the UDT to parse.
	 * Must be in the range <code>0 - (UDT count-1)</code>.
	 * @param offset offset of the first character in the input string of the phrase to match.
	 * @return the length of the matched phrase if the parse is successful.
	 * <code>-1</code> otherwise.
	 * Phrase lengths must in the range <code>0 - (n-1)</code>,
	 * where <code>n</code> is the remaining string length.
	 * @throws IllegalArgumentException thrown if id is out of range (see above).
	 * @throws Exception thrown if the callback function returns an illegal value.
	 * An illegal value would be a phrase length <code>L &gt;= n</code> 
	 * or a zero (empty) length by a UDT designated as non-empty.
	 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
	 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
	 */
	public int executeUdt(int id, int offset) throws Exception{
		int ret = -1;
		if( id < 0 || id >= rnms.length){
			throw new IllegalArgumentException(
					"Parser: udtExecuteUdt: UDT id="+id+
					": id must be >= 0 and less than number of UDTs="+udts.length);
		}
		if(offset < sysData.offset || offset > sysData.inputString.length){
			throw new IllegalArgumentException(
					"Parser: udtExecuteUdt: UDT id="+id+": offset = "+offset+
					"; offset must be >= "+sysData.offset+
					" and less than the input string length "+
							sysData.inputString.length);
		}
		Opcode.Udt ubie = udts[id];
		int saveOffset = sysData.offset;
		sysData.offset = offset;
		ubie.execute(sysData);
		if(sysData.match){ret = sysData.offset - offset;}
		sysData.offset = saveOffset;
		return ret;
	}
	/**
	 * <i>NOTE: This function should only be called from UdtCallback or RuleCallback
	 * callback functions. Results will be unpredictable if called otherwise.</i><p>
	 * Adds a rule node to the AST.
	 * May alter the grammar the AST is representing and should only be used with care.
	 * @param id grammar id of the rule name node to add.
	 * @param offset offset into the input string to the first character of the 
	 * phrase this node has matched.
	 * @param length length of the matched phrase.
	 * Must not be larger than the remaining input string length.
	 * @param match is not used.
	 * @throws IllegalArgumentException thrown if the id is out of range or the 
	 * @throws Exception thrown if the callback function returns an illegal value.
	 * An illegal value would be a phrase length <code>L &gt;= n</code>,
	 * where <code>n</code> is the remaining length of the input string,
	 * or a zero (empty) length by a UDT designated as non-empty.
	 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
	 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
	 */
	public void addAstRuleNode(int id, int offset, int length, boolean match)
			throws IllegalArgumentException, Exception{
		if((sysData.ast != null)){
			if(id >= rnms.length){
				throw new IllegalArgumentException(
					"Parser: udtAddAstRuleNode: rule id must be less than "+rnms.length);
			}
			int remainingStringLength = sysData.inputString.length - offset; 
			if(length > remainingStringLength){
				throw new IllegalArgumentException(
					"Parser: udtAddAstRuleNode: phrase length must be less than remaining length of input string: "+
					remainingStringLength);
			}
			sysData.ast.down(false, id, sysData.ast.rules[id].NAME, offset);
			sysData.ast.up(false, id, sysData.ast.rules[id].NAME, offset, length);
		}
	}

	/**
	 * <i>NOTE: This function should only be called from UdtCallback or RuleCallback
	 * callback functions. Results will be unpredictable if called otherwise.</i><p>
	 * Adds a UDT node to the AST.
	 * May alter the grammar the AST is representing and should only be used with care.
	 * @param id grammar id of the UDT name node to add.
	 * @param offset offset into the input string to the first character of the 
	 * phrase this node has matched.
	 * @param length length of the matched phrase.
	 * Must not be larger than the remaining input string length.
	 * @param match is not used.
	 * @throws IllegalArgumentException thrown if the id is out of range or the 
	 * @throws Exception thrown if the callback function returns an illegal value.
	 * An illegal value would be a phrase length <code>L &gt;= n</code>, 
	 * where <code>n</code> is the remaining length of the input string,
	 * or a zero (empty) length by a UDT designated as non-empty.
	 * (UDTs having names beginning with "<code>u_</code>" are designated as non-empty.
	 * UDTs having names beginning with "<code>e_</code>" are designated as empty.)
	 */
	public void addAstUdtNode(int id, int offset, int length, boolean match)
			throws IllegalArgumentException, Exception{
		if((sysData.ast != null)){
			if(id >= udts.length){
				throw new IllegalArgumentException(
					"Parser: udtAddAstUdtNode: UDT id must be less than "+udts.length);
			}
			int remainingStringLength = sysData.inputString.length - offset; 
			if(length > remainingStringLength){
				throw new IllegalArgumentException(
					"Parser: udtAddAstUdtNode: phrase length must be less than remaining length of input string: "+
					remainingStringLength);
			}
			sysData.ast.down(true, id, sysData.ast.udts[id].NAME, offset);
			sysData.ast.up(true, id, sysData.ast.udts[id].NAME, offset, length);
		}
	}

	// package API
	class SystemData {
		int startRule = 0;
		char[] inputString = null;
		CallbackData callbackData = new CallbackData();
		int treeDepth = 0;
		int inPredicate = 0;
		int maxMatchedLength = 0;
		int offset = 0;
		boolean match = false;
		Ast ast = null;
		Trace trace = null;
		Statistics stats = null;
		void clear(){
			inPredicate = 0;
			maxMatchedLength = 0;
			treeDepth = 0;
			offset = 0;
			match = false;
			callbackData.inputString = inputString;
			if(ast != null){ast.clear();}
			if(trace != null){trace.clear();}
			if(stats != null){stats.clear();}
		}
	}
	
	Result getResult(){return new Result(sysData.inputString,
			sysData.match,
			sysData.offset,
			sysData.maxMatchedLength);}

	// private functions
	private void initInstance(Grammar grammar) {
		this.grammar = grammar;
		Grammar.OpcodeAlt galt;
		Grammar.OpcodeCat gcat;
		Grammar.OpcodeRep grep;
		Grammar.OpcodeAnd gand;
		Grammar.OpcodeNot gnot;
		Opcode.Alt alt;
		Opcode.Cat cat;
		Opcode.Rep rep;
		Opcode.And and;
		Opcode.Not not;

		// create the rule list
		int count = grammar.getRuleCount();
		rnms = new Opcode.Rnm[count];
		for(int i = 0; i < count; i++){
			Grammar.Rule r = grammar.getRule(i);
			rnms[r.ID] = new Opcode.Rnm();
			rnms[r.ID].id = r.ID;
			rnms[r.ID].name = r.NAME;
			rnms[r.ID].child = null;
			rnms[r.ID].callback = null;
		}
		
		// create the UDT list
		count = grammar.getUdtCount();
		udts = new Opcode.Udt[count];
		for(int i = 0; i < count; i++){
			Grammar.Udt r = grammar.getUdt(i);
			udts[r.ID] = new Opcode.Udt();
			udts[r.ID].id = r.ID;
			udts[r.ID].name = r.NAME;
			udts[r.ID].mayBeEmpty = r.MAYBEEMPTY;
			udts[r.ID].callback = null;
		}
		
		// first pass creates the list of opcodes
		count = grammar.getOpcodeCount();
		opcodes = new Opcode[count];
		for(int i = 0; i < count; i++){
			int temp;
			Grammar.Opcode op = grammar.getOpcode(i);
			switch(op.type){
			case ALT:
				opcodes[i] = new Opcode.Alt();
				break;
			case CAT:
				opcodes[i] = new Opcode.Cat();
				break;
			case REP:
				opcodes[i] = new Opcode.Rep();
				break;
			case AND:
				opcodes[i] = new Opcode.And();
				break;
			case NOT:
				opcodes[i] = new Opcode.Not();
				break;
			case RNM:
				temp = ((Grammar.OpcodeRnm)op).id;
				opcodes[i] = rnms[temp];
				break;
			case UDT:
				temp = ((Grammar.OpcodeUdt)op).id;
				opcodes[i] = udts[temp];
				break;
			case TBS:
				opcodes[i] = new Opcode.Tbs(((Grammar.OpcodeTbs)op).string);
				break;
			case TLS:
				opcodes[i] = new Opcode.Tls(((Grammar.OpcodeTls)op).string);
				break;
			case TRG:
				opcodes[i] = new Opcode.Trg(((Grammar.OpcodeTrg)op).min, ((Grammar.OpcodeTrg)op).max);
				break;
			}
		}
		
		// fill in the rnm opcode reference
		for(int i = 0; i < rnms.length; i++){
			Grammar.Rule r = grammar.getRule(i);
			rnms[i].child = opcodes[r.CHILD];
		}

		// second pass fills in the opcode references
		for(int i = 0; i < count; i++){
			Grammar.Opcode op = grammar.getOpcode(i);
			switch(op.type){
			case ALT:
				galt = (Grammar.OpcodeAlt)op;
				alt = (Opcode.Alt)opcodes[i];
				alt.children = new Opcode[galt.children.length];
				for(int j = 0, n = galt.children.length; j < n; j++){
					alt.children[j] = opcodes[galt.children[j]];
				}
				break;
			case CAT:
				gcat = (Grammar.OpcodeCat)op;
				cat = (Opcode.Cat)opcodes[i];
				cat.children = new Opcode[gcat.children.length];
				for(int j = 0, n = gcat.children.length; j < n; j++){
					cat.children[j] = opcodes[gcat.children[j]];
				}
				break;
			case REP:
				grep = (Grammar.OpcodeRep)op;
				rep = (Opcode.Rep)opcodes[i];
				rep.min = grep.min;
				rep.max = grep.max;
				rep.child = opcodes[grep.child];
				break;
			case AND:
				gand = (Grammar.OpcodeAnd)op;
				and = (Opcode.And)opcodes[i];
				and.child = opcodes[gand.child];
				break;
			case NOT:
				gnot = (Grammar.OpcodeNot)op;
				not = (Opcode.Not)opcodes[i];
				not.child = opcodes[gnot.child];
				break;
			}
		}
	}
}
/*
 * Optional user-defined rule name callback function.
 * An extension of this class must be defined for each RNM call back function.
 * An object of this class must be set with the Parser function setRuleCallback(RuleCallback object)
 * <p>
 * note: User should make no changes to userString.
 *       UserinputString is a copy of the Parser's inputString and any changes made to it
 *       will have no effect on the Parser. However, the results of future callback functions
 *       is unpredictable.
 * @param preTraversal If true, the callback function is being called before traversal
 *                     of the branch below this node.
 *                     If false, the callback function is being called after traversal
 *                     of the branch below this node.
 * @param id id of the rule
 * @param name name of the rule
 * @param inputString the input string (see note)
 * @param offset offset from the beginning of the input string 
 * to the first character of the string to match
 * @param phrase If preTraversal is true, phrase is the output of the callback function.
 *                 If preTraversal is false, phrase is input to the callback function.
 * @return If true, the callback function will be treated as a terminal node.
 *           For the pre-traversal direction, the output will be used as the phrase matching results
 *           and the syntax tree traversal below this node will be aborted.
 *           If false the output is ignored and the syntax tree traversal
 *           will continue normally.
 */

/*
 * User-defined terminal callback function.
 * Mandatory for each UDT name in the defining grammar syntax.
 * An extension of this class must be defined for each UDT call back function.
 * An object of this class must be set with the Parser function setUdtCallback(UdtCallback object).
 * User data to be used by the call back function must be defined prior to parsing
 * with a call to Parser.setUserData(Object myData).
 * 
 * @param CallbackData callbackData
 * input:
 * int    id          = UDT id
 * String name        = UDT name
 * char[] inputString = the input string being parsed
 * int    offset      = offset to the phrase beginning
 * Object myData      = user-defined data specified in Parser.setUserData(Object myData)
 * 
 * User should make no changes to input data other than "myData".
 * Any changes made will be ignored by the Parser(*).
 * 
 * (*): Any changes made to input data will be ignored by the Parser.
 *      inputString is a private copy of the Parser's inputString.
 *      User should make no changes to userString.
 *      However, any changes made will affect future call back functions,
 *      but not the normal APG parse tree traversal.
 *       
 * return value: > 0, matched phrase of length value 
 *               = 0, matched empty phrase, if empty property = false(**)
 *               = 0, no phrase matched, if empty property = true(**)
 *               < 0, no phrase was matched
 *               
 * (**) Parser enforces the UDT empty property. 
 * UDT names beginning with "u_" have the property "empty = false",
 * those beginning with "e_" have the property "empty = true".
 * This is necessary to prevent an unintentional left-recursive grammar from
 * executing an infinite recursion (OS will probably exceed is limiting depth on the call stack.)
 * 
 */
