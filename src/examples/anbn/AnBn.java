// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.
package examples.anbn;

import apg.Grammar;
import java.io.PrintStream;

/**
 * This class has been generated automatically from an SABNF grammar by the
 * {@link apg.Generator} class of Java APG, Version 1.1.<br>
 * It is an extension of the {@link apg.Grammar} class containing additional
 * members and enums not found in the base class.<br>
 * The function {@link #getInstance()} will return a reference to a static,
 * singleton instance of the class.
 * <br>Copyright (c) 2021 Lowell D. Thomas, all rights reserved<br>
 * <a href="https://opensource.org/licenses/BSD-2-Clause">2-Clause BSD
 * License</a>
 */

public class AnBn extends Grammar {

    // public API
    /**
     * Called to get a singleton instance of this class.
     *
     * @return a singleton instance of this class, cast as the base class,
     * Grammar.
     */
    public static Grammar getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new AnBn(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /**
     * The number of rules in the grammar
     */
    public static int ruleCount = 1;

    /**
     * This enum provides easy to remember enum constants for locating the rule
     * identifiers and names. The enum constants have the same spelling as the
     * rule names rendered in all caps with underscores replacing hyphens.
     */
    public enum RuleNames {
        /**
         * id = <code>0</code>, name = <code>"AnBn"</code>
         */
        ANBN("AnBn", 0, 0, 5);
        private String name;
        private int id;
        private int offset;
        private int count;

        RuleNames(String string, int id, int offset, int count) {
            this.name = string;
            this.id = id;
            this.offset = offset;
            this.count = count;
        }

        /**
         * Associates the enum with the original grammar name of the rule it
         * represents.
         *
         * @return the original grammar rule name.
         */
        public String ruleName() {
            return name;
        }

        /**
         * Associates the enum with an identifier for the grammar rule it
         * represents.
         *
         * @return the rule name identifier.
         */
        public int ruleID() {
            return id;
        }

        private int opcodeOffset() {
            return offset;
        }

        private int opcodeCount() {
            return count;
        }
    }

    // UDT name enum
    /**
     * The number of UDTs in the grammar
     */
    public static int udtCount = 0;

    /**
     * This enum provides easy to remember enum constants for locating the UDT
     * identifiers and names. The enum constants have the same spelling as the
     * UDT names rendered in all caps with underscores replacing hyphens.
     */
    public enum UdtNames {
    }

    // private
    private static AnBn factoryInstance = null;

    private AnBn(Rule[] rules, Udt[] udts, Opcode[] opcodes) {
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules() {
        Rule[] rules = new Rule[1];
        for (RuleNames r : RuleNames.values()) {
            rules[r.ruleID()] = getRule(r.ruleID(), r.ruleName(), r.opcodeOffset(), r.opcodeCount());
        }
        return rules;
    }

    private static Udt[] getUdts() {
        Udt[] udts = new Udt[0];
        return udts;
    }

    // opcodes
    private static Opcode[] getOpcodes() {
        Opcode[] op = new Opcode[5];
        addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op) {
        {
            int[] a = {1, 2, 4};
            op[0] = getOpcodeCat(a);
        }
        {
            char[] a = {97};
            op[1] = getOpcodeTls(a);
        }
        op[2] = getOpcodeRep((char) 0, (char) 1, 3);
        op[3] = getOpcodeRnm(0, 0); // AnBn
        {
            char[] a = {98};
            op[4] = getOpcodeTls(a);
        }
    }

    /**
     * Displays the original SABNF grammar on the output device.
     *
     * @param out the output device to display on.
     */
    public static void display(PrintStream out) {
        out.println(";");
        out.println("; examples.anbn.AnBn");
        out.println(";");
        out.println("AnBn = \"a\" [AnBn] \"b\"");
    }
}
