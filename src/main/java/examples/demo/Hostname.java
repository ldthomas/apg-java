// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.demo;

import apg.Grammar;
import java.io.PrintStream;
/** This class has been generated automatically from an SABNF grammar by
 * the {@link apg.Generator} class of Java APG, Version 1.1.0.<br>
 * It is an extension of the {@link apg.Grammar}
 * class containing additional members and enums not found
 * in the base class.<br>
 * The function {@link #getInstance()} will return a reference to a static,
 * singleton instance of the class.
 */

public class Hostname extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new Hostname(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 7;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>4</code>, name = <code>"ALPHA"</code> */
        ALPHA("ALPHA", 4, 33, 3),
        /** id = <code>5</code>, name = <code>"alphanum"</code> */
        ALPHANUM("alphanum", 5, 36, 4),
        /** id = <code>0</code>, name = <code>"context"</code> */
        CONTEXT("context", 0, 0, 4),
        /** id = <code>2</code>, name = <code>"domainlabel"</code> */
        DOMAINLABEL("domainlabel", 2, 14, 9),
        /** id = <code>1</code>, name = <code>"hostname"</code> */
        HOSTNAME("hostname", 1, 4, 10),
        /** id = <code>6</code>, name = <code>"SP"</code> */
        SP("SP", 6, 40, 1),
        /** id = <code>3</code>, name = <code>"toplabel"</code> */
        TOPLABEL("toplabel", 3, 23, 10);
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
    private static Hostname factoryInstance = null;
    private Hostname(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[7];
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
    	Opcode[] op = new Opcode[41];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2,3}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(6, 40); // SP
        op[2] = getOpcodeRnm(1, 4); // hostname
        op[3] = getOpcodeRnm(6, 40); // SP
        {int[] a = {5,11,12}; op[4] = getOpcodeCat(a);}
        op[5] = getOpcodeRep((char)0, Character.MAX_VALUE, 6);
        {int[] a = {7,8,9}; op[6] = getOpcodeCat(a);}
        op[7] = getOpcodeRnm(2, 14); // domainlabel
        {char[] a = {46}; op[8] = getOpcodeTls(a);}
        op[9] = getOpcodeAnd(10);
        op[10] = getOpcodeRnm(5, 36); // alphanum
        op[11] = getOpcodeRnm(3, 23); // toplabel
        op[12] = getOpcodeRep((char)0, (char)1, 13);
        {char[] a = {46}; op[13] = getOpcodeTls(a);}
        {int[] a = {15,17}; op[14] = getOpcodeCat(a);}
        op[15] = getOpcodeRep((char)1, Character.MAX_VALUE, 16);
        op[16] = getOpcodeRnm(5, 36); // alphanum
        op[17] = getOpcodeRep((char)0, Character.MAX_VALUE, 18);
        {int[] a = {19,21}; op[18] = getOpcodeCat(a);}
        op[19] = getOpcodeRep((char)1, Character.MAX_VALUE, 20);
        {char[] a = {45}; op[20] = getOpcodeTls(a);}
        op[21] = getOpcodeRep((char)1, Character.MAX_VALUE, 22);
        op[22] = getOpcodeRnm(5, 36); // alphanum
        {int[] a = {24,25,27}; op[23] = getOpcodeCat(a);}
        op[24] = getOpcodeRnm(4, 33); // ALPHA
        op[25] = getOpcodeRep((char)0, Character.MAX_VALUE, 26);
        op[26] = getOpcodeRnm(5, 36); // alphanum
        op[27] = getOpcodeRep((char)0, Character.MAX_VALUE, 28);
        {int[] a = {29,31}; op[28] = getOpcodeCat(a);}
        op[29] = getOpcodeRep((char)1, Character.MAX_VALUE, 30);
        {char[] a = {45}; op[30] = getOpcodeTls(a);}
        op[31] = getOpcodeRep((char)1, Character.MAX_VALUE, 32);
        op[32] = getOpcodeRnm(5, 36); // alphanum
        {int[] a = {34,35}; op[33] = getOpcodeAlt(a);}
        op[34] = getOpcodeTrg((char)65, (char)90);
        op[35] = getOpcodeTrg((char)97, (char)122);
        {int[] a = {37,38,39}; op[36] = getOpcodeAlt(a);}
        op[37] = getOpcodeTrg((char)65, (char)90);
        op[38] = getOpcodeTrg((char)97, (char)122);
        op[39] = getOpcodeTrg((char)48, (char)57);
        {char[] a = {32}; op[40] = getOpcodeTls(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.demo.Hostname");
        out.println(";");
        out.println(";");
        out.println("; hostname: Taken from the SIP message standard RFC 3261.");
        out.println(";           SABNF Modifications:");
        out.println(";           1) prioritized-choice disambiguation");
        out.println(";           2) syntactic predicate to differentiate between domainlabel and toplabel");
        out.println(";           3) the interior hyphen requirement corrected for \"greedy\" repetitions");
        out.println(";");
        out.println("context     = SP hostname SP");
        out.println("hostname    = *(domainlabel \".\" &alphanum) toplabel [ \".\" ]");
        out.println("domainlabel = 1*alphanum *(1*\"-\" 1*alphanum)");
        out.println("toplabel    = ALPHA *alphanum *(1*\"-\" 1*alphanum)");
        out.println("ALPHA       = %d65-90 / %d97-122");
        out.println("alphanum    = %d65-90 / %d97-122 / %d48-57");
        out.println("SP          = \" \"");
    }
}
