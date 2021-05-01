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

public class Comment extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new Comment(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 5;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>3</code>, name = <code>"any"</code> */
        ANY("any", 3, 16, 3),
        /** id = <code>4</code>, name = <code>"anyLF"</code> */
        ANYLF("anyLF", 4, 19, 5),
        /** id = <code>2</code>, name = <code>"c-comment"</code> */
        C_COMMENT("c-comment", 2, 8, 8),
        /** id = <code>1</code>, name = <code>"cpp-comment"</code> */
        CPP_COMMENT("cpp-comment", 1, 4, 4),
        /** id = <code>0</code>, name = <code>"semi-comment"</code> */
        SEMI_COMMENT("semi-comment", 0, 0, 4);
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
    private static Comment factoryInstance = null;
    private Comment(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[5];
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
    	Opcode[] op = new Opcode[24];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2}; op[0] = getOpcodeCat(a);}
        {char[] a = {59}; op[1] = getOpcodeTls(a);}
        op[2] = getOpcodeRep((char)0, Character.MAX_VALUE, 3);
        op[3] = getOpcodeRnm(3, 16); // any
        {int[] a = {5,6}; op[4] = getOpcodeCat(a);}
        {char[] a = {47,47}; op[5] = getOpcodeTls(a);}
        op[6] = getOpcodeRep((char)0, Character.MAX_VALUE, 7);
        op[7] = getOpcodeRnm(3, 16); // any
        {int[] a = {9,10,15}; op[8] = getOpcodeCat(a);}
        {char[] a = {47,42}; op[9] = getOpcodeTls(a);}
        op[10] = getOpcodeRep((char)0, Character.MAX_VALUE, 11);
        {int[] a = {12,14}; op[11] = getOpcodeCat(a);}
        op[12] = getOpcodeNot(13);
        {char[] a = {42,47}; op[13] = getOpcodeTls(a);}
        op[14] = getOpcodeRnm(4, 19); // anyLF
        {char[] a = {42,47}; op[15] = getOpcodeTls(a);}
        {int[] a = {17,18}; op[16] = getOpcodeAlt(a);}
        op[17] = getOpcodeTrg((char)32, (char)126);
        {char[] a = {9}; op[18] = getOpcodeTbs(a);}
        {int[] a = {20,21,22,23}; op[19] = getOpcodeAlt(a);}
        op[20] = getOpcodeTrg((char)32, (char)126);
        {char[] a = {9}; op[21] = getOpcodeTbs(a);}
        {char[] a = {10}; op[22] = getOpcodeTbs(a);}
        {char[] a = {13}; op[23] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.testudtlib.Comment");
        out.println(";");
        out.println("semi-comment = \";\" *any                 ; semi comment");
        out.println("cpp-comment  = \"//\" *any                ; C++ comment");
        out.println("c-comment    = \"/*\" *(!\"*/\" anyLF) \"*/\" ; C comment");
        out.println("any          = %d32-126 / %d9");
        out.println("anyLF        = %d32-126 / %d9 / %d10 / %d13 ");
    }
}
