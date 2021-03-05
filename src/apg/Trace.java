package apg;

import java.io.PrintStream;
import java.util.Stack;
/**
 * The Trace class will display the exact path followed by the parser on its
 * journey through the syntax tree.
 * It will generate a detailed information record for every operator node
 * the Parser visits (hits). 
 * Separate records are generated for the pre-branch node hit 
 * (just prior to visiting the branch below)
 * and the post-branch node hit (just after visiting the branch below.)
 * <p>
 * Methods are available
 * for controlling which nodes to create records for and for displaying the records
 * after the parse has completed.
 * The enabling/disabling of operator nodes is incremental. A few examples will illustrate.
 * <p>
 * Suppose you wanted to display all operator nodes except the UDT operator nodes.
 * This could be accomplished with<br>
 * <code>
 * enableAllNodes(true);<br>
 * enableAllUdtNodes(false;)<br>
 * </code>
 * <p>
 * Or suppose you wanted to display only the operator nodes for rules named
 * "alpha", with grammar id=3, and "beta", with grammar id=5<br>
 * <code>
 * enableAllNodes(false);<br>
 * enableRule(3, true);<br>
 * enableRule(5, true);<br>
 * </code>
 * <p>
 * Or suppose you wanted to display all operator nodes <i>except for</i> rules named
 * "alpha", with grammar, id=3 and "beta", with grammar id=5<br>
 * <code>
 * enableAllNodes(true);<br>
 * enableRule(3, false);<br>
 * enableRule(5, false);<br>
 * </code>
 * <p>
 * Optionally, the trace records may be generated as an XML file. The user can then
 * use his/her favorite XML parser to peruse the trace.
 */
public class Trace {
	// public API
	/**
	 * Resets the set of operator nodes to trace to the default set.
	 * The default set is to trace all rule name and all UDT operator nodes only.
	 * All other operators are not trace.
	 */
	public void enableDefaultNodes(){
		enableAllNodes(false);
		enableAllRules(true);
		enableAllUdts(true);
	}
	/**
	 * Enables or disables <i>all</i> operator nodes.
	 * @param enable if <code>true</code> all operator nodes will be traced, if <code>false</code> none will be trace.
	 */
	public void enableAllNodes(boolean enable){
		alt = enable;
		cat = enable;
		rep = enable;
		and = enable;
		not = enable;
		trg = enable;
		tbs = enable;
		tls = enable;
		for(int i = 0; i < ruleCount; i++){rnmList[i] = enable;}
		for(int i = 0; i < udtCount; i++){udtList[i] = enable;}
	}
	/**
	 * Enables or disables the tracing of all rule name operators.
	 * @param enable if <code>true</code> all rule name operators will be traced,
	 * if <code>false</code> no rule name operators will be trace.
	 */
	public void enableAllRules(boolean enable){
		for(int i = 0; i < ruleCount; i++){rnmList[i] = enable;}
	}
	/**
	 * Enables or disables the tracing of all UDT operator nodes.
	 * @param enable if <code>true</code> all UDT operator nodes will be traced,
	 * if <code>false</code> no UDT operators will be trace.
	 */
	public void enableAllUdts(boolean enable){
		for(int i = 0; i < udtCount; i++){udtList[i] = enable;}
	}
	/**
	 * Enables or disables the tracing of all terminal operators.
	 * Terminal nodes are literal string operators (<code>TLS</code>),
	 * binary string operators (<code>TBS</code>) 
	 * and character range operators (<code>TRG</code>)
	 * @param enable if <code>true</code> all terminal operators will be traced,
	 * if <code>false</code> no terminal operators will be trace.
	 */
	public void enableAllTerminals(boolean enable){
		trg = enable;
		tbs = enable;
		tls = enable;
		for(int i = 0; i < udtCount; i++){udtList[i] = enable;}
	}
	/**
	 * Enables or disables the tracing of all non-terminal operators.
	 * Non-terminal operators are alternation operators (<code>ALT</code>),
	 * concatenation operators (<code>CAT</code>), 
	 * repetition operators (<code>REP</code>), 
	 * <code>"and"</code> syntactic predicate operators (<code>AND</code>) 
	 * and <code>"not"</code> syntactic predicate operators (<code>NOT</code>)
	 * @param enable if <code>true</code> all non-terminal operators will be traced,
	 * if <code>false</code> no non-terminal operators will be traced.
	 */
	public void enableAllNonTerminals(boolean enable){
		alt = enable;
		cat = enable;
		rep = enable;
		and = enable;
		not = enable;
	}
	/**
	 * Enables or disables the tracing of the named nodes.
	 * @param enable if <code>true</code> the named operator nodes will be traced,
	 * if <code>false</code> the named operators will not be traced.
	 * @param name name of the operator type to enable or disable<br>
	 * <code>"alt"</code> enable/disable alternation (<code>ALT</code>) operators<br>
	 * <code>"cat"</code> enable/disable concatenation (<code>CAT</code>) operators<br>
	 * <code>"rep"</code> enable/disable repetition (<code>REP</code>) operators<br>
	 * <code>"and"</code> enable/disable "and" syntactic predicate (<code>AND</code>) operators<br>
	 * <code>"not"</code> enable/disable "not" syntactic predicate (<code>NOT</code>) operators<br>
	 * @throws Exception thrown if operator type name is unrecognized (none of the above)
	 */
	public void enableNode(boolean enable, String name) throws Exception{
		if(name.equalsIgnoreCase("alt")){alt = enable;}
		else if(name.equalsIgnoreCase("cat")){cat = enable;}
		else if(name.equalsIgnoreCase("rep")){rep = enable;}
		else if(name.equalsIgnoreCase("and")){and = enable;}
		else if(name.equalsIgnoreCase("not")){not = enable;}
		else{ throw new Exception("Unrecognized node type" +
				"valid node types are \"alt\", \"cat\", \"rep\", \"and\"and \"not\"");}
	}
	/**
	 * Enables or disables the tracing of individual rule name operators.
	 * @param enable if <code>true</code> the named rule name operators will be traced,
	 * if <code>false</code> the named rule name operators will not be traced.
	 * @param id the grammar id of the rule to enable/disable
	 * @throws Exception thrown if the rule id is out of range
	 */
	public void enableRule(boolean enable, int id) throws Exception{
		if(id < 0 || id >= rnmList.length){
			throw new Exception("Trace: enableRule: id out of range");
		}
		rnmList[id] = enable;
	}
	/**
	 * Enables or disables the tracing of individual UDT operators.
	 * @param enable if <code>true</code> the named UDT operators will be traced,
	 * if <code>false</code> the named UDT operators will not be traced.
	 * @param id the grammar id of the UDT to enable/disable
	 * @throws Exception thrown if the UDT id is out of range
	 */
	public void enableUdt(boolean enable, int id) throws Exception{
		if(id < 0 || id >= udtList.length){
			throw new Exception("Trace: enableUdt: id out of range");
		}
		udtList[id] = enable;
	}

	/**
	 * Enable or disable XML as the trace record format.
	 * @param enabled if <code>true</code>, XML format will be enabled,
	 * if <code>false</code> the native APG format will be used.
	 */
	public void enableXml(boolean enabled){enableXml = enabled;}
	/**
	 * Sets the output device to record the trace records on.
	 * They are not recorded in internal RAM memory.
	 * <p>
	 * An example of the XML records is given here:
	 * <pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;TRACE_ROOT tag="0"&gt;
&lt;opcode tag="152" type="CAT" children="3"&gt;
 &lt;input length="5"&gt;(a+b)&lt;/input&gt;
 [child nodes]
 &lt;state opcodeTag="152" match="true"&gt;MATCH&lt;/state&gt;
 &lt;phrase length="5"&gt;(a+b)&lt;/phrase&gt;
&lt;/opcode&gt;
&lt;/TRACE_ROOT&gt;

NOTES:
 1.0 Encoding is always UTF-8.
 2.0 A special root node "TRACE_ROOT" is always added to ensure a root node that
     is the parent of all other nodes.
 3.0 The pre-branch visit to the node displays two records:
     1) The &lt;opcode&gt; record, tag is the record number and type is the node type.
     2) The &lt;input&gt; record has the text of the remaining string to be parsed.
 4.0 The post-branch visit to the node displays two or three records:
     1) The &lt;state&gt; record, opcodeTag is the tag of the pre-branch &lt;opcode&gt;
        and match = true/false is the parser state.
     2) The &lt;phrase&gt; record gives the matched phrase, if match is true.
     3) The end tag &lt;/opcode&gt;  
	 * </pre>
	 * @param out the output device. The default device is <code>System.out</code>.
	 */
	public void setOut(PrintStream out){
		this.out = out;
		writer.setOut(out);
	}
	
	// package-only API
	// constructor
	Trace(Parser parser, Grammar grammar){
		this.parser = parser;
		ruleCount = grammar.getRuleCount();
		udtCount = grammar.getUdtCount();
		suffixMaxChars = 128;
		if(ruleCount > 0){rnmList = new boolean[ruleCount];}
		else {rnmList = null;}
		if(udtCount > 0){udtList = new boolean[udtCount];}
		else {udtList = null;}
		enableDefaultNodes();
	}
	
	void clear(){
		treeDepth = 0;
		tag = 0;
		stack.clear();
	}
	
	void beginTrace(char[] string) throws Exception{
		if(enableXml){
			writer.declaration();
			String[] tagAttrs = {"tag", String.format("%d", tag)};
			writer.startTag(treeDepth, traceRoot, tagAttrs);
			
			stack.push(tag);

			String[] inputAttrs = {"length", String.format("%d", string.length)};
			writer.textTag(treeDepth+1, "input", inputAttrs,
					Utilities.charArrayToString(string, 0, -1, Integer.MAX_VALUE));

		} else{
			out.println(String.format("  tag: this opcode record number"));
			out.println(String.format("optag: opposite record number"));
			out.println(String.format("    d: tree depth"));
			out.println(String.format("    s: state, ~ open, N no match, E empty, M match"));
			out.println(String.format("%5s:%5s:%3s:s:", "tag", "optag", "d"));
			out.println(String.format("%5s:%5s:%3s:-:", "-----", "-----", "---"));
			out.print(String.format("%5d:%5s:%3d:", tag, "--", treeDepth));
			out.print(Utilities.parserStateToString(true, false, 0));
			out.print(":");
			out.print(Utilities.indent(treeDepth));
			out.print(traceRoot + "()");
			out.println(Utilities.charArrayToString(string, 0, -1, suffixMaxChars));
			stack.push(tag);
		}
		treeDepth++;
	}
	void endTrace(char[] string) throws Exception{
		int opcodeTag;
		Parser.Result result = parser.getResult();
		boolean match = result.success();
		int length = result.getMaxMatchedPhraseLength();
		if(enableXml){
			opcodeTag = stack.pop();
			String[] stateAttrs = {"opcodeTag", String.format("%d", opcodeTag)};
			if(match){
				if(length == 0){
					writer.textTag(treeDepth, "state", stateAttrs, "EMPTY");
				}
				else{
					writer.textTag(treeDepth, "state", stateAttrs, "MATCH");
					String[] phraseAttrs = {"length", String.format("%d", result.getMaxMatchedPhraseLength())};
					writer.textTag(treeDepth, "phrase", phraseAttrs,
							Utilities.charArrayToString(string, 0, result.getMaxMatchedPhraseLength(), Integer.MAX_VALUE));
				}
			} else{
				writer.textTag(treeDepth, "state", stateAttrs, "NO MATCH");
			}
			treeDepth--;
			writer.endTag(treeDepth, traceRoot);
		} else{
			opcodeTag = stack.pop();
			treeDepth--;
			out.print(String.format("%5d:%5d:%3d:", ++tag, opcodeTag, treeDepth));
			out.print(Utilities.parserStateToString(false, match, length));
			out.print(":");
			out.print(Utilities.indent(treeDepth));
			out.print(traceRoot + "()");
			if(match){
				out.print(Utilities.charArrayToString(string, 0, length, suffixMaxChars));
			}
			out.println("");
			out.println(String.format("%5s:%5s:%3s:-:", "-----", "-----", "---"));
			out.println(String.format("%5s:%5s:%3s:s:", "tag", "optag", "d"));
			out.println(String.format("  tag: this opcode record number"));
			out.println(String.format("optag: opposite record number"));
			out.println(String.format("    d: tree depth"));
			out.println(String.format("    s: state, ~ open, N no match, E empty, M match"));
		}
	}

	// pre-branch trace record
	void traceDown(char[] string, int offset, Opcode opcode) throws Exception{
		if(isNodeEnabled(opcode)){
			if(enableXml){
				tag++;
				stack.push(tag);
				String[] opcodeAttrs = opcodeXMLAttrs(tag, opcode);
				writer.startTag(treeDepth, "opcode", opcodeAttrs);
				String[] inputAttrs = {"length", String.format("%d", string.length - offset)};
				writer.textTag(treeDepth+1, "input", inputAttrs, 
						Utilities.charArrayToString(string, offset, -1, Integer.MAX_VALUE));
			} else {
				out.print(String.format("%5d:%5s:%3d:", ++tag, "--", treeDepth));
				out.print(Utilities.parserStateToString(true, false, 0));
				out.print(":");
				out.print(Utilities.indent(treeDepth));
				out.print(opcode.toString());
				out.println(Utilities.charArrayToString(string, offset, -1, suffixMaxChars));
				stack.push(tag);
			}
			treeDepth++;
		}
	}

	// post-branch trace record
	void traceUp(char[] string, int offset, int length, boolean match, Opcode opcode) throws Exception{
		if(isNodeEnabled(opcode)){
			if(enableXml){
				int opcodeTag = stack.pop();
				String[] stateAttrs = {"opcodeTag", String.format("%d", opcodeTag), "match", String.format("%b", match)};
				if(match){
					if(length == 0){writer.textTag(treeDepth, "state", stateAttrs, "EMPTY");}
					else{
						writer.textTag(treeDepth, "state", stateAttrs, "MATCH");
						String[] phraseAttrs = {"length", String.format("%d", length)};
						writer.textTag(treeDepth, "phrase", phraseAttrs,
								Utilities.charArrayToString(string, offset, length, Integer.MAX_VALUE));
					}
				} else{writer.textTag(treeDepth, "state", stateAttrs, "NO MATCH");}
				treeDepth--;
				writer.endTag(treeDepth, "opcode");

			} else{
				int opcodeTag = stack.pop();
				treeDepth--;
				out.print(String.format("%5d:%5d:%3d:", ++tag, opcodeTag, treeDepth));
				out.print(Utilities.parserStateToString(false, match, length));
				out.print(":");
				out.print(Utilities.indent(treeDepth));
				out.print(opcode.toString());
				if(match){out.print(Utilities.charArrayToString(string, offset, length, suffixMaxChars));}
				out.println("");
			}
		}
	}
	
	// private
	private Parser parser;
	private String traceRoot = "TRACE_ROOT";
	private PrintStream out = System.out;
	private Utilities.XMLWriter writer = new Utilities.XMLWriter(out);
	private int treeDepth = 0;
	private int tag = 0;
	private Stack<Integer> stack = new Stack<Integer>();
	private final int ruleCount;
	private final int udtCount;
	private final int suffixMaxChars;
	private boolean enableXml = false;

	// default trace options (user must provide his own code to change them)
	private boolean alt;
	private boolean cat;
	private boolean rep;
	private boolean and;
	private boolean not;
	private boolean trg;
	private boolean tbs;
	private boolean tls;
	private boolean[] rnmList;
	private boolean[] udtList;

	private String[] opcodeXMLAttrs(int tag, Opcode opcode) throws Exception{
		String[] ret;
		String type = "type";
		char[] string;
		switch(opcode.type){
		case ALT:
			ret = new String[6];
			ret[2] = type; ret[3] = "ALT";
			ret[4] = "children"; ret[5] = String.format("%d", ((Opcode.Alt)opcode).children.length);
			break;
		case CAT:
			ret = new String[6];
			ret[2] = type; ret[3] = "CAT";
			ret[4] = "children"; ret[5] = String.format("%d", ((Opcode.Cat)opcode).children.length);
			break;
		case AND:
			ret = new String[4];
			ret[2] = type; ret[3] = "AND";
			break;
		case NOT:
			ret = new String[4];
			ret[2] = type; ret[3] = "NOT";
			break;
		case REP:
			ret = new String[8];
			ret[2] = type; ret[3] = "REP";
			ret[4] = "min"; ret[5] = String.format("%d", (int)((Opcode.Rep)opcode).min);
			ret[6] = "max"; ret[7] = String.format("%d", (int)((Opcode.Rep)opcode).max);
			break;
		case RNM:
			ret = new String[8];
			ret[2] = type; ret[3] = "RNM";
			ret[4] = "id"; ret[5] = String.format("%d", ((Opcode.Rnm)opcode).id);
			ret[6] = "name"; ret[7] = ((Opcode.Rnm)opcode).name;
			break;
		case UDT:
			ret = new String[8];
			ret[2] = type; ret[3] = "UDT";
			ret[4] = "id"; ret[5] = String.format("%d", ((Opcode.Udt)opcode).id);
			ret[6] = "name"; ret[7] = ((Opcode.Udt)opcode).name;
			break;
		case TBS:
			string = ((Opcode.Tbs)opcode).string;
			ret = new String[8];
			ret[2] = type; ret[3] = "TBS";
			ret[4] = "length"; ret[5] = String.format("%d", string.length);
			ret[6] = "name"; ret[7] = Utilities.charArrayToXml(string, 0, -1);
			break;
		case TLS:
			string = ((Opcode.Tls)opcode).string;
			ret = new String[8];
			ret[2] = type; ret[3] = "TLS";
			ret[4] = "length"; ret[5] = String.format("%d", string.length);
			ret[6] = "name"; ret[7] = Utilities.charArrayToXml(string, 0, -1);
			break;
		case TRG:
			ret = new String[8];
			ret[2] = type; ret[3] = "TRG";
			ret[4] = "min"; ret[5] = String.format("%d", (int)((Opcode.Trg)opcode).min);
			ret[6] = "max"; ret[7] = String.format("%d", (int)((Opcode.Trg)opcode).max);
			break;
		default:
			throw new Exception("Trace: opcodeXMLAttrs: unknown opcode type");
		}
		ret[0] = "tag"; ret[1] = String.format("%d", tag);
		return ret;
	}
	
	// examine options, return true = display trace for this opcode, return false = don't display
	private boolean isNodeEnabled(Opcode opcode){
		boolean ret = false;
		switch(opcode.getType()){
		case ALT:
			ret = alt;
			break;
		case CAT:
			ret = cat;
			break;
		case REP:
			ret = rep;
			break;
		case AND:
			ret = and;
			break;
		case NOT:
			ret = not;
			break;
		case RNM:
			ret = rnmList[((Opcode.Rnm)opcode).id];
			break;
		case TRG:
			ret = trg;
			break;
		case TBS:
			ret = tbs;
			break;
		case TLS:
			ret = tls;
			break;
		case UDT:
			ret = udtList[((Opcode.Udt)opcode).id];
			break;
		}
		return ret;
	}
}
//