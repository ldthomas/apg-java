/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

import apg.Parser.*;

abstract class Opcode {

    enum Type {
        ALT, CAT, REP, AND, NOT, RNM, UDT, TRG, TBS, TLS;
    }

    protected Type type;

    Type getType() {
        return type;
    }

    void execute(SystemData data) throws Exception {

        // pre-execution
        data.treeDepth++;
        Ast.StackSize astSize = null;
        int offset = data.offset;
        if (data.trace != null) {
            data.trace.traceDown(data.inputString, offset, this);
        }
        if (data.ast != null) {
            astSize = data.ast.getStackSize();
        }

        // execute the opcode-type-specific operations
        executeOpcode(data);

        // post-execution
        if (data.maxMatchedLength < data.offset) {
            data.maxMatchedLength = data.offset;
        }
        if (data.trace != null) {
            data.trace.traceUp(data.inputString, offset, data.offset - offset, data.match, this);
        }
        if (data.ast != null && !data.match) {
            data.ast.setStackSize(astSize);
        }
        if (data.stats != null) {
            data.stats.incrementOperator(type, data.match, data.offset - offset);
        }
        data.treeDepth--;
    }

    /**
     *
     * @param data
     *
     * executeOpcode must return: SystemData.match = true/false
     * SystemData.offset = offset to first unmatched string character (eg. input
     * offset + matched phrase length)
     */
    abstract void executeOpcode(SystemData data) throws Exception;

    @Override
    public String toString() {
        return "Opcode(" + getType() + ")";
    }

    static final class Alt extends Opcode {

        Opcode[] children;

        Alt() {
            type = Type.ALT;
        }

        void executeOpcode(SystemData data) throws Exception {
            for (int i = 0, n = children.length; i < n; i++) {
                children[i].execute(data);
                if (data.match) {
                    break;
                } // prioritized-choice - returns first successful child state and phrase
//				if(data.stats != null){data.stats.incrementBacktrack(type);} // backtracks on unsuccessful phrase match
            }
        }

        @Override
        public String toString() {
            return String.format("ALT(%d)", children.length);
        }
    }

    static final class Cat extends Opcode {

        Opcode[] children;

        Cat() {
            type = Type.CAT;
        }

        void executeOpcode(SystemData data) throws Exception {
            int backtrackOffset = data.offset;
            for (int i = 0, n = children.length; i < n; i++) {
                children[i].execute(data);
                if (!data.match) {
                    data.offset = backtrackOffset; // backtrack on failure
                    break; // CAT fails if any child fails
                }
            }
        }

        @Override
        public String toString() {
            return String.format("CAT(%d)", children.length);
        }
    }

    static final class Rep extends Opcode {

        Opcode child;
        char min, max;

        Rep() {
            type = Type.REP;
        }

        void executeOpcode(SystemData data) throws Exception {
            int reps;
            boolean empty = false;
            int backtrackOffset = data.offset;
            for (reps = 0; reps < (int) max; reps++) {
                int childOffset = data.offset;
                child.execute(data);
                if (data.match && childOffset == data.offset) {
                    empty = true;
                    break; // EMPTY ends the loop
                } else if (!data.match) {
//					if(data.stats != null){data.stats.incrementBacktrack(type);} // NOMATCH backtracks over failed child
                    break;// NOMATCH ends the loop (REP may or may not succeed)
                }
            }

            // evaluate the success of the repetition
            if (empty) {
                data.match = true;
            }//REP always succeeds on empty regardless of min,max values
            else if ((reps >= (int) min) && (reps <= (int) max)) {
                data.match = true;
            }// number of matched reps OK
            else {
                data.match = false;
                data.offset = backtrackOffset; // REP failed - backtrack
            }
        }

        @Override
        public String toString() {
            return String.format("REP(%%x%02X-%02X)", (int) min, (int) max);
        }
    }

    static final class And extends Opcode {

        Opcode child;

        And() {
            type = Type.AND;
        }

        void executeOpcode(SystemData data) throws Exception {
            int backtrackOffset = data.offset;
            data.inPredicate += 1;
            child.execute(data);
            data.inPredicate -= 1;
            data.offset = backtrackOffset;// AND always backtracks & returns child state
        }

        @Override
        public String toString() {
            return String.format("AND");
        }
    }

    static final class Not extends Opcode {

        Opcode child;

        Not() {
            type = Type.NOT;
        }

        void executeOpcode(SystemData data) throws Exception {
            int backtrackOffset = data.offset;
            data.inPredicate += 1;
            child.execute(data);
            data.inPredicate -= 1;
            data.match = !data.match;// NOT returns the toggled the child state
            data.offset = backtrackOffset;// NOT always backtracks
        }

        @Override
        public String toString() {
            return String.format("NOT");
        }
    }

    static final class Rnm extends Opcode {

        int id;
        String name;
        RuleCallback callback;
        Opcode child;

        Rnm() {
            type = Type.RNM;
        }

        void executeOpcode(SystemData data) throws Exception {
            int offset = data.offset;
            int returnLength = -1;

            if (data.ast != null && data.inPredicate == 0) {
                // pre-traversal AST handler
                data.ast.down(false, id, name, offset);
            }

            if (callback != null) {
                // pre-branch user callback
                returnLength = callback.preBranch(data.offset);
                if (returnLength >= 0) {
                    // callback function has performed a terminal node operation, overrides parse
                    if (returnLength > (data.inputString.length - data.offset)) {
                        throw new Exception("Rnm Opcode[" + name
                                + "] error: returned phrase length[" + returnLength
                                + "] greater than remaining input string length["
                                + (data.inputString.length - data.offset) + "]");
                    }
                    data.match = true;
                    data.offset += returnLength;
                } // RHA modified from here
                else if (returnLength == -2) {
                    // override parser, rejecting any possible phrase match
                    data.match = false;
                    data.offset = offset;
                } // RHA modified to here
                else {
                    child.execute(data);
                }// ignore callback function result
            } else {
                child.execute(data);
            }// no callback function - normal traversal of child subtree

            if (callback != null && returnLength < 0) {
                // post-branch user callback & pre-branch callback was NOT a terminal operation
                int phraseLength = -1;
                if (data.match) {
                    phraseLength = data.offset - offset;
                }
                returnLength = callback.postBranch(offset, phraseLength);
                if (returnLength >= 0) {
                    // callback acting as terminal node - override the rule result
                    data.match = true;
                    data.offset = offset + returnLength;
                } // RHA modified from here
                else if (returnLength == -2) {
                    // override parser, reject matched phrase
                    data.match = false;
                    data.offset = offset;
                }
                // RHA modified to here
            }

            if (data.ast != null && data.inPredicate == 0) {
                // post-traversal AST handler
                data.ast.up(false, id, name, offset, data.offset - offset);
            }

            if (data.stats != null) {
                data.stats.incrementRnmOperator(data.match, data.offset - offset);
                data.stats.incrementRuleNameOperator(id, data.match, data.offset - offset);
            }
        }

        @Override
        public String toString() {
            return String.format("RNM(%d, %s)", id, name);
        }
    }

    static final class Udt extends Opcode {

        int id;
        String name;
        boolean mayBeEmpty;
        UdtCallback callback;

        Udt() {
            type = Type.UDT;
        }

        void executeOpcode(SystemData data) throws Exception {
            int astOffset = data.offset;
            int phraseLength = 0;
            if (data.ast != null && data.inPredicate == 0) {
                data.ast.down(true, id, name, astOffset);
            }
            if (data.offset >= data.inputString.length) {
                data.match = mayBeEmpty;
            }// handle EOS as empty string
            else {
                phraseLength = callback.callback(data.offset);
                if (phraseLength > (data.inputString.length - data.offset)) {
                    throw new Exception("UDT Opcode[" + name
                            + "] error: returned phrase length[" + phraseLength
                            + "] greater than remaining input string length["
                            + (data.inputString.length - data.offset) + "]");
                }
                if (phraseLength < 0) {
                    data.match = false;
                } else if (phraseLength == 0) {
                    data.match = mayBeEmpty;
                } // enforce empty string specification (e_udtName)
                else {
                    data.match = true;
                    data.offset += phraseLength;
                }
            }

            if (data.ast != null && data.inPredicate == 0) {
                data.ast.up(true, id, name, astOffset, phraseLength);
            }

            if (data.stats != null) {
                data.stats.incrementUdtOperator(data.match, phraseLength);
                data.stats.incrementUdtNameOperator(id, data.match, phraseLength);
            }
        }

        @Override
        public String toString() {
            return String.format("UDT(%d, %s)", id, name);
        }
    }

    static final class Trg extends Opcode {

        char min, max;

        Trg(char min, char max) {
            this.type = Type.TRG;
            this.min = min;
            this.max = max;
        }

        void executeOpcode(SystemData data) {
            data.match = false;
            if (data.offset < data.inputString.length) {
                if (data.inputString[data.offset] >= min && data.inputString[data.offset] <= max) {
                    data.match = true;
                    data.offset += 1;
                }
            }
        }

        @Override
        public String toString() {
            return String.format("TRG(x%02X-%02X)", (int) min, (int) max);
        }
    }

    static final class Tbs extends Opcode {

        char[] string;

        Tbs(char[] string) {
            this.type = Type.TBS;
            this.string = string;
        }

        void executeOpcode(SystemData data) {
            int i;
            while (true) {
                if (string.length == 0) {
                    data.match = true;
                    break;
                }
                if (string.length > (data.inputString.length - data.offset)) {
                    data.match = false; // end of string - unsuccessful phrase match
                    break;
                }
                for (i = 0; i < string.length; i++) {
                    if (data.inputString[data.offset + i] != string[i]) {
                        data.match = false; // unsuccessful phrase match
                        break;
                    }
                }
                if (i == string.length) {
                    data.match = true;
                    data.offset += string.length; // success
                }
                break;
            }
        }

        @Override
        public String toString() {
            String out = String.format("TBS(%d, %%x", string.length);
            out += String.format("%x", (int) string[0]);
            for (int i = 1; i < string.length; i++) {
                out += String.format(".%x", (int) string[i]);
            }
            out += ")";
            return out;
        }
    }

    static final class Tls extends Opcode {

        char[] string;

        Tls(char[] string) {
            this.type = Type.TLS;
            this.string = string;
        }

        void executeOpcode(SystemData data) {
            int i;
            while (true) {
                if (string.length > (data.inputString.length - data.offset)) {
                    data.match = false; // end of string - unsuccessful phrase match
                    break;
                }
                for (i = 0; i < string.length; i++) {
                    char cInput = data.inputString[data.offset + i];
                    if (cInput >= 65 && cInput <= 90) {
                        cInput += 32;
                    } // convert to lower case
                    if (cInput != string[i]) {
                        data.match = false; // unsuccessful phrase match
                        break;
                    }
                }
                if (i == string.length) {
                    data.match = true;
                    data.offset += string.length; // success
                }
                break;
            }
        }

        @Override
        public String toString() {
            String out = String.format("TLS(%d, ", string.length);
            for (int i = 0; i < string.length; i++) {
                out += String.format("%c", (int) string[i]);
            }
            out += ")";
            return out;
        }
    }
}
