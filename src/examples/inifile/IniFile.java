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
 */

public class IniFile extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new IniFile(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 19;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>16</code>, name = <code>"alpha"</code> */
        ALPHA("alpha", 16, 103, 3),
        /** id = <code>18</code>, name = <code>"any"</code> */
        ANY("any", 18, 107, 3),
        /** id = <code>4</code>, name = <code>"BadSectionLine"</code> */
        BADSECTIONLINE("BadSectionLine", 4, 24, 5),
        /** id = <code>7</code>, name = <code>"BadValueLine"</code> */
        BADVALUELINE("BadValueLine", 7, 42, 7),
        /** id = <code>12</code>, name = <code>"BlankLine"</code> */
        BLANKLINE("BlankLine", 12, 86, 5),
        /** id = <code>14</code>, name = <code>"comment"</code> */
        COMMENT("comment", 14, 95, 4),
        /** id = <code>17</code>, name = <code>"digit"</code> */
        DIGIT("digit", 17, 106, 1),
        /** id = <code>3</code>, name = <code>"GoodSectionLine"</code> */
        GOODSECTIONLINE("GoodSectionLine", 3, 14, 10),
        /** id = <code>6</code>, name = <code>"GoodValueLine"</code> */
        GOODVALUELINE("GoodValueLine", 6, 32, 10),
        /** id = <code>0</code>, name = <code>"IniFile"</code> */
        INIFILE("IniFile", 0, 0, 5),
        /** id = <code>13</code>, name = <code>"LineEnd"</code> */
        LINEEND("LineEnd", 13, 91, 4),
        /** id = <code>1</code>, name = <code>"Section"</code> */
        SECTION("Section", 1, 5, 6),
        /** id = <code>2</code>, name = <code>"SectionLine"</code> */
        SECTIONLINE("SectionLine", 2, 11, 3),
        /** id = <code>9</code>, name = <code>"SectionName"</code> */
        SECTIONNAME("SectionName", 9, 57, 5),
        /** id = <code>11</code>, name = <code>"Value"</code> */
        VALUE("Value", 11, 67, 19),
        /** id = <code>8</code>, name = <code>"ValueArray"</code> */
        VALUEARRAY("ValueArray", 8, 49, 8),
        /** id = <code>5</code>, name = <code>"ValueLine"</code> */
        VALUELINE("ValueLine", 5, 29, 3),
        /** id = <code>10</code>, name = <code>"ValueName"</code> */
        VALUENAME("ValueName", 10, 62, 5),
        /** id = <code>15</code>, name = <code>"wsp"</code> */
        WSP("wsp", 15, 99, 4);
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
    private static IniFile factoryInstance = null;
    private IniFile(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[19];
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
    	Opcode[] op = new Opcode[110];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,3}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRep((char)0, Character.MAX_VALUE, 2);
        op[2] = getOpcodeRnm(12, 86); // BlankLine
        op[3] = getOpcodeRep((char)0, Character.MAX_VALUE, 4);
        op[4] = getOpcodeRnm(1, 5); // Section
        {int[] a = {6,7}; op[5] = getOpcodeCat(a);}
        op[6] = getOpcodeRnm(2, 11); // SectionLine
        op[7] = getOpcodeRep((char)0, Character.MAX_VALUE, 8);
        {int[] a = {9,10}; op[8] = getOpcodeAlt(a);}
        op[9] = getOpcodeRnm(12, 86); // BlankLine
        op[10] = getOpcodeRnm(5, 29); // ValueLine
        {int[] a = {12,13}; op[11] = getOpcodeAlt(a);}
        op[12] = getOpcodeRnm(3, 14); // GoodSectionLine
        op[13] = getOpcodeRnm(4, 24); // BadSectionLine
        {int[] a = {15,16,17,18,19,20,21,23}; op[14] = getOpcodeCat(a);}
        {char[] a = {91}; op[15] = getOpcodeTls(a);}
        op[16] = getOpcodeRnm(15, 99); // wsp
        op[17] = getOpcodeRnm(9, 57); // SectionName
        op[18] = getOpcodeRnm(15, 99); // wsp
        {char[] a = {93}; op[19] = getOpcodeTls(a);}
        op[20] = getOpcodeRnm(15, 99); // wsp
        op[21] = getOpcodeRep((char)0, (char)1, 22);
        op[22] = getOpcodeRnm(14, 95); // comment
        op[23] = getOpcodeRnm(13, 91); // LineEnd
        {int[] a = {25,26,28}; op[24] = getOpcodeCat(a);}
        {char[] a = {91}; op[25] = getOpcodeTls(a);}
        op[26] = getOpcodeRep((char)0, Character.MAX_VALUE, 27);
        op[27] = getOpcodeRnm(18, 107); // any
        op[28] = getOpcodeRnm(13, 91); // LineEnd
        {int[] a = {30,31}; op[29] = getOpcodeAlt(a);}
        op[30] = getOpcodeRnm(6, 32); // GoodValueLine
        op[31] = getOpcodeRnm(7, 42); // BadValueLine
        {int[] a = {33,34,35,36,37,38,39,41}; op[32] = getOpcodeCat(a);}
        op[33] = getOpcodeRnm(10, 62); // ValueName
        op[34] = getOpcodeRnm(15, 99); // wsp
        {char[] a = {61}; op[35] = getOpcodeTls(a);}
        op[36] = getOpcodeRnm(15, 99); // wsp
        op[37] = getOpcodeRnm(8, 49); // ValueArray
        op[38] = getOpcodeRnm(15, 99); // wsp
        op[39] = getOpcodeRep((char)0, (char)1, 40);
        op[40] = getOpcodeRnm(14, 95); // comment
        op[41] = getOpcodeRnm(13, 91); // LineEnd
        {int[] a = {43,46,48}; op[42] = getOpcodeCat(a);}
        {int[] a = {44,45}; op[43] = getOpcodeAlt(a);}
        op[44] = getOpcodeTrg((char)33, (char)90);
        op[45] = getOpcodeTrg((char)92, (char)126);
        op[46] = getOpcodeRep((char)0, Character.MAX_VALUE, 47);
        op[47] = getOpcodeRnm(18, 107); // any
        op[48] = getOpcodeRnm(13, 91); // LineEnd
        {int[] a = {50,51}; op[49] = getOpcodeCat(a);}
        op[50] = getOpcodeRnm(11, 67); // Value
        op[51] = getOpcodeRep((char)0, Character.MAX_VALUE, 52);
        {int[] a = {53,54,55,56}; op[52] = getOpcodeCat(a);}
        op[53] = getOpcodeRnm(15, 99); // wsp
        {char[] a = {44}; op[54] = getOpcodeTls(a);}
        op[55] = getOpcodeRnm(15, 99); // wsp
        op[56] = getOpcodeRnm(11, 67); // Value
        op[57] = getOpcodeRep((char)1, Character.MAX_VALUE, 58);
        {int[] a = {59,60,61}; op[58] = getOpcodeAlt(a);}
        op[59] = getOpcodeRnm(16, 103); // alpha
        op[60] = getOpcodeRnm(17, 106); // digit
        {char[] a = {95}; op[61] = getOpcodeTbs(a);}
        op[62] = getOpcodeRep((char)1, Character.MAX_VALUE, 63);
        {int[] a = {64,65,66}; op[63] = getOpcodeAlt(a);}
        op[64] = getOpcodeRnm(16, 103); // alpha
        op[65] = getOpcodeRnm(17, 106); // digit
        {char[] a = {95}; op[66] = getOpcodeTbs(a);}
        {int[] a = {68,72,79}; op[67] = getOpcodeAlt(a);}
        op[68] = getOpcodeRep((char)1, Character.MAX_VALUE, 69);
        {int[] a = {70,71}; op[69] = getOpcodeAlt(a);}
        op[70] = getOpcodeRnm(16, 103); // alpha
        op[71] = getOpcodeRnm(17, 106); // digit
        {int[] a = {73,74,78}; op[72] = getOpcodeCat(a);}
        {char[] a = {34}; op[73] = getOpcodeTbs(a);}
        op[74] = getOpcodeRep((char)1, Character.MAX_VALUE, 75);
        {int[] a = {76,77}; op[75] = getOpcodeAlt(a);}
        op[76] = getOpcodeTrg((char)32, (char)33);
        op[77] = getOpcodeTrg((char)35, (char)126);
        {char[] a = {34}; op[78] = getOpcodeTbs(a);}
        {int[] a = {80,81,85}; op[79] = getOpcodeCat(a);}
        {char[] a = {39}; op[80] = getOpcodeTbs(a);}
        op[81] = getOpcodeRep((char)1, Character.MAX_VALUE, 82);
        {int[] a = {83,84}; op[82] = getOpcodeAlt(a);}
        op[83] = getOpcodeTrg((char)32, (char)38);
        op[84] = getOpcodeTrg((char)40, (char)126);
        {char[] a = {39}; op[85] = getOpcodeTbs(a);}
        {int[] a = {87,88,90}; op[86] = getOpcodeCat(a);}
        op[87] = getOpcodeRnm(15, 99); // wsp
        op[88] = getOpcodeRep((char)0, (char)1, 89);
        op[89] = getOpcodeRnm(14, 95); // comment
        op[90] = getOpcodeRnm(13, 91); // LineEnd
        {int[] a = {92,93,94}; op[91] = getOpcodeAlt(a);}
        {char[] a = {13,10}; op[92] = getOpcodeTbs(a);}
        {char[] a = {10}; op[93] = getOpcodeTbs(a);}
        {char[] a = {13}; op[94] = getOpcodeTbs(a);}
        {int[] a = {96,97}; op[95] = getOpcodeCat(a);}
        {char[] a = {59}; op[96] = getOpcodeTls(a);}
        op[97] = getOpcodeRep((char)0, Character.MAX_VALUE, 98);
        op[98] = getOpcodeRnm(18, 107); // any
        op[99] = getOpcodeRep((char)0, Character.MAX_VALUE, 100);
        {int[] a = {101,102}; op[100] = getOpcodeAlt(a);}
        {char[] a = {32}; op[101] = getOpcodeTbs(a);}
        {char[] a = {9}; op[102] = getOpcodeTbs(a);}
        {int[] a = {104,105}; op[103] = getOpcodeAlt(a);}
        op[104] = getOpcodeTrg((char)65, (char)90);
        op[105] = getOpcodeTrg((char)97, (char)122);
        op[106] = getOpcodeTrg((char)48, (char)57);
        {int[] a = {108,109}; op[107] = getOpcodeAlt(a);}
        op[108] = getOpcodeTrg((char)32, (char)126);
        {char[] a = {9}; op[109] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.inifile.IniFile");
        out.println(";");
        out.println("IniFile         = *BlankLine *Section");
        out.println("Section         = SectionLine *(BlankLine/ValueLine)");
        out.println("SectionLine     = GoodSectionLine/BadSectionLine");
        out.println("GoodSectionLine = \"[\" wsp SectionName wsp \"]\" wsp [comment] LineEnd");
        out.println("BadSectionLine  = \"[\" *any LineEnd;");
        out.println("ValueLine       = GoodValueLine/BadValueLine");
        out.println("GoodValueLine   = ValueName wsp \"=\" wsp ValueArray wsp [comment] LineEnd");
        out.println("BadValueLine    = (%d33-90/%d92-126) *any LineEnd");
        out.println("ValueArray      = Value *(wsp \",\" wsp Value)");
        out.println("SectionName     = 1*(alpha/digit/%d95)");
        out.println("ValueName       = 1*(alpha/digit/%d95)");
        out.println("Value           = (1*(alpha/digit)) /");
        out.println("                  (%d34 1*(%d32-33/%d35-126) %d34) /");
        out.println("                  (%d39 1*(%d32-38/%d40-126) %d39)");
        out.println("BlankLine       = wsp [comment] LineEnd");
        out.println("LineEnd         = %d13.10/%d10/%d13");
        out.println("comment         = \";\" *any");
        out.println("wsp             = *(%d32/%d9)");
        out.println("alpha           = %d65-90/%d97-122");
        out.println("digit           = %d48-57");
        out.println("any             = %d32-126/%d9");
    }
}
