/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

import java.util.Stack;
import java.io.PrintStream;

/**
 * A class representation of the parser-generated Abstract Syntax Tree (AST).
 * <p>
 * The functions in this class allow the user to define which nodes appear in
 * the AST, traversal of the AST performing user-defined callback functions for
 * translation of the parsed sentence, and output of the AST in native or XML
 * format.
 * </p>
 * <p>
 * With the XML format the user is freed from the APG translation convention and
 * may use the XML parser of his/her choice for translation. The XML generated
 * is
 * <a href="http://www.w3schools.com/xml/xml_validator.asp" target="_blank">valid</a>
 * but has no Document Type Definition (DTD). The structure of an XML AST is:
 * </p>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;AST_ROOT id=&quot;6&quot; depth=&quot;0&quot;&gt;
 * &lt;records thisRecord=&quot;0&quot; oppositeRecord=&quot;105&quot;/&gt;
 * &lt;phrase length=&quot;22&quot;&gt;<i>the entire input string</i>&lt;/phrase&gt;
 * &lt;node-name id=&quot;4&quot; depth=&quot;1&quot;&gt;
 * &lt;records thisRecord=&quot;1&quot; oppositeRecord=&quot;9&quot;/&gt;
 * &lt;phrase length=&quot;25&quot;&gt;<i>text of recognized phrase</i>&lt;/phrase&gt;
 * [child nodes]
 * &lt;records thisRecord=&quot;9&quot; oppositeRecord=&quot;1&quot;/&gt;
 * &lt;/node-name&gt;
 * &lt;records thisRecord=&quot;105&quot; oppositeRecord=&quot;0&quot;/&gt;
 * &lt;/AST_ROOT&gt;
 *
 * open tag:
 * node-name - the grammar rule or UDT name
 * id        - the grammar identifier of the rule or UDT
 * depth     - the AST tree depth of the node
 * records: there are two AST records for each node, pre-branch and post-branch
 * pre-branch record:
 * thisRecord     - the record number of the pre-branch record
 * oppositeRecord - the record number of the post-branch record
 * phrase: content is the text of the phrase recognized by this node.
 * length: the number of characters in the phrase
 * post-branch record:
 * thisRecord     - the record number of the post-branch record
 * oppositeRecord - the record number of the pre-branch record
 * close tag:
 *
 * Notes:
 * 1. Only UTF-8 encoding is used.
 * 2. The root node is always AST_ROOT. This is generated to satisfy the
 * XML requirement of a root node that is the parent of all other nodes.
 * If the start rule is not included in the AST, this requirement is
 * not otherwise guaranteed. The underscore insures that the name
 * will never conflict with any rule or UTD name.
 * 3. The "thisRecord/oppositeRecord" numbers are often useful for locating the opposite
 * record, especially when there are a large number of child nodes between them.
 * 4. The phrase text is displayed in UTF-8 encoding. Only the ASCII characters
 * newline (10) and printing characters (32-126) are used.
 * XML character entity notation is used only for the pre-defined entity characters.
 * The character reference notation, <i>&amp;#xhhhh;</i>, is used for all
 * non-newline control characters and all other non-printing characters.
 * </pre>
 */
public class Ast {
    // public API

    /**
     * The parser provisions each callback function with an instance of this
     * class. giving the user access to the input string and optionally any
     * other user-defined data that the function may need to do its job.
     */
    public class CallbackData {

        /**
         * User-defined data that the user sets prior to parsing with the parser
         * function setMyData(). Otherwise, null.
         */
        public Object myData = null;
        /**
         * The sentence or input string (as a char array) being parsed. Any
         * changes made to this string during translation will render the
         * results of the translation unpredictable.
         */
        public char[] inputString = null;
    }

    /**
     * The base class for all AST callback functions. It is very similar to the
     * callback functions used for semantic actions called by the Parser.
     */
    public static abstract class AstCallback {

        /**
         * Provides user access to the sentence being parsed, as well as any
         * user-defined data that may be necessary.
         */
        public final CallbackData callbackData;

        /**
         *
         * @param ast an instance of the AST this callback function is to be
         * attached to.
         */
        public AstCallback(Ast ast) {
            this.callbackData = ast.callbackData;
        }

        /**
         * This function is called when this AST node is reached the first time
         * and before the AST branch below this node is traversed.
         *
         * @param offset offset into the input string of the first character of
         * the phrase matched by this node.
         * @param length the number of characters in the matched phrase.
         * @return if <code>true</code> AST traversal will skip processing of
         * all nodes in the AST branch below this node. if <code>false</code>
         * AST traversal continues normally.
         */
        public boolean preBranch(int offset, int length) {
            return false;
        }

        /**
         * This function is called when this AST node is reached the second and
         * final time and after the AST branch below this node has been
         * traversed.
         *
         * @param offset offset into the input string of the first character of
         * the phrase matched by this node.
         * @param length the number of characters in the matched phrase.
         */
        public abstract void postBranch(int offset, int length);
    }

    /**
     * Ast assigns a root node to each AST named AST_ROOT. The underscore
     * insures that it will never conflict with an node name. The AST and its
     * XML representation is always required to have a root node that is the
     * last ancestor of every other node in the tree. Since the user may choose
     * not to have the start rule in the AST, an assigned root node is
     * necessary.
     * <p>
     * With this function the user may assign a callback function for the root
     * node.
     *
     * @param callback the callback function to attach to the root node.
     */
    public void setRootCallback(AstCallback callback) {
        astNodes[astRootId].callback = callback;
    }

    /**
     * Used to set a callback function for the identified rule.
     *
     * @param id identifier of the rule to set the callback for. This is the id
     * assigned to the rule in the generated Grammar class with the ruleID()
     * function.
     * @param callback the user-defined callback function for this rule node.
     * Must be an extension of the AstCallback class.
     * @throws Exception Exceptions are thrown if the id is out of range,
     * <code>0-(ruleCount - 1)</code> or id this rule node has not been set
     * (@see {@link #enableRuleNode})
     */
    public void setRuleCallback(int id, AstCallback callback) throws Exception {
        if (id < 0 || id >= ruleCount) {
            throw new IllegalArgumentException("rule callback rule ID out of bounds");
        }
        if (!astNodes[id].enabled) {
            throw new Exception("AST node not set for this rule: id: "
                    + id + " name: " + rules[id].NAME);
        }
        astNodes[id].callback = callback;
    }

    /**
     * Used to set a callback function for the identified UDT.
     *
     * @param id identifier of the rule to set the callback for. This is the id
     * assigned to the rule in the generated Grammar class with the udtID()
     * function.
     * @param callback the user-defined callback function for this rule node.
     * Must be an extension of the AstCallback class.
     * @throws Exception Exceptions are thrown if the id is out of range,
     * <code>0-(udtCount - 1)</code> or id this UDT node has not been set (@see
     * {@link #enableUdtNode})
     */
    public void setUdtCallback(int id, AstCallback callback) throws Exception {
        if (id < 0 || id >= udtCount) {
            throw new IllegalArgumentException("UDT callback rule ID out of bounds");
        }
        int astId = id + ruleCount;
        if (!astNodes[astId].enabled) {
            throw new Exception("AST node not set for this UDT: id: "
                    + id + " name: " + udts[id].NAME);
        }
        astNodes[astId].callback = callback;
    }

    /**
     *
     * @param id identifier of the rule to be enabled in the AST.
     * @param enable if <code>true</code>, the rule node will be retained in the
     * AST, if <code>false</code>, the rule node will never appear in the AST.
     * @throws Exception Exceptions are thrown if the id is out of range,
     * <code>0-(ruleCount - 1)</code>
     */
    public void enableRuleNode(int id, boolean enable) throws Exception {
        if (id < 0 || id >= ruleCount) {
            throw new IllegalArgumentException("rule callback rule ID out of bounds");
        }
        astNodes[id].enabled = enable;
    }

    /**
     *
     * @param id identifier of the UDT to be enabled in the AST.
     * @param enable if <code>true</code>, the UDT node will be retained in the
     * AST, if <code>false</code>, the UDT node will never appear in the AST.
     * @throws Exception Exceptions are thrown if the id is out of range,
     * <code>0-(udtCount - 1)</code>
     */
    public void enableUdtNode(int id, boolean enable) throws Exception {
        if (id < 0 || id >= udtCount) {
            throw new IllegalArgumentException("UDT callback rule ID out of bounds");
        }
        astNodes[ruleCount + id].enabled = enable;
    }

    /**
     *
     * @param data any user-defined class object or null.
     */
    public void setMyData(Object data) {
        callbackData.myData = data;
    }

    /**
     * Traverse the AST and call the user-defined callback functions at each
     * node. Nodes must have been identified with a call to enableRuleNode() or
     * enableUdtNode(). Additionally, callback functions must have been set for
     * the nodes with calls to setRuleCallback() or setUdtCallback();
     *
     * @see Ast#enableRuleNode(int, boolean)
     * @see Ast#enableUdtNode(int, boolean)
     * @see Ast#setRuleCallback(int, AstCallback)
     * @see Ast#setUdtCallback(int, AstCallback)
     */
    public void translateAst() {
        boolean skip = false;
        int skipDepth = 0;
        for (int i = 0; i < stack.size(); i++) {
            Record record = stack.get(i);
            AstCallback callback = astNodes[record.astId].callback;
            if (callback != null) {
                if (record.up) {
                    if (skip && skipDepth == record.depth) {
                        skip = false;
                    }
                    if (!skip) {
                        callback.postBranch(record.offset, record.length);
                    }
                } else {
                    if (!skip) {
                        skip = callback.preBranch(record.offset, record.length);
                        if (skip) {
                            skipDepth = record.depth;
                        }
                    }
                }
            }
        }
    }

    /**
     * Display the AST in native APG format.
     *
     * @param out PrintStream to display the AST on.
     */
    public void display(PrintStream out) {
        display(out, false);
    }

    /**
     * Display the AST in native APG format or XML format.
     *
     * @param out PrintStream to display the AST on.
     * @param xml if <code>true</code>, format is XML, if <code>false</code>,
     * format is native APG.
     */
    public void display(PrintStream out, boolean xml) {
        if (xml) {
            displayXml(out);
        } else {
            displayAst(out);
        }
    }

    // package-only visible API
    Grammar.Rule[] rules; // package visible because parser udtAddAstRecord() needs to access them            
    Grammar.Udt[] udts;  // package visible because parser udtAddAstRecord() needs to access them  

    Ast(Parser parser, Grammar grammar) {
        this.parser = parser;
        stack = new Stack<Record>();
        treeDepthStack = new Stack<Record>();
        ruleCount = grammar.getRuleCount();
        rules = new Grammar.Rule[ruleCount];
        udtCount = grammar.getUdtCount();
        astRootId = ruleCount + udtCount;
        astNodeCount = ruleCount + udtCount + 1;
        astNodes = new AstNode[astNodeCount];
        int astId = 0;
        for (int i = 0; i < ruleCount; i++) {
            rules[i] = grammar.getRule(i);
            astNodes[astId] = new AstNode();
            astNodes[astId].name = rules[i].NAME;
            astId++;
        }
        if (udtCount > 0) {
            udts = new Grammar.Udt[ruleCount];
            for (int i = 0; i < udtCount; i++) {
                udts[i] = grammar.getUdt(i);
                astNodes[astId] = new AstNode();
                astNodes[astId].name = udts[i].NAME;
                astId++;
            }
        } else {
            udts = null;
        }
        astNodes[astRootId] = new AstNode();
        astNodes[astRootId].enabled = true;
        astNodes[astRootId].name = astRootName;
    }

    void clear() {
        stack.clear();
        treeDepthStack.clear();
    }

    void beginAst() {
        // open the root record
        down(false, astRootId, astRootName, 0);
    }

    void endAst() {
        // close the root record
        Parser.Result result = parser.getResult();
        if (result.success()) {
            up(false, astRootId, astRootName, 0, callbackData.inputString.length);
        } else {
            clear();
        }
    }

    // used by Opcode.execute() to delete ast nodes from backtraced branches
    class StackSize {

        private int stackSize;
        private int treeDepthStackSize;
    }

    StackSize getStackSize() {
        StackSize size = new StackSize();
        size.stackSize = stack.size();
        size.treeDepthStackSize = treeDepthStack.size();
        return size;
    }

    void setStackSize(StackSize size) {
        stack.setSize(size.stackSize);
        treeDepthStack.setSize(size.treeDepthStackSize);
    }

    void setInputString(String input) {
        setInputString(input.toCharArray());
    }

    void setInputString(char[] input) {
        callbackData.inputString = input;
    }

    void down(boolean udt, int id, String name, int offset) {
        // create the down record
        int astId = udt ? ruleCount + id : id;
        if (astNodes[astId].enabled) {
            Record record = new Record();
            record.up = false;
            record.udt = udt;
            record.id = id;
            record.astId = astId;
            record.depth = treeDepthStack.size();
            record.name = name;
            record.offset = offset;
            record.length = 0;
            record.thisRecord = stack.size();
            record.otherRecord = 0;

            // push the record on the stacks
            treeDepthStack.push(record);
            stack.push(record);
        }
    }

    void up(boolean udt, int id, String name, int offset, int length) {
        int astId = udt ? ruleCount + id : id;
        if (astNodes[astId].enabled) {
            Record downRecord = treeDepthStack.pop();
            Record record = new Record();
            record.up = true;
            record.udt = udt;
            record.id = id;
            record.name = name;
            record.astId = astId;
            record.depth = treeDepthStack.size();
            record.offset = offset;
            record.length = length;
            record.thisRecord = stack.size();
            record.otherRecord = downRecord.thisRecord;
            stack.push(record);
            downRecord.otherRecord = record.thisRecord;
            downRecord.length = record.length;
        }
    }

    // private
    private final static String astRootName = "AST_ROOT";
    private int astRootId;
    private Parser parser;
    private Stack<Record> stack;
    private Stack<Record> treeDepthStack;
    private int ruleCount;
    private int udtCount;
    private int astNodeCount;
    private AstNode[] astNodes;
    private CallbackData callbackData = new CallbackData();

    private class Record {

        boolean up; //true if direction is up, otherwise direction is down
        boolean udt; // true if the node is a UDT node
        int id; // RNM or UDT id
        String name; // RNM or UDT name
        int astId;
        int depth;
        int length;
        int offset;
        int thisRecord;
        int otherRecord;
    }

    private class AstNode {

        boolean enabled = false;
        String name = null;
        int astId = -1;
        AstCallback callback = null;
    }

    private void displayAst(PrintStream out) {
        int treeDepth = 0;
        int maxChars = 132;
        if (stack.size() == 0) {
            out.println(String.format("<no AST records - parse did not succeed>"));
        } else {
            out.println(String.format("  tag: record number of this rule/UDT"));
            out.println(String.format("optag: record number of opposite rule/UDT"));
            out.println(String.format("  len: phrase length"));
            out.println(String.format("    d: tree depth"));
            out.println(String.format("    s: state, E empty, M match"));
            out.println(String.format("%5s:%5s:%3s:%3s: s: name, id, phrase", "tag", "optag", "len", "d"));
            out.println(String.format("%5s:%5s:%3s:%3s:--:-----------------", "-----", "-----", "---", "---"));
            for (int i = 0; i < stack.size(); i++) {
                Record record = stack.get(i);
                if (record.up) {
                    treeDepth--;
                    out.print(String.format("%5d:%5d:%3d:%3d: ",
                            record.thisRecord, record.otherRecord, record.length, treeDepth));
                    out.print(Utilities.parserStateToString(false, true, record.length));
                    out.print(": ");
                    out.print(Utilities.indent(treeDepth));
                    out.print(String.format("%s, ", record.name));
                    out.print(String.format("%d : ", record.id));
                    out.print(Utilities.charArrayToString(callbackData.inputString, record.offset, record.length, maxChars));
                } else {
                    out.print(String.format("%5d:%5d:%3d:%3d: ",
                            record.thisRecord, record.otherRecord, record.length, treeDepth));
                    out.print(Utilities.parserStateToString(false, true, record.length));
                    out.print(": ");
                    out.print(Utilities.indent(treeDepth));
                    out.print(String.format("%s, ", record.name));
                    out.print(String.format("%d : ", record.id));
                    out.print(Utilities.charArrayToString(callbackData.inputString, record.offset, record.length, maxChars));
                    treeDepth++;
                }
                out.print("\n");
            }
            out.println(String.format("%5s:%5s:%3s:%3s:--:-----------------", "-----", "-----", "---", "---"));
            out.println(String.format("%5s:%5s:%3s:%3s: s: name, id, phrase", "tag", "optag", "len", "d"));
            out.println(String.format("  tag: record number of this rule/UDT"));
            out.println(String.format("optag: record number of opposite rule/UDT"));
            out.println(String.format("  len: phrase length"));
            out.println(String.format("    d: tree depth"));
            out.println(String.format("    s: state, E empty, M match"));
        }
    }

    // transform AST to XML, UTF-8 encoding
    private void displayXml(PrintStream out) {
        try {
            int depth = 0;
            Utilities.XMLWriter writer = new Utilities.XMLWriter(out);
            if (stack.size() > 0) {
                writer.declaration();
                for (Record record : stack) {
                    if (!record.up) {
                        // write record name  tag
                        String[] nameAttrs = {"id", String.format("%d", record.id), "depth", String.format("%d", depth)};
                        writer.startTag(depth, record.name, nameAttrs);

                        // record numbers tag
                        String[] recordAttrs = {"thisRecord", String.format("%d", record.thisRecord),
                            "oppositeRecord", String.format("%d", record.otherRecord)};
                        writer.textTag(depth + 1, "records", recordAttrs, null);

                        // phrase tag
                        String[] phraseAttrs = {"length", String.format("%d", record.length)};
                        writer.textTag(depth + 1, "phrase", phraseAttrs,
                                Utilities.charArrayToXml(callbackData.inputString, record.offset, record.length));
                        depth++;
                    } else {
                        // record numbers tag
                        String[] recordAttrs = {"thisRecord", String.format("%d", record.thisRecord),
                            "oppositeRecord", String.format("%d", record.otherRecord)};
                        writer.textTag(depth, "records", recordAttrs, null);
                        depth--;
                        // write end tag;
                        writer.endTag(depth, record.name);
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
