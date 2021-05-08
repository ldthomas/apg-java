/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

/*
		@Override public int preBranch(int offset){
			return -1;
		}
		@Override public int postBranch(int offset, int length){
			return -1;
		}
 */
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Stack;
import java.util.TreeMap;

import apg.Opcode.Type;
import apg.Parser.RuleCallback;
import apg.Utilities.*;

/*
 * Defines the syntax call back functions for the SABNF grammar.
 * Defines subclasses used to build the opcodes
 */
class GeneratorSyntax {

    static Vector<String> errors;
    static Vector<String> warnings;
    static LineCatalog lineCatalog;
    static int currentID;
    static SyntaxRule currentRule;
    // RHA: state variables for incremental alternatives
    static boolean currentRuleIsIncremented;
    static boolean nextAlternativeIsIncremental;
    static Vector<SyntaxRule> rules;
    static Vector<SyntaxRule> udts;
    static Vector<SyntaxOpcode> opcodes;
    static TreeMap<String, Integer> ruleMap;
    static TreeMap<String, Integer> udtMap;
    static Stack<SyntaxOpcode> parentStack;
    static Vector<Character> tbs;

//	GeneratorSyntax(Vector<String> errors, Vector<String> warnings,
//			LineCatalog catalog){
//		GeneratorSyntax.errors = errors;
//		GeneratorSyntax.warnings = warnings;
//		GeneratorSyntax.lineCatalog = catalog;
//		GeneratorSyntax.currentID = 0;
//		GeneratorSyntax.currentRule = new SyntaxRule();
//		GeneratorSyntax.ruleMap = new TreeMap<String, Integer>();
//		GeneratorSyntax.rules = new Vector<SyntaxRule>();
//		GeneratorSyntax.opcodes = new Stack<SyntaxOpcode>();
//		GeneratorSyntax.udtMap = new TreeMap<String, Integer>();
//		GeneratorSyntax.udts = new Vector<SyntaxRule>();
//		GeneratorSyntax.parentStack = new Stack<SyntaxOpcode>();
//		GeneratorSyntax.tbs = new Vector<Character>();
//	}
    static void initSyntax(Vector<String> errors, Vector<String> warnings,
            LineCatalog catalog) {
        GeneratorSyntax.errors = errors;
        GeneratorSyntax.warnings = warnings;
        GeneratorSyntax.lineCatalog = catalog;
        GeneratorSyntax.currentID = 0;
        GeneratorSyntax.currentRule = new SyntaxRule();
        GeneratorSyntax.ruleMap = new TreeMap<String, Integer>();
        GeneratorSyntax.rules = new Vector<SyntaxRule>();
        GeneratorSyntax.opcodes = new Stack<SyntaxOpcode>();
        GeneratorSyntax.udtMap = new TreeMap<String, Integer>();
        GeneratorSyntax.udts = new Vector<SyntaxRule>();
        GeneratorSyntax.parentStack = new Stack<SyntaxOpcode>();
        GeneratorSyntax.tbs = new Vector<Character>();
    }

    static class SyntaxRule {

        int errorsReported;
        int lineno;
        String name;
        int id;
        boolean mayBeEmpty;
        int opcodeOffset;
        int opcodeCount;

        void clear() {
            errorsReported = 0;
            lineno = -1;
            name = null;
            id = 0;
            mayBeEmpty = false;
            opcodeOffset = 0;
            opcodeCount = 0;
        }

        SyntaxRule(SyntaxRule rule) {
            this.errorsReported = rule.errorsReported;
            this.lineno = rule.lineno;
            this.name = rule.name;
            this.id = rule.id;
            this.mayBeEmpty = rule.mayBeEmpty;
            this.opcodeOffset = rule.opcodeOffset;
            this.opcodeCount = rule.opcodeCount;
        }

        public SyntaxRule() {
            clear();
        }

        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("rule name: ");
            buf.append(name);
            buf.append("\n");
            buf.append("  rule id: ");
            buf.append(id);
            buf.append("\n");
            buf.append("  line no: ");
            buf.append(lineno);
            buf.append("\n");
            buf.append("  opcodeOffset: ");
            buf.append(opcodeOffset);
            buf.append("\n");
            buf.append("  opcodeCount: ");
            buf.append(opcodeCount);
            buf.append("\n");
            return buf.toString();
        }
    }

    static class SyntaxOpcode {
        // all

        boolean root;
        int lineno;
        int index;
        Type type;
        Vector<SyntaxOpcode> childOpcodes = new Vector<SyntaxOpcode>();

        // RNM, UDT
        int id;
        String name;
        boolean mayBeEmpty; //(UDT)

        // AND, NOT
        boolean and;

        // REP, TRG
        int min, max;

        // TLS, TBS
        char[] string;

        private void printChildren(StringBuffer buf, Vector<SyntaxOpcode> c) {
            if (c == null) {
                return;
            }
            for (int i = 0; i < c.size(); i++) {
                if (i > 0) {
                    buf.append(", ");
                }
                buf.append(c.elementAt(i).index);
            }
        }

        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("[");
            buf.append(index);
            buf.append("]");
            switch (type) {
                case ALT:
                    buf.append("ALT(");
                    printChildren(buf, childOpcodes);
                    buf.append(")\n");
                    break;
                case CAT:
                    buf.append("CAT(");
                    printChildren(buf, childOpcodes);
                    buf.append(")\n");
                    break;
                case AND:
                    buf.append("AND(");
                    printChildren(buf, childOpcodes);
                    buf.append(")\n");
                    break;
                case NOT:
                    buf.append("NOT(");
                    printChildren(buf, childOpcodes);
                    buf.append(")\n");
                    break;
                case RNM:
                    buf.append("RNM(");
                    buf.append(name);
                    buf.append("[");
                    buf.append(id);
                    buf.append("], ");
                    buf.append(childOpcodes.elementAt(0).index);
                    buf.append(")\n");
                    break;
                case UDT:
                    buf.append("UDT(");
                    buf.append(name);
                    buf.append("[");
                    buf.append(id);
                    buf.append(", ");
                    buf.append(mayBeEmpty);
                    buf.append("])\n");
                    break;
                case REP:
                    buf.append("REP(");
                    buf.append("[");
                    buf.append(min);
                    buf.append(", ");
                    if (max == Integer.MAX_VALUE) {
                        buf.append("inf");
                    } else {
                        buf.append(max);
                    }
                    buf.append("], ");
                    printChildren(buf, childOpcodes);
                    buf.append(")\n");
                    break;
                case TRG:
                    buf.append("TRG(");
                    buf.append("[");
                    buf.append(min);
                    buf.append(", ");
                    buf.append(max);
                    buf.append("])\n");
                    break;
                case TLS:
                    buf.append("TLS(\"");
                    buf.append(new String(string));
                    buf.append("\")\n");
                    break;
                case TBS:
                    buf.append("TBS(");
                    buf.append(Utilities.charArrayToString(string, 0, string.length, 132));
                    buf.append(")\n");
                    break;
            }
            return buf.toString();
        }
    }

    private static final class RuleRefEntry {

        private int count;
        private String name;

        private RuleRefEntry(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }

    static class SyntaxMetrics {

        TreeMap<String, RuleRefEntry> ruleRefs = new TreeMap<String, RuleRefEntry>();
        TreeMap<String, RuleRefEntry> udtRefs = new TreeMap<String, RuleRefEntry>();
        int rules;
        int udts;
        int opcodes;
        int alt;
        int altChildren;
        int cat;
        int catChildren;
        int and;
        int not;
        int rep;
        int rnm;
        int udt;
        int trg;
        int tbs;
        int tls;

        SyntaxMetrics() {
            clear();
        }

        private void clear() {
            rules = 0;
            udts = 0;
            opcodes = 0;
            alt = 0;
            altChildren = 0;
            catChildren = 0;
            cat = 0;
            and = 0;
            not = 0;
            rep = 0;
            rnm = 0;
            udt = 0;
            trg = 0;
            tbs = 0;
            tls = 0;
        }

        @Override
        public String toString() {
            Set<Map.Entry<String, RuleRefEntry>> set;
            StringBuffer buf = new StringBuffer();
            buf.append("SyntaxMetrics:\n");
            buf.append("  rules: " + rules + "\n");
            buf.append("   udts: " + udts + "\n");
            buf.append("opcodes: " + opcodes + "\n");
            buf.append("    ALT: " + alt + " children: " + altChildren + "\n");
            buf.append("    CAT: " + cat + " children: " + catChildren + "\n");
            buf.append("    AND: " + and + "\n");
            buf.append("    NOT: " + not + "\n");
            buf.append("    REP: " + rep + "\n");
            buf.append("    RNM: " + (rnm) + "\n");
            buf.append("    UDT: " + udt + "\n");
            buf.append("    TRG: " + trg + "\n");
            buf.append("    TBS: " + tbs + "\n");
            buf.append("    TLS: " + tls + "\n");
            buf.append("\nRNM opcodes:\n");
            buf.append("count: Rule Name\n");
            set = ruleRefs.entrySet();
            for (Map.Entry<String, RuleRefEntry> entry : set) {
                RuleRefEntry value = entry.getValue();
                buf.append(String.format("%5d: %s", value.count, value.name));
                buf.append("\n");
            }

            set = udtRefs.entrySet();
            if (set.size() > 0) {
                buf.append("\nUDT opcodes:\n");
                buf.append("count: Udt Name\n");
                for (Map.Entry<String, RuleRefEntry> entry : set) {
                    RuleRefEntry value = entry.getValue();
                    buf.append(String.format("%5d: %s", value.count, value.name));
                    buf.append("\n");
                }
            }
            return buf.toString();
        }
    }

    private static void logError(int offset, String msg) {
        LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
        if (line == null) {
            errors.add("line[undefined]: " + msg);
        } else {
            errors.add("line[" + line.lineno + "]: " + msg);
        }
    }

// may be needed later	
//	private static void logWarning(int offset, String msg){
//		LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
//		if(line == null){warnings.add("line[undefined]: " + msg);}
//		else{warnings.add("line[" + line.lineno + "]: " + msg);}
//	}
    private static int decDigits(char[] string, int offset, int length) {
        int value = 0;
        int max = offset + length;
        for (int i = offset; i < max; i++) {
            value = 10 * value + (int) (string[i] - 48);
        }
        return value;
    }

    private static int hexDigits(char[] string, int offset, int length) {
        int value = 0;
        int max = offset + length;
        for (int i = offset; i < max; i++) {
            int digit = (int) string[i];
            if (digit < 58) {
                digit = digit - 48;
            } else if (digit < 71) {
                digit = digit - 65 + 10;
            } else {
                digit = digit - 97 + 10;
            }
            value = 16 * value + digit;
        }
        return value;
    }

    private static int binaryDigits(char[] string, int offset, int length) {
        int value = 0;
        int max = offset + length;
        for (int i = offset; i < max; i++) {
            value = 2 * value + (int) (string[i] - 48);
        }
        return value;
    }

    // syntax callback functions
    private static void addOpcode(SyntaxOpcode op) {
        opcodes.add(op);
        currentRule.opcodeCount++;
    }

    private static void removeOpcodes(SyntaxOpcode op) {
        // remove this opcode and all above it from the rule opcode vector
        int from = op.index;
        int to = opcodes.size();
        List<SyntaxOpcode> list = opcodes.subList(from, to);
        currentRule.opcodeCount -= list.size();
        opcodes.removeAll(list);
    }

    private static void addChildToParent(Stack<SyntaxOpcode> stack, SyntaxOpcode child) {
        if (!stack.empty()) {
            SyntaxOpcode parent = stack.peek();
            parent.childOpcodes.add(child);
        }
    }

    // callback functions
    static class GenFile extends RuleCallback {

        GenFile(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            // set the correct rule id in all RNM opcodes
            // RHA: previous code unnecessarily (?) relied on consecutive numbering
//			for(SyntaxRule rule : rules){
//				for(int i = rule.opcodeOffset; i < (rule.opcodeOffset + rule.opcodeCount); i++){
            for (int i = 0; i < opcodes.size(); i++) {
                SyntaxOpcode op = opcodes.elementAt(i);
                if (op.type == Opcode.Type.RNM) {
                    Integer value = ruleMap.get(op.name.toLowerCase());
                    if (value != null) {
                        SyntaxRule rnmRule = rules.elementAt(value);
                        SyntaxOpcode rnmOp = opcodes.elementAt(rnmRule.opcodeOffset + 1);
                        op.id = rnmRule.id;
                        op.childOpcodes.add(rnmOp);
                    }
                }
            }
//			}
            return -1;
        }
    }

    static class GenRule extends RuleCallback {

        GenRule(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            currentRule.clear();
            currentRule.opcodeOffset = opcodes.size();
            LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
            if (line != null) {
                currentRule.lineno = line.lineno;
            }
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // RHA: clean up after incrementing rule
            if (currentRuleIsIncremented) {
                currentRuleIsIncremented = false;
                currentRule = new SyntaxRule();
            } else if (length >= 0) {
                // put the current rule in the rule list
                currentRule.id = currentID;
                Integer previousRule = ruleMap.put(currentRule.name.toLowerCase(), currentID);
                if (previousRule != null) {
                    // report multiply-defined rule name error
                    logError(offset, "rule '" + currentRule.name
                            + "' is multiply defined");
                } else {
                    rules.add(new SyntaxRule(currentRule));
                    currentID++;
                }
            } else {
                // rule syntax failure
                if (offset < callbackData.inputString.length) {
                    logError(offset, "rule '" + currentRule.name + "' has syntax errors");
                }
            }
            return -1;
        }
    }

    static class NameDef extends RuleCallback {

        NameDef(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                currentRule.name = new String(callbackData.inputString, offset, length);
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                currentRule.lineno = line.lineno;
            }
            return -1;
        }
    }

    static class IncAlt extends RuleCallback {

        IncAlt(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            // RHA: find rule to increment and prepare incrementing it
            if (length >= 0) {
                SyntaxRule ruleToIncrement = null;
                for (SyntaxRule rule : rules) {
                    if (rule.name.equals(currentRule.name)) {
                        ruleToIncrement = rule;
                    }
                }
                if (ruleToIncrement == null) {
                    logError(offset, "rule '" + currentRule.name + "' to increment not defined");
                } else {
                    currentRule = ruleToIncrement;
                    currentRuleIsIncremented = true;
                    nextAlternativeIsIncremental = true;
                }
            }
            return -1;
        }
    }

    static class Rnm extends RuleCallback {

        Rnm(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.RNM;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;

                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                // get the rule name
                thisOpcode.name = new String(callbackData.inputString, offset, length);
                // add this opcode as a child to the parent opcode
                parentOpcode.childOpcodes.add(thisOpcode);
            }
            return -1;
        }
    }

    static class Udt extends RuleCallback {

        Udt(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.UDT;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;

                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // add the UDT to the list
                String thisPhrase = new String(callbackData.inputString, offset, length);
                thisPhrase = thisPhrase.toLowerCase();
                if (thisPhrase.charAt(0) == 'e') {
                    thisOpcode.mayBeEmpty = true;
                } else {
                    thisOpcode.mayBeEmpty = false;
                }
                Integer prev = udtMap.get(thisPhrase);
                if (prev == null) {
                    SyntaxRule temp = new SyntaxRule();
                    temp.id = udts.size();
                    temp.name = thisPhrase;
                    temp.mayBeEmpty = thisOpcode.mayBeEmpty;
                    udtMap.put(temp.name, temp.id);
                    udts.add(temp);
                    thisOpcode.id = temp.id;
                } else {
                    thisOpcode.id = prev;
                }

                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                // get the rule name
                thisOpcode.name = new String(callbackData.inputString, offset, length);
                // add this opcode as a child to the parent opcode
                parentOpcode.childOpcodes.add(thisOpcode);
            }
            return -1;
        }
    }

    static class Option extends RuleCallback {

        Option(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            // create this opcode
            SyntaxOpcode thisOpcode = new SyntaxOpcode();
            thisOpcode.type = Type.REP;
            thisOpcode.index = opcodes.size();
            thisOpcode.min = 0;
            thisOpcode.max = 1;
            LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
            thisOpcode.lineno = line.lineno;

            // add this opcode to current rule opcode vector
            addOpcode(thisOpcode);

            // add this opcode to parent stack
            parentStack.push(thisOpcode);
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // pop this opcode from the parent stack
            SyntaxOpcode thisOpcode = parentStack.pop();

            // get the parent opcode
            if (length >= 0) {
                addChildToParent(parentStack, thisOpcode);
            }// add this opcode as a child to the parent opcode
            else {
                removeOpcodes(thisOpcode);
            }
            return -1;
        }
    }

    static class Alternation extends RuleCallback {

        Alternation(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            if (nextAlternativeIsIncremental) {
                nextAlternativeIsIncremental = false;
                // RHA: set up environment for increment
                parentStack.push(opcodes.get(currentRule.opcodeOffset));
            } else {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.ALT;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;

                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // add this opcode to parent stack
                parentStack.push(thisOpcode);
            }
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // pop this opcode from the parent stack
            SyntaxOpcode thisOpcode = parentStack.pop();

            // get the parent opcode
            if (length >= 0) {
                addChildToParent(parentStack, thisOpcode);
            }// add this opcode as a child to the parent opcode
            else {
                removeOpcodes(thisOpcode);
            }
            return -1;
        }
    }

    static class Concatenation extends RuleCallback {

        Concatenation(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            // create this opcode
            SyntaxOpcode thisOpcode = new SyntaxOpcode();
            thisOpcode.type = Type.CAT;
            thisOpcode.index = opcodes.size();
            LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
            thisOpcode.lineno = line.lineno;

            // add this opcode to current rule opcode vector
            addOpcode(thisOpcode);

            // add this opcode to parent stack
            parentStack.push(thisOpcode);
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // pop this opcode from the parent stack
            SyntaxOpcode thisOpcode = parentStack.pop();

            // get the parent opcode
            if (length >= 0) {
                addChildToParent(parentStack, thisOpcode);
            }// add this opcode as a child to the parent opcode
            else {
                removeOpcodes(thisOpcode);
            }
            return -1;
        }
    }

    static class Repetition extends RuleCallback {

        Repetition(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            // create this opcode
            SyntaxOpcode thisOpcode = new SyntaxOpcode();
            thisOpcode.type = Type.REP;
            thisOpcode.index = opcodes.size();
            thisOpcode.min = -1;
            thisOpcode.max = -1;
            LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
            thisOpcode.lineno = line.lineno;

            // add this opcode to current rule opcode vector
            addOpcode(thisOpcode);

            // add this opcode to parent stack
            parentStack.push(thisOpcode);
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // pop this opcode from the parent stack
            SyntaxOpcode thisOpcode = parentStack.pop();

            // get the parent opcode
            if (length >= 0) {
                if (thisOpcode.min == -1) {
                    thisOpcode.min = 1;
                }
                if (thisOpcode.max == -1) {
                    thisOpcode.max = 1;
                }
                if (thisOpcode.min > thisOpcode.max) {
                    logError(offset, "Repitition(n*m) n["
                            + thisOpcode.min + "] must be greater than m["
                            + thisOpcode.max + "]");
                    currentRule.errorsReported++;
                }
                // add this opcode as a child to the parent opcode
                addChildToParent(parentStack, thisOpcode);
            } else {
                removeOpcodes(thisOpcode);
            }
            return -1;
        }
    }

    static class Rep extends RuleCallback {

        Rep(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // get the parent opcode
                SyntaxOpcode thisOpcode = parentStack.peek();
                if (thisOpcode.min == -1) {
                    thisOpcode.min = 0;
                }
                if (thisOpcode.max == -1) {
                    thisOpcode.max = Integer.MAX_VALUE;
                }
            }
            return -1;
        }
    }

    static class RepMin extends RuleCallback {

        RepMin(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length > 0) {
                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                parentOpcode.min = decDigits(callbackData.inputString, offset, length);
            }
            return -1;
        }
    }

    static class RepMax extends RuleCallback {

        RepMax(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length > 0) {
                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                parentOpcode.max = decDigits(callbackData.inputString, offset, length);
            }
            return -1;
        }
    }

    static class RepMinMax extends RuleCallback {

        RepMinMax(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length > 0) {
                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                parentOpcode.min = decDigits(callbackData.inputString, offset, length);
                parentOpcode.max = parentOpcode.min;
            }
            return -1;
        }
    }

    static class Predicate extends RuleCallback {

        Predicate(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            // create this opcode
            SyntaxOpcode thisOpcode = new SyntaxOpcode();
            thisOpcode.type = Type.AND;
            thisOpcode.index = opcodes.size();
            LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
            thisOpcode.lineno = line.lineno;

            // add this opcode to current rule opcode vector
            addOpcode(thisOpcode);

            // add this opcode to parent stack
            parentStack.push(thisOpcode);
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            // pop this opcode from the parent stack
            SyntaxOpcode thisOpcode = parentStack.pop();

            // get the parent opcode
            if (length >= 0) {
                addChildToParent(parentStack, thisOpcode);
            }// add this opcode as a child to the parent opcode
            else {
                removeOpcodes(thisOpcode);
            }
            return -1;
        }
    }

    static class And extends RuleCallback {

        And(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length > 0) {
                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                parentOpcode.type = Type.AND;
            }
            return -1;
        }
    }

    static class Not extends RuleCallback {

        Not(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length > 0) {
                // get the parent opcode
                SyntaxOpcode parentOpcode = parentStack.peek();
                parentOpcode.type = Type.NOT;
            }
            return -1;
        }
    }

    static class ProsVal extends RuleCallback {

        ProsVal(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                String value = new String(callbackData.inputString, offset, length);
                logError(offset, value + " - \"prose values\" not supported - aborting this rule");
                currentRule.errorsReported++;
            }
            return -1;
        }
    }

    static class Tls extends RuleCallback {

        Tls(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.TLS;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;
                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // remove the quotes
                int thisOffset = offset + 1;
                int ilength = length - 2;
                // remove leading %i
                if (callbackData.inputString[offset] == '%') {
                    thisOffset += 2;
                    ilength -= 2;
                }
                thisOpcode.string = new char[ilength];
                for (int i = 0; i < ilength; i++) {
                    thisOpcode.string[i] = callbackData.inputString[thisOffset + i];
                }
                // add this opcode as a child to the parent opcode
                addChildToParent(parentStack, thisOpcode);
            }
            return -1;
        }
    }

    static class Tcs extends RuleCallback {

        Tcs(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.TBS;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;
                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // remove the quotes
                int thisOffset = offset + 1;
                int ilength = length - 2;
                // remove leading %s
                if (callbackData.inputString[offset] == '%') {
                    thisOffset += 2;
                    ilength -= 2;
                }
                thisOpcode.string = new char[ilength];
                for (int i = 0; i < ilength; i++) {
                    thisOpcode.string[i] = callbackData.inputString[thisOffset + i];
                }
                // add this opcode as a child to the parent opcode
                addChildToParent(parentStack, thisOpcode);
            }
            return -1;
        }
    }

    static class Tbs extends RuleCallback {

        Tbs(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            tbs.clear();
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.TBS;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;
                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // pick up the char[] left by TbsString
                thisOpcode.string = new char[tbs.size()];
                int i = 0;
                for (char c : tbs) {
                    thisOpcode.string[i++] = c;
                }
                tbs.clear();
                addChildToParent(parentStack, thisOpcode);
            }
            return -1;
        }
    }

    static class DNum extends RuleCallback {

        DNum(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                char digit = (char) decDigits(callbackData.inputString, offset, length);
                tbs.add(digit);
            } else {
                logError(offset, " illegal decimal digit found");
                currentRule.errorsReported++;
            }
            return -1;
        }
    }

    static class XNum extends RuleCallback {

        XNum(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                char digit = (char) hexDigits(callbackData.inputString, offset, length);
                tbs.add(digit);
            } else {
                logError(offset, " illegal hexadecimal digit found");
                currentRule.errorsReported++;
            }
            return -1;
        }
    }

    static class BNum extends RuleCallback {

        BNum(Parser parser) {
            super(parser);
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                char digit = (char) binaryDigits(callbackData.inputString, offset, length);
                tbs.add(digit);
            } else {
                logError(offset, " illegal binary digit found");
                currentRule.errorsReported++;
            }
            return -1;
        }
    }

    static class Trg extends RuleCallback {

        Trg(Parser parser) {
            super(parser);
        }

        @Override
        public int preBranch(int offset) {
            tbs.clear();
            return -1;
        }

        @Override
        public int postBranch(int offset, int length) {
            if (length >= 0) {
                // create this opcode
                SyntaxOpcode thisOpcode = new SyntaxOpcode();
                thisOpcode.type = Type.TRG;
                thisOpcode.index = opcodes.size();
                LineCatalog.Line line = lineCatalog.getLineFromOffset(offset);
                thisOpcode.lineno = line.lineno;
                // add this opcode to current rule opcode vector
                addOpcode(thisOpcode);

                // pick up the char[] left by TrgRange
                thisOpcode.min = (int) tbs.elementAt(0);
                if (tbs.size() == 2) {
                    thisOpcode.max = (int) tbs.elementAt(1);
                } else {
                    thisOpcode.max = thisOpcode.min;
                }
                tbs.clear();
                addChildToParent(parentStack, thisOpcode);
            }
            return -1;
        }
    }

    // prune the intermediate opcodes (remove redundant ALT(1), CAT(1) REP(1,1))
    static boolean prune() {
        boolean ret = false;
        while (true) {
            if (rules.size() == 0) {
                break;
            }
            Vector<SyntaxOpcode> prunedOpcodes = new Vector<SyntaxOpcode>();
            for (SyntaxRule rule : rules) {
                int oldOffset = rule.opcodeOffset;
                parentStack.clear();
                rule.opcodeOffset = prunedOpcodes.size();
                opcodes.elementAt(oldOffset).root = true;
                traverse(prunedOpcodes, opcodes, oldOffset);
                rule.opcodeCount = prunedOpcodes.size() - rule.opcodeOffset;
            }
            opcodes = prunedOpcodes;

            // reset all RNM opcode children
            for (SyntaxOpcode op : opcodes) {
                if (op.type == Type.RNM) {
                    SyntaxRule rnmRule = rules.elementAt(op.id);
                    SyntaxOpcode child = opcodes.elementAt(rnmRule.opcodeOffset);
                    op.childOpcodes.add(child);
                }
            }
            ret = true;
            break;
        }
        return ret;
    }

    private static void traverse(Vector<SyntaxOpcode> newOpcodes,
            Vector<SyntaxOpcode> oldOpcodes, int oldIndex) {
        SyntaxOpcode oldOpcode = oldOpcodes.elementAt(oldIndex);
        SyntaxOpcode op;
        SyntaxOpcode parentOpcode;
        SyntaxOpcode singleChild;
        switch (oldOpcode.type) {
            case ALT:
            case CAT:
                if (oldOpcode.childOpcodes.size() > 1) {
                    op = new SyntaxOpcode();
                    op.index = newOpcodes.size();
                    op.lineno = oldOpcode.lineno;
                    op.type = oldOpcode.type;
                    newOpcodes.add(op);
                    parentStack.push(op);
                    for (SyntaxOpcode child : oldOpcode.childOpcodes) {
                        traverse(newOpcodes, oldOpcodes, child.index);
                    }
                    parentStack.pop();
                    if (!parentStack.empty()) {
                        parentOpcode = parentStack.peek();
                        parentOpcode.childOpcodes.add(op);
                    }
                } else {
                    singleChild = oldOpcode.childOpcodes.elementAt(0);
                    traverse(newOpcodes, oldOpcodes, singleChild.index);
                }
                break;
            case AND:
            case NOT:
                op = new SyntaxOpcode();
                op.index = newOpcodes.size();
                op.lineno = oldOpcode.lineno;
                op.type = oldOpcode.type;
                op.and = oldOpcode.and;
                newOpcodes.add(op);
                parentStack.push(op);
                singleChild = oldOpcode.childOpcodes.elementAt(0);
                traverse(newOpcodes, oldOpcodes, singleChild.index);
                parentStack.pop();
                if (!parentStack.empty()) {
                    parentOpcode = parentStack.peek();
                    parentOpcode.childOpcodes.add(op);
                }
                break;
            case TRG:
            case TLS:
            case TBS:
            case UDT:
                op = new SyntaxOpcode();
                op.index = newOpcodes.size();
                op.lineno = oldOpcode.lineno;
                op.type = oldOpcode.type;
                op.min = oldOpcode.min;
                op.max = oldOpcode.max;
                op.string = oldOpcode.string;
                op.id = oldOpcode.id;
                op.name = oldOpcode.name;
                op.mayBeEmpty = oldOpcode.mayBeEmpty;
                newOpcodes.add(op);
                if (!parentStack.empty()) {
                    parentOpcode = parentStack.peek();
                    parentOpcode.childOpcodes.add(op);
                }
                break;
            case REP:
                if (!(oldOpcode.min == 1 && oldOpcode.max == 1)) {
                    op = new SyntaxOpcode();
                    op.index = newOpcodes.size();
                    op.lineno = oldOpcode.lineno;
                    op.type = oldOpcode.type;
                    op.min = oldOpcode.min;
                    op.max = oldOpcode.max;
                    newOpcodes.add(op);
                    parentStack.push(op);
                    singleChild = oldOpcode.childOpcodes.elementAt(0);
                    traverse(newOpcodes, oldOpcodes, singleChild.index);
                    parentStack.pop();
                    if (!parentStack.empty()) {
                        parentOpcode = parentStack.peek();
                        parentOpcode.childOpcodes.add(op);
                    }
                } else {
                    singleChild = oldOpcode.childOpcodes.elementAt(0);
                    traverse(newOpcodes, oldOpcodes, singleChild.index);
                }
                break;
            case RNM:
                op = new SyntaxOpcode();
                op.index = newOpcodes.size();
                op.lineno = oldOpcode.lineno;
                op.type = oldOpcode.type;
                op.name = oldOpcode.name;
                op.id = oldOpcode.id;
                newOpcodes.add(op);
                if (oldOpcode.root) {
                    oldOpcode.root = false;
                    parentStack.push(op);
                    singleChild = oldOpcode.childOpcodes.elementAt(0);
                    traverse(newOpcodes, oldOpcodes, singleChild.index);
                    parentStack.pop();
                } else {
                    if (!parentStack.empty()) {
                        parentOpcode = parentStack.peek();
                        parentOpcode.childOpcodes.add(op);
                    }
                }
                break;
        }
    }

    static SyntaxMetrics metrics(Vector<SyntaxRule> rules,
            Vector<SyntaxRule> udts,
            Vector<SyntaxOpcode> opcodes) {
        SyntaxMetrics ret = new SyntaxMetrics();
        ret.rules = rules.size();
        ret.udts = udts.size();
        ret.opcodes = opcodes.size();

        // initialize the rules
        for (SyntaxRule rule : rules) {
            ret.ruleRefs.put(rule.name.toLowerCase(), new RuleRefEntry(rule.name, 0));
        }
        // initialize the udts
        for (SyntaxRule udt : udts) {
            ret.udtRefs.put(udt.name.toLowerCase(), new RuleRefEntry(udt.name, 0));
        }
        for (SyntaxRule rule : rules) {
            for (int i = rule.opcodeOffset; i < (rule.opcodeOffset + rule.opcodeCount); i++) {
                RuleRefEntry entry;
                SyntaxOpcode opcode = opcodes.elementAt(i);
                switch (opcode.type) {
                    case ALT:
                        ret.alt++;
                        ret.altChildren += opcode.childOpcodes.size();
                        break;
                    case CAT:
                        ret.cat++;
                        ret.catChildren += opcode.childOpcodes.size();
                        break;
                    case AND:
                        ret.and++;
                        break;
                    case NOT:
                        ret.not++;
                        break;
                    case TRG:
                        ret.trg++;
                        break;
                    case TLS:
                        ret.tls++;
                        break;
                    case TBS:
                        ret.tbs++;
                        break;
                    case UDT:
                        ret.udt++;
                        entry = ret.udtRefs.get(opcode.name.toLowerCase());
                        if (entry != null) {
                            entry.count = entry.count + 1;
                        } else {
                            logError(opcode.index, "UDT name: " + opcode.name + " referenced but not defined");
                        }
                        break;
                    case REP:
                        ret.rep++;
                        break;
                    case RNM:
                        ret.rnm++;
                        entry = ret.ruleRefs.get(opcode.name.toLowerCase());
                        if (entry != null) {
                            entry.count = entry.count + 1;
                        } else {
                            logError(opcode.index, "RNM name: " + opcode.name + " referenced but not defined");
                        }
                        break;
                }
            }
        }
        return ret;
    }
}
