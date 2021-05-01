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

public class WhiteSpace extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new WhiteSpace(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 12;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>7</code>, name = <code>"any"</code> */
        ANY("any", 7, 27, 1),
        /** id = <code>6</code>, name = <code>"comment"</code> */
        COMMENT("comment", 6, 23, 4),
        /** id = <code>10</code>, name = <code>"CR"</code> */
        CR("CR", 10, 32, 1),
        /** id = <code>11</code>, name = <code>"CRLF"</code> */
        CRLF("CRLF", 11, 33, 1),
        /** id = <code>5</code>, name = <code>"F-SP"</code> */
        F_SP("F-SP", 5, 17, 6),
        /** id = <code>2</code>, name = <code>"FWSP"</code> */
        FWSP("FWSP", 2, 9, 4),
        /** id = <code>0</code>, name = <code>"FWSP-COMMENT"</code> */
        FWSP_COMMENT("FWSP-COMMENT", 0, 0, 5),
        /** id = <code>9</code>, name = <code>"LF"</code> */
        LF("LF", 9, 31, 1),
        /** id = <code>3</code>, name = <code>"OWSP"</code> */
        OWSP("OWSP", 3, 13, 2),
        /** id = <code>8</code>, name = <code>"SP"</code> */
        SP("SP", 8, 28, 3),
        /** id = <code>4</code>, name = <code>"WSP"</code> */
        WSP("WSP", 4, 15, 2),
        /** id = <code>1</code>, name = <code>"WSP-COMMENT"</code> */
        WSP_COMMENT("WSP-COMMENT", 1, 5, 4);
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
    private static WhiteSpace factoryInstance = null;
    private WhiteSpace(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[12];
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
    	Opcode[] op = new Opcode[34];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        op[0] = getOpcodeRep((char)1, Character.MAX_VALUE, 1);
        {int[] a = {2,3,4}; op[1] = getOpcodeAlt(a);}
        op[2] = getOpcodeRnm(8, 28); // SP
        op[3] = getOpcodeRnm(6, 23); // comment
        op[4] = getOpcodeRnm(5, 17); // F-SP
        op[5] = getOpcodeRep((char)1, Character.MAX_VALUE, 6);
        {int[] a = {7,8}; op[6] = getOpcodeAlt(a);}
        op[7] = getOpcodeRnm(8, 28); // SP
        op[8] = getOpcodeRnm(6, 23); // comment
        op[9] = getOpcodeRep((char)1, Character.MAX_VALUE, 10);
        {int[] a = {11,12}; op[10] = getOpcodeAlt(a);}
        op[11] = getOpcodeRnm(8, 28); // SP
        op[12] = getOpcodeRnm(5, 17); // F-SP
        op[13] = getOpcodeRep((char)0, Character.MAX_VALUE, 14);
        op[14] = getOpcodeRnm(8, 28); // SP
        op[15] = getOpcodeRep((char)1, Character.MAX_VALUE, 16);
        op[16] = getOpcodeRnm(8, 28); // SP
        {int[] a = {18,22}; op[17] = getOpcodeCat(a);}
        {int[] a = {19,20,21}; op[18] = getOpcodeAlt(a);}
        op[19] = getOpcodeRnm(9, 31); // LF
        op[20] = getOpcodeRnm(11, 33); // CRLF
        op[21] = getOpcodeRnm(10, 32); // CR
        op[22] = getOpcodeRnm(8, 28); // SP
        {int[] a = {24,25}; op[23] = getOpcodeCat(a);}
        {char[] a = {59}; op[24] = getOpcodeTls(a);}
        op[25] = getOpcodeRep((char)0, Character.MAX_VALUE, 26);
        op[26] = getOpcodeRnm(7, 27); // any
        op[27] = getOpcodeTrg((char)32, (char)126);
        {int[] a = {29,30}; op[28] = getOpcodeAlt(a);}
        {char[] a = {32}; op[29] = getOpcodeTbs(a);}
        {char[] a = {9}; op[30] = getOpcodeTbs(a);}
        {char[] a = {10}; op[31] = getOpcodeTbs(a);}
        {char[] a = {13}; op[32] = getOpcodeTbs(a);}
        {char[] a = {13,10}; op[33] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.testudtlib.WhiteSpace");
        out.println(";");
        out.println("FWSP-COMMENT = 1*(SP / comment / F-SP) ; white space includes comments and folding white space");
        out.println("WSP-COMMENT  = 1*(SP / comment)        ; white space includes comments");
        out.println("FWSP         = 1*(SP / F-SP)           ; white space includes folding white space");
        out.println("OWSP         = *SP                     ; optional white space");
        out.println("WSP          = 1*SP                    ; white space (at least one space required)");
        out.println("F-SP         = (LF / CRLF / CR) SP     ; folding white space - line end followed by a single space");
        out.println("comment      = \";\" *any                ; comment - semicolon to end of line");
        out.println("any          = %d32-126                ; any printing character");
        out.println("SP           = %d32 / %d9              ; space - space or horizontal tab");
        out.println("LF           = %d10                    ; line feed");
        out.println("CR           = %d13                    ; carriage return");
        out.println("CRLF         = %d13.10                 ; carriage return / line feed pair");
    }
}
