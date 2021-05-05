// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.inifile;

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

public class UIniFile extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new UIniFile(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 12;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>4</code>, name = <code>"BadSectionLine"</code> */
        BADSECTIONLINE("BadSectionLine", 4, 28, 4),
        /** id = <code>7</code>, name = <code>"BadValueLine"</code> */
        BADVALUELINE("BadValueLine", 7, 45, 6),
        /** id = <code>3</code>, name = <code>"GoodSectionLine"</code> */
        GOODSECTIONLINE("GoodSectionLine", 3, 18, 10),
        /** id = <code>6</code>, name = <code>"GoodValueLine"</code> */
        GOODVALUELINE("GoodValueLine", 6, 35, 10),
        /** id = <code>0</code>, name = <code>"IniFile"</code> */
        INIFILE("IniFile", 0, 0, 7),
        /** id = <code>1</code>, name = <code>"Section"</code> */
        SECTION("Section", 1, 7, 8),
        /** id = <code>2</code>, name = <code>"SectionLine"</code> */
        SECTIONLINE("SectionLine", 2, 15, 3),
        /** id = <code>9</code>, name = <code>"SectionName"</code> */
        SECTIONNAME("SectionName", 9, 59, 1),
        /** id = <code>11</code>, name = <code>"Value"</code> */
        VALUE("Value", 11, 61, 4),
        /** id = <code>8</code>, name = <code>"ValueArray"</code> */
        VALUEARRAY("ValueArray", 8, 51, 8),
        /** id = <code>5</code>, name = <code>"ValueLine"</code> */
        VALUELINE("ValueLine", 5, 32, 3),
        /** id = <code>10</code>, name = <code>"ValueName"</code> */
        VALUENAME("ValueName", 10, 60, 1);
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
    public static int udtCount = 9;
    /** This enum provides easy to remember enum constants for locating the UDT identifiers and names.
     * The enum constants have the same spelling as the UDT names rendered in all caps with underscores replacing hyphens. */
    public enum UdtNames{
        /** id = <code>4</code>, name = <code>"e_any"</code> */
        E_ANY(4, "e_any", true),
        /** id = <code>0</code>, name = <code>"e_comment-wsp"</code> */
        E_COMMENT_WSP(0, "e_comment-wsp", true),
        /** id = <code>2</code>, name = <code>"e_wsp"</code> */
        E_WSP(2, "e_wsp", true),
        /** id = <code>6</code>, name = <code>"u_alphadigit"</code> */
        U_ALPHADIGIT(6, "u_alphadigit", false),
        /** id = <code>5</code>, name = <code>"u_alphadigitunder"</code> */
        U_ALPHADIGITUNDER(5, "u_alphadigitunder", false),
        /** id = <code>3</code>, name = <code>"u_comment"</code> */
        U_COMMENT(3, "u_comment", false),
        /** id = <code>7</code>, name = <code>"u_dqstring"</code> */
        U_DQSTRING(7, "u_dqstring", false),
        /** id = <code>1</code>, name = <code>"u_lineend"</code> */
        U_LINEEND(1, "u_lineend", false),
        /** id = <code>8</code>, name = <code>"u_sqstring"</code> */
        U_SQSTRING(8, "u_sqstring", false);
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
    private static UIniFile factoryInstance = null;
    private UIniFile(Rule[] rules, Udt[] udts, Opcode[] opcodes){
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
    	Udt[] udts = new Udt[9];
        for(UdtNames r : UdtNames.values()){
            udts[r.udtID()] = getUdt(r.udtID(), r.udtName(), r.udtMayBeEmpty());
        }
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[65];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,5}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRep((char)0, Character.MAX_VALUE, 2);
        {int[] a = {3,4}; op[2] = getOpcodeCat(a);}
        op[3] = getOpcodeUdt(0); // e_comment-wsp
        op[4] = getOpcodeUdt(1); // u_lineend
        op[5] = getOpcodeRep((char)0, Character.MAX_VALUE, 6);
        op[6] = getOpcodeRnm(1, 7); // Section
        {int[] a = {8,9}; op[7] = getOpcodeCat(a);}
        op[8] = getOpcodeRnm(2, 15); // SectionLine
        op[9] = getOpcodeRep((char)0, Character.MAX_VALUE, 10);
        {int[] a = {11,14}; op[10] = getOpcodeAlt(a);}
        {int[] a = {12,13}; op[11] = getOpcodeCat(a);}
        op[12] = getOpcodeUdt(0); // e_comment-wsp
        op[13] = getOpcodeUdt(1); // u_lineend
        op[14] = getOpcodeRnm(5, 32); // ValueLine
        {int[] a = {16,17}; op[15] = getOpcodeAlt(a);}
        op[16] = getOpcodeRnm(3, 18); // GoodSectionLine
        op[17] = getOpcodeRnm(4, 28); // BadSectionLine
        {int[] a = {19,20,21,22,23,24,25,27}; op[18] = getOpcodeCat(a);}
        {char[] a = {91}; op[19] = getOpcodeTls(a);}
        op[20] = getOpcodeUdt(2); // e_wsp
        op[21] = getOpcodeRnm(9, 59); // SectionName
        op[22] = getOpcodeUdt(2); // e_wsp
        {char[] a = {93}; op[23] = getOpcodeTls(a);}
        op[24] = getOpcodeUdt(2); // e_wsp
        op[25] = getOpcodeRep((char)0, (char)1, 26);
        op[26] = getOpcodeUdt(3); // u_comment
        op[27] = getOpcodeUdt(1); // u_lineend
        {int[] a = {29,30,31}; op[28] = getOpcodeCat(a);}
        {char[] a = {91}; op[29] = getOpcodeTls(a);}
        op[30] = getOpcodeUdt(4); // e_any
        op[31] = getOpcodeUdt(1); // u_lineend
        {int[] a = {33,34}; op[32] = getOpcodeAlt(a);}
        op[33] = getOpcodeRnm(6, 35); // GoodValueLine
        op[34] = getOpcodeRnm(7, 45); // BadValueLine
        {int[] a = {36,37,38,39,40,41,42,44}; op[35] = getOpcodeCat(a);}
        op[36] = getOpcodeRnm(10, 60); // ValueName
        op[37] = getOpcodeUdt(2); // e_wsp
        {char[] a = {61}; op[38] = getOpcodeTls(a);}
        op[39] = getOpcodeUdt(2); // e_wsp
        op[40] = getOpcodeRnm(8, 51); // ValueArray
        op[41] = getOpcodeUdt(2); // e_wsp
        op[42] = getOpcodeRep((char)0, (char)1, 43);
        op[43] = getOpcodeUdt(3); // u_comment
        op[44] = getOpcodeUdt(1); // u_lineend
        {int[] a = {46,49,50}; op[45] = getOpcodeCat(a);}
        {int[] a = {47,48}; op[46] = getOpcodeAlt(a);}
        op[47] = getOpcodeTrg((char)33, (char)90);
        op[48] = getOpcodeTrg((char)92, (char)126);
        op[49] = getOpcodeUdt(4); // e_any
        op[50] = getOpcodeUdt(1); // u_lineend
        {int[] a = {52,53}; op[51] = getOpcodeCat(a);}
        op[52] = getOpcodeRnm(11, 61); // Value
        op[53] = getOpcodeRep((char)0, Character.MAX_VALUE, 54);
        {int[] a = {55,56,57,58}; op[54] = getOpcodeCat(a);}
        op[55] = getOpcodeUdt(2); // e_wsp
        {char[] a = {44}; op[56] = getOpcodeTls(a);}
        op[57] = getOpcodeUdt(2); // e_wsp
        op[58] = getOpcodeRnm(11, 61); // Value
        op[59] = getOpcodeUdt(5); // u_alphadigitunder
        op[60] = getOpcodeUdt(5); // u_alphadigitunder
        {int[] a = {62,63,64}; op[61] = getOpcodeAlt(a);}
        op[62] = getOpcodeUdt(6); // u_alphadigit
        op[63] = getOpcodeUdt(7); // u_dqstring
        op[64] = getOpcodeUdt(8); // u_sqstring
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; example.inifile.UIniFile");
        out.println(";");
        out.println("IniFile         = *(e_comment-wsp u_lineend) *Section");
        out.println("Section         = SectionLine *((e_comment-wsp u_lineend)/ValueLine)");
        out.println("SectionLine     = GoodSectionLine/BadSectionLine");
        out.println("GoodSectionLine = \"[\" e_wsp SectionName e_wsp \"]\" e_wsp [u_comment] u_lineend");
        out.println("BadSectionLine  = \"[\" e_any u_lineend;");
        out.println("ValueLine       = GoodValueLine/BadValueLine");
        out.println("GoodValueLine   = ValueName e_wsp \"=\" e_wsp ValueArray e_wsp [u_comment] u_lineend");
        out.println("BadValueLine    = (%d33-90/%d92-126) e_any u_lineend");
        out.println("ValueArray      = Value *(e_wsp \",\" e_wsp Value)");
        out.println("SectionName     = u_alphadigitunder");
        out.println("ValueName       = u_alphadigitunder");
        out.println("Value           = u_alphadigit/u_dqstring/u_sqstring");
    }
}
