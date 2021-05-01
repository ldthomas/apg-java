// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.testudtlib;

import apg.Grammar;
import java.io.PrintStream;
/** This class has been generated automatically from an SABNF grammar by
 * the {@link apg.Generator} class of Java APG, Version 1.1.<br>
 * It is an extension of the {@link apg.Grammar}
 * class containing additional members and enums not found
 * in the base class.<br>
 * The function {@link #getInstance()} will return a reference to a static,
 * singleton instance of the class.
 * <br>Copyright (c) 2021 Lowell D. Thomas, all rights reserved<br>
 * <a href="https://opensource.org/licenses/BSD-2-Clause">2-Clause BSD License</a>
 */

public class HexNum extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new HexNum(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 4;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>2</code>, name = <code>"ALPHA"</code> */
        ALPHA("ALPHA", 2, 11, 4),
        /** id = <code>3</code>, name = <code>"DIGIT"</code> */
        DIGIT("DIGIT", 3, 15, 1),
        /** id = <code>1</code>, name = <code>"HEXDIGIT"</code> */
        HEXDIGIT("HEXDIGIT", 1, 2, 9),
        /** id = <code>0</code>, name = <code>"hexnum"</code> */
        HEXNUM("hexnum", 0, 0, 2);
        private String name;
        private int id;
        private int offset;
        private int count;
        RuleNames(String string, int id, int offset, int count){
            this.name = string;
            this.id = id;
            this.offset = offset;
            this.count = count;
        }
        /** Associates the enum with the original grammar name of the rule it represents.
        * @return the original grammar rule name. */
        public  String ruleName(){return name;}
        /** Associates the enum with an identifier for the grammar rule it represents.
        * @return the rule name identifier. */
        public  int    ruleID(){return id;}
        private int    opcodeOffset(){return offset;}
        private int    opcodeCount(){return count;}
    }

    // UDT name enum
    /** The number of UDTs in the grammar */
    public static int udtCount = 0;
    /** This enum provides easy to remember enum constants for locating the UDT identifiers and names.
     * The enum constants have the same spelling as the UDT names rendered in all caps with underscores replacing hyphens. */
    public enum UdtNames{
    }

    // private
    private static HexNum factoryInstance = null;
    private HexNum(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[4];
        for(RuleNames r : RuleNames.values()){
            rules[r.ruleID()] = getRule(r.ruleID(), r.ruleName(), r.opcodeOffset(), r.opcodeCount());
        }
        return rules;
    }

    private static Udt[] getUdts(){
    	Udt[] udts = new Udt[0];
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[16];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        op[0] = getOpcodeRep((char)1, Character.MAX_VALUE, 1);
        op[1] = getOpcodeRnm(1, 2); // HEXDIGIT
        {int[] a = {3,4,5,6,7,8,9,10}; op[2] = getOpcodeAlt(a);}
        op[3] = getOpcodeRnm(2, 11); // ALPHA
        op[4] = getOpcodeRnm(3, 15); // DIGIT
        {char[] a = {65}; op[5] = getOpcodeTls(a);}
        {char[] a = {66}; op[6] = getOpcodeTls(a);}
        {char[] a = {67}; op[7] = getOpcodeTls(a);}
        {char[] a = {68}; op[8] = getOpcodeTls(a);}
        {char[] a = {69}; op[9] = getOpcodeTls(a);}
        {char[] a = {70}; op[10] = getOpcodeTls(a);}
        {int[] a = {12,13,14}; op[11] = getOpcodeAlt(a);}
        op[12] = getOpcodeTrg((char)65, (char)90);
        op[13] = getOpcodeTrg((char)97, (char)122);
        {char[] a = {95}; op[14] = getOpcodeTbs(a);}
        op[15] = getOpcodeTrg((char)48, (char)57);
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.testudtlib.HexNum");
        out.println(";");
        out.println("hexnum   = 1*HEXDIGIT");
        out.println("HEXDIGIT = ALPHA / DIGIT / \"A\" / \"B\" / \"C\" / \"D\" / \"E\" / \"F\"");
        out.println("ALPHA    = %d65-90 / %d97-122 / %d95");
        out.println("DIGIT    = %d48-57");
    }
}
