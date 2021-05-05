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
 * <br>Copyright (c) 2021 Lowell D. Thomas, all rights reserved<br>
 * <a href="https://opensource.org/licenses/BSD-2-Clause">2-Clause BSD License</a>
 */

public class UHostname extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new UHostname(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 7;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>5</code>, name = <code>"alphanum"</code> */
        ALPHANUM("alphanum", 5, 17, 4),
        /** id = <code>0</code>, name = <code>"context"</code> */
        CONTEXT("context", 0, 0, 4),
        /** id = <code>3</code>, name = <code>"domainlabel"</code> */
        DOMAINLABEL("domainlabel", 3, 15, 1),
        /** id = <code>1</code>, name = <code>"hostname"</code> */
        HOSTNAME("hostname", 1, 4, 2),
        /** id = <code>2</code>, name = <code>"label"</code> */
        LABEL("label", 2, 6, 9),
        /** id = <code>6</code>, name = <code>"SP"</code> */
        SP("SP", 6, 21, 1),
        /** id = <code>4</code>, name = <code>"toplabel"</code> */
        TOPLABEL("toplabel", 4, 16, 1);
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
    public static int udtCount = 1;
    /** This enum provides easy to remember enum constants for locating the UDT identifiers and names.
     * The enum constants have the same spelling as the UDT names rendered in all caps with underscores replacing hyphens. */
    public enum UdtNames{
        /** id = <code>0</code>, name = <code>"u_udt-as-alt"</code> */
        U_UDT_AS_ALT(0, "u_udt-as-alt", false);
        private String name;
        private int id;
        private boolean empty;
        UdtNames(int id, String string, boolean empty){
            this.name = string;
            this.id = id;
            this.empty = empty;
        }
        /** Associates the enum with the original grammar name of the UDT it represents.
        * @return the original grammar UDT name. */
        public String  udtName(){return name;}
        /** Associates the enum with an identifier for the UDT it represents.
        * @return the UDT identifier. */
        public int     udtID(){return id;}
        /** Associates the enum with the "empty" attribute of the UDT it represents.
        * @return the "empty" attribute.
        * True if the UDT name begins with <code>"e_"</code>, false if it begins with <code>"u_"</code>. */
        public boolean udtMayBeEmpty(){return empty;}
    }

    // private
    private static UHostname factoryInstance = null;
    private UHostname(Rule[] rules, Udt[] udts, Opcode[] opcodes){
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
    	Udt[] udts = new Udt[1];
        for(UdtNames r : UdtNames.values()){
            udts[r.udtID()] = getUdt(r.udtID(), r.udtName(), r.udtMayBeEmpty());
        }
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[22];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2,3}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(6, 21); // SP
        op[2] = getOpcodeRnm(1, 4); // hostname
        op[3] = getOpcodeRnm(6, 21); // SP
        op[4] = getOpcodeRep((char)1, Character.MAX_VALUE, 5);
        op[5] = getOpcodeUdt(0); // u_UDT-as-ALT
        {int[] a = {7,9}; op[6] = getOpcodeCat(a);}
        op[7] = getOpcodeRep((char)1, Character.MAX_VALUE, 8);
        op[8] = getOpcodeRnm(5, 17); // alphanum
        op[9] = getOpcodeRep((char)0, Character.MAX_VALUE, 10);
        {int[] a = {11,13}; op[10] = getOpcodeCat(a);}
        op[11] = getOpcodeRep((char)1, Character.MAX_VALUE, 12);
        {char[] a = {45}; op[12] = getOpcodeTls(a);}
        op[13] = getOpcodeRep((char)1, Character.MAX_VALUE, 14);
        op[14] = getOpcodeRnm(5, 17); // alphanum
        {char[] a = {}; op[15] = getOpcodeTls(a);}
        {char[] a = {}; op[16] = getOpcodeTls(a);}
        {int[] a = {18,19,20}; op[17] = getOpcodeAlt(a);}
        op[18] = getOpcodeTrg((char)65, (char)90);
        op[19] = getOpcodeTrg((char)97, (char)122);
        op[20] = getOpcodeTrg((char)48, (char)57);
        {char[] a = {32}; op[21] = getOpcodeTls(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; example.demo.UHostname");
        out.println(";");
        out.println(";");
        out.println("; hostname: Taken from the SIP message standard RFC 3261.");
        out.println(";           SABNF Modifications:");
        out.println(";           1) prioritized-choice disambiguation");
        out.println(";           2) UDT is used to select between domanlabel and toplabel");
        out.println(";           3) the interior hyphen requirement corrected for \"greedy\" repetitions");
        out.println(";");
        out.println("context     = SP hostname SP");
        out.println("hostname    = 1*u_UDT-as-ALT                 ; UDT to select between domainlabel & toplabel");
        out.println("label       = 1*alphanum *(1*\"-\" 1*alphanum) ; generic label");
        out.println("domainlabel = \"\"                             ; dummy rule for AST node creation");
        out.println("toplabel    = \"\"                             ; dummy rule for AST node creation");
        out.println("alphanum    = %d65-90 / %d97-122 / %d48-57");
        out.println("SP          = \" \"");
    }
}
