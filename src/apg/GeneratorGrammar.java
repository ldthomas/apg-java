// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package apg;

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

public class GeneratorGrammar extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new GeneratorGrammar(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 47;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>39</code>, name = <code>"alphanum"</code> */
        ALPHANUM("alphanum", 39, 203, 10),
        /** id = <code>4</code>, name = <code>"Alternation"</code> */
        ALTERNATION("Alternation", 4, 23, 6),
        /** id = <code>19</code>, name = <code>"AltOp"</code> */
        ALTOP("AltOp", 19, 100, 3),
        /** id = <code>22</code>, name = <code>"AndOp"</code> */
        ANDOP("AndOp", 22, 105, 1),
        /** id = <code>35</code>, name = <code>"Bin"</code> */
        BIN("Bin", 35, 191, 3),
        /** id = <code>1</code>, name = <code>"BlankLine"</code> */
        BLANKLINE("BlankLine", 1, 5, 4),
        /** id = <code>37</code>, name = <code>"bnum"</code> */
        BNUM("bnum", 37, 196, 2),
        /** id = <code>20</code>, name = <code>"CatOp"</code> */
        CATOP("CatOp", 20, 103, 1),
        /** id = <code>44</code>, name = <code>"comment"</code> */
        COMMENT("comment", 44, 226, 6),
        /** id = <code>5</code>, name = <code>"Concatenation"</code> */
        CONCATENATION("Concatenation", 5, 29, 7),
        /** id = <code>33</code>, name = <code>"Dec"</code> */
        DEC("Dec", 33, 185, 3),
        /** id = <code>15</code>, name = <code>"DefinedAs"</code> */
        DEFINEDAS("DefinedAs", 15, 92, 1),
        /** id = <code>36</code>, name = <code>"dnum"</code> */
        DNUM("dnum", 36, 194, 2),
        /** id = <code>10</code>, name = <code>"Element"</code> */
        ELEMENT("Element", 10, 61, 10),
        /** id = <code>0</code>, name = <code>"File"</code> */
        FILE("File", 0, 0, 5),
        /** id = <code>41</code>, name = <code>"fsp"</code> */
        FSP("fsp", 41, 217, 5),
        /** id = <code>11</code>, name = <code>"Group"</code> */
        GROUP("Group", 11, 71, 6),
        /** id = <code>34</code>, name = <code>"Hex"</code> */
        HEX("Hex", 34, 188, 3),
        /** id = <code>16</code>, name = <code>"IncAlt"</code> */
        INCALT("IncAlt", 16, 93, 1),
        /** id = <code>46</code>, name = <code>"LineContinue"</code> */
        LINECONTINUE("LineContinue", 46, 236, 5),
        /** id = <code>45</code>, name = <code>"LineEnd"</code> */
        LINEEND("LineEnd", 45, 232, 4),
        /** id = <code>14</code>, name = <code>"NameDef"</code> */
        NAMEDEF("NameDef", 14, 91, 1),
        /** id = <code>23</code>, name = <code>"NotOp"</code> */
        NOTOP("NotOp", 23, 106, 1),
        /** id = <code>12</code>, name = <code>"Option"</code> */
        OPTION("Option", 12, 77, 6),
        /** id = <code>42</code>, name = <code>"owsp"</code> */
        OWSP("owsp", 42, 222, 2),
        /** id = <code>8</code>, name = <code>"Predicate"</code> */
        PREDICATE("Predicate", 8, 43, 5),
        /** id = <code>13</code>, name = <code>"ProsVal"</code> */
        PROSVAL("ProsVal", 13, 83, 8),
        /** id = <code>9</code>, name = <code>"Rep"</code> */
        REP("Rep", 9, 48, 13),
        /** id = <code>30</code>, name = <code>"rep-max"</code> */
        REP_MAX("rep-max", 30, 145, 2),
        /** id = <code>28</code>, name = <code>"rep-min"</code> */
        REP_MIN("rep-min", 28, 141, 2),
        /** id = <code>29</code>, name = <code>"rep-min-max"</code> */
        REP_MIN_MAX("rep-min-max", 29, 143, 2),
        /** id = <code>7</code>, name = <code>"Repeat"</code> */
        REPEAT("Repeat", 7, 40, 3),
        /** id = <code>6</code>, name = <code>"Repetition"</code> */
        REPETITION("Repetition", 6, 36, 4),
        /** id = <code>17</code>, name = <code>"RnmOp"</code> */
        RNMOP("RnmOp", 17, 94, 1),
        /** id = <code>2</code>, name = <code>"Rule"</code> */
        RULE("Rule", 2, 9, 10),
        /** id = <code>3</code>, name = <code>"RuleError"</code> */
        RULEERROR("RuleError", 3, 19, 4),
        /** id = <code>40</code>, name = <code>"sp"</code> */
        SP("sp", 40, 213, 4),
        /** id = <code>21</code>, name = <code>"StarOp"</code> */
        STAROP("StarOp", 21, 104, 1),
        /** id = <code>25</code>, name = <code>"TbsOp"</code> */
        TBSOP("TbsOp", 25, 110, 3),
        /** id = <code>32</code>, name = <code>"TbsString"</code> */
        TBSSTRING("TbsString", 32, 163, 22),
        /** id = <code>27</code>, name = <code>"TcsOp"</code> */
        TCSOP("TcsOp", 27, 123, 18),
        /** id = <code>26</code>, name = <code>"TlsOp"</code> */
        TLSOP("TlsOp", 26, 113, 10),
        /** id = <code>24</code>, name = <code>"TrgOp"</code> */
        TRGOP("TrgOp", 24, 107, 3),
        /** id = <code>31</code>, name = <code>"TrgRange"</code> */
        TRGRANGE("TrgRange", 31, 147, 16),
        /** id = <code>18</code>, name = <code>"UdtOp"</code> */
        UDTOP("UdtOp", 18, 95, 5),
        /** id = <code>43</code>, name = <code>"wsp"</code> */
        WSP("wsp", 43, 224, 2),
        /** id = <code>38</code>, name = <code>"xnum"</code> */
        XNUM("xnum", 38, 198, 5);
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
    private static GeneratorGrammar factoryInstance = null;
    private GeneratorGrammar(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[47];
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
    	Opcode[] op = new Opcode[241];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        op[0] = getOpcodeRep((char)0, Character.MAX_VALUE, 1);
        {int[] a = {2,3,4}; op[1] = getOpcodeAlt(a);}
        op[2] = getOpcodeRnm(1, 5); // BlankLine
        op[3] = getOpcodeRnm(2, 9); // Rule
        op[4] = getOpcodeRnm(3, 19); // RuleError
        {int[] a = {6,8}; op[5] = getOpcodeCat(a);}
        op[6] = getOpcodeRep((char)0, Character.MAX_VALUE, 7);
        op[7] = getOpcodeRnm(40, 213); // sp
        op[8] = getOpcodeRnm(45, 232); // LineEnd
        {int[] a = {10,11,12,15,16,17,18}; op[9] = getOpcodeCat(a);}
        op[10] = getOpcodeRnm(14, 91); // NameDef
        op[11] = getOpcodeRnm(42, 222); // owsp
        {int[] a = {13,14}; op[12] = getOpcodeAlt(a);}
        op[13] = getOpcodeRnm(16, 93); // IncAlt
        op[14] = getOpcodeRnm(15, 92); // DefinedAs
        op[15] = getOpcodeRnm(42, 222); // owsp
        op[16] = getOpcodeRnm(4, 23); // Alternation
        op[17] = getOpcodeRnm(42, 222); // owsp
        op[18] = getOpcodeRnm(45, 232); // LineEnd
        {int[] a = {20,22}; op[19] = getOpcodeCat(a);}
        op[20] = getOpcodeRep((char)0, Character.MAX_VALUE, 21);
        op[21] = getOpcodeRnm(41, 217); // fsp
        op[22] = getOpcodeRnm(45, 232); // LineEnd
        {int[] a = {24,25}; op[23] = getOpcodeCat(a);}
        op[24] = getOpcodeRnm(5, 29); // Concatenation
        op[25] = getOpcodeRep((char)0, Character.MAX_VALUE, 26);
        {int[] a = {27,28}; op[26] = getOpcodeCat(a);}
        op[27] = getOpcodeRnm(19, 100); // AltOp
        op[28] = getOpcodeRnm(5, 29); // Concatenation
        {int[] a = {30,31,32}; op[29] = getOpcodeCat(a);}
        op[30] = getOpcodeRnm(42, 222); // owsp
        op[31] = getOpcodeRnm(6, 36); // Repetition
        op[32] = getOpcodeRep((char)0, Character.MAX_VALUE, 33);
        {int[] a = {34,35}; op[33] = getOpcodeCat(a);}
        op[34] = getOpcodeRnm(20, 103); // CatOp
        op[35] = getOpcodeRnm(6, 36); // Repetition
        {int[] a = {37,38,39}; op[36] = getOpcodeAlt(a);}
        op[37] = getOpcodeRnm(7, 40); // Repeat
        op[38] = getOpcodeRnm(8, 43); // Predicate
        op[39] = getOpcodeRnm(10, 61); // Element
        {int[] a = {41,42}; op[40] = getOpcodeCat(a);}
        op[41] = getOpcodeRnm(9, 48); // Rep
        op[42] = getOpcodeRnm(10, 61); // Element
        {int[] a = {44,47}; op[43] = getOpcodeCat(a);}
        {int[] a = {45,46}; op[44] = getOpcodeAlt(a);}
        op[45] = getOpcodeRnm(22, 105); // AndOp
        op[46] = getOpcodeRnm(23, 106); // NotOp
        op[47] = getOpcodeRnm(10, 61); // Element
        {int[] a = {49,53,56,59,60}; op[48] = getOpcodeAlt(a);}
        {int[] a = {50,51,52}; op[49] = getOpcodeCat(a);}
        op[50] = getOpcodeRnm(28, 141); // rep-min
        op[51] = getOpcodeRnm(21, 104); // StarOp
        op[52] = getOpcodeRnm(30, 145); // rep-max
        {int[] a = {54,55}; op[53] = getOpcodeCat(a);}
        op[54] = getOpcodeRnm(28, 141); // rep-min
        op[55] = getOpcodeRnm(21, 104); // StarOp
        {int[] a = {57,58}; op[56] = getOpcodeCat(a);}
        op[57] = getOpcodeRnm(21, 104); // StarOp
        op[58] = getOpcodeRnm(30, 145); // rep-max
        op[59] = getOpcodeRnm(21, 104); // StarOp
        op[60] = getOpcodeRnm(29, 143); // rep-min-max
        {int[] a = {62,63,64,65,66,67,68,69,70}; op[61] = getOpcodeAlt(a);}
        op[62] = getOpcodeRnm(24, 107); // TrgOp
        op[63] = getOpcodeRnm(25, 110); // TbsOp
        op[64] = getOpcodeRnm(26, 113); // TlsOp
        op[65] = getOpcodeRnm(27, 123); // TcsOp
        op[66] = getOpcodeRnm(18, 95); // UdtOp
        op[67] = getOpcodeRnm(17, 94); // RnmOp
        op[68] = getOpcodeRnm(11, 71); // Group
        op[69] = getOpcodeRnm(12, 77); // Option
        op[70] = getOpcodeRnm(13, 83); // ProsVal
        {int[] a = {72,73,74,75,76}; op[71] = getOpcodeCat(a);}
        {char[] a = {40}; op[72] = getOpcodeTbs(a);}
        op[73] = getOpcodeRnm(42, 222); // owsp
        op[74] = getOpcodeRnm(4, 23); // Alternation
        op[75] = getOpcodeRnm(42, 222); // owsp
        {char[] a = {41}; op[76] = getOpcodeTbs(a);}
        {int[] a = {78,79,80,81,82}; op[77] = getOpcodeCat(a);}
        {char[] a = {91}; op[78] = getOpcodeTbs(a);}
        op[79] = getOpcodeRnm(42, 222); // owsp
        op[80] = getOpcodeRnm(4, 23); // Alternation
        op[81] = getOpcodeRnm(42, 222); // owsp
        {char[] a = {93}; op[82] = getOpcodeTbs(a);}
        {int[] a = {84,85,90}; op[83] = getOpcodeCat(a);}
        {char[] a = {60}; op[84] = getOpcodeTbs(a);}
        op[85] = getOpcodeRep((char)0, Character.MAX_VALUE, 86);
        {int[] a = {87,88,89}; op[86] = getOpcodeAlt(a);}
        op[87] = getOpcodeTrg((char)32, (char)61);
        op[88] = getOpcodeTrg((char)63, (char)127);
        {char[] a = {9}; op[89] = getOpcodeTbs(a);}
        {char[] a = {62}; op[90] = getOpcodeTbs(a);}
        op[91] = getOpcodeRnm(39, 203); // alphanum
        {char[] a = {61}; op[92] = getOpcodeTbs(a);}
        {char[] a = {61,47}; op[93] = getOpcodeTbs(a);}
        op[94] = getOpcodeRnm(39, 203); // alphanum
        {int[] a = {96,99}; op[95] = getOpcodeCat(a);}
        {int[] a = {97,98}; op[96] = getOpcodeAlt(a);}
        {char[] a = {117,95}; op[97] = getOpcodeTbs(a);}
        {char[] a = {101,95}; op[98] = getOpcodeTbs(a);}
        op[99] = getOpcodeRnm(39, 203); // alphanum
        {int[] a = {101,102}; op[100] = getOpcodeCat(a);}
        op[101] = getOpcodeRnm(42, 222); // owsp
        {char[] a = {47}; op[102] = getOpcodeTbs(a);}
        op[103] = getOpcodeRnm(43, 224); // wsp
        {char[] a = {42}; op[104] = getOpcodeTbs(a);}
        {char[] a = {38}; op[105] = getOpcodeTbs(a);}
        {char[] a = {33}; op[106] = getOpcodeTbs(a);}
        {int[] a = {108,109}; op[107] = getOpcodeCat(a);}
        {char[] a = {37}; op[108] = getOpcodeTbs(a);}
        op[109] = getOpcodeRnm(31, 147); // TrgRange
        {int[] a = {111,112}; op[110] = getOpcodeCat(a);}
        {char[] a = {37}; op[111] = getOpcodeTbs(a);}
        op[112] = getOpcodeRnm(32, 163); // TbsString
        {int[] a = {114,116,117,122}; op[113] = getOpcodeCat(a);}
        op[114] = getOpcodeRep((char)0, (char)1, 115);
        {char[] a = {37,105}; op[115] = getOpcodeTls(a);}
        {char[] a = {34}; op[116] = getOpcodeTbs(a);}
        op[117] = getOpcodeRep((char)0, Character.MAX_VALUE, 118);
        {int[] a = {119,120,121}; op[118] = getOpcodeAlt(a);}
        op[119] = getOpcodeTrg((char)32, (char)33);
        op[120] = getOpcodeTrg((char)35, (char)127);
        {char[] a = {9}; op[121] = getOpcodeTbs(a);}
        {char[] a = {34}; op[122] = getOpcodeTbs(a);}
        {int[] a = {124,132}; op[123] = getOpcodeAlt(a);}
        {int[] a = {125,126,131}; op[124] = getOpcodeCat(a);}
        {char[] a = {39}; op[125] = getOpcodeTbs(a);}
        op[126] = getOpcodeRep((char)0, Character.MAX_VALUE, 127);
        {int[] a = {128,129,130}; op[127] = getOpcodeAlt(a);}
        op[128] = getOpcodeTrg((char)32, (char)38);
        op[129] = getOpcodeTrg((char)40, (char)127);
        {char[] a = {9}; op[130] = getOpcodeTbs(a);}
        {char[] a = {39}; op[131] = getOpcodeTbs(a);}
        {int[] a = {133,134,135,140}; op[132] = getOpcodeCat(a);}
        {char[] a = {37,115}; op[133] = getOpcodeTls(a);}
        {char[] a = {34}; op[134] = getOpcodeTbs(a);}
        op[135] = getOpcodeRep((char)0, Character.MAX_VALUE, 136);
        {int[] a = {137,138,139}; op[136] = getOpcodeAlt(a);}
        op[137] = getOpcodeTrg((char)32, (char)33);
        op[138] = getOpcodeTrg((char)35, (char)127);
        {char[] a = {9}; op[139] = getOpcodeTbs(a);}
        {char[] a = {34}; op[140] = getOpcodeTbs(a);}
        op[141] = getOpcodeRep((char)1, Character.MAX_VALUE, 142);
        op[142] = getOpcodeTrg((char)48, (char)57);
        op[143] = getOpcodeRep((char)1, Character.MAX_VALUE, 144);
        op[144] = getOpcodeTrg((char)48, (char)57);
        op[145] = getOpcodeRep((char)1, Character.MAX_VALUE, 146);
        op[146] = getOpcodeTrg((char)48, (char)57);
        {int[] a = {148,153,158}; op[147] = getOpcodeAlt(a);}
        {int[] a = {149,150,151,152}; op[148] = getOpcodeCat(a);}
        op[149] = getOpcodeRnm(33, 185); // Dec
        op[150] = getOpcodeRnm(36, 194); // dnum
        {char[] a = {45}; op[151] = getOpcodeTbs(a);}
        op[152] = getOpcodeRnm(36, 194); // dnum
        {int[] a = {154,155,156,157}; op[153] = getOpcodeCat(a);}
        op[154] = getOpcodeRnm(34, 188); // Hex
        op[155] = getOpcodeRnm(38, 198); // xnum
        {char[] a = {45}; op[156] = getOpcodeTbs(a);}
        op[157] = getOpcodeRnm(38, 198); // xnum
        {int[] a = {159,160,161,162}; op[158] = getOpcodeCat(a);}
        op[159] = getOpcodeRnm(35, 191); // Bin
        op[160] = getOpcodeRnm(37, 196); // bnum
        {char[] a = {45}; op[161] = getOpcodeTbs(a);}
        op[162] = getOpcodeRnm(37, 196); // bnum
        {int[] a = {164,171,178}; op[163] = getOpcodeAlt(a);}
        {int[] a = {165,166,167}; op[164] = getOpcodeCat(a);}
        op[165] = getOpcodeRnm(33, 185); // Dec
        op[166] = getOpcodeRnm(36, 194); // dnum
        op[167] = getOpcodeRep((char)0, Character.MAX_VALUE, 168);
        {int[] a = {169,170}; op[168] = getOpcodeCat(a);}
        {char[] a = {46}; op[169] = getOpcodeTbs(a);}
        op[170] = getOpcodeRnm(36, 194); // dnum
        {int[] a = {172,173,174}; op[171] = getOpcodeCat(a);}
        op[172] = getOpcodeRnm(34, 188); // Hex
        op[173] = getOpcodeRnm(38, 198); // xnum
        op[174] = getOpcodeRep((char)0, Character.MAX_VALUE, 175);
        {int[] a = {176,177}; op[175] = getOpcodeCat(a);}
        {char[] a = {46}; op[176] = getOpcodeTbs(a);}
        op[177] = getOpcodeRnm(38, 198); // xnum
        {int[] a = {179,180,181}; op[178] = getOpcodeCat(a);}
        op[179] = getOpcodeRnm(35, 191); // Bin
        op[180] = getOpcodeRnm(37, 196); // bnum
        op[181] = getOpcodeRep((char)0, Character.MAX_VALUE, 182);
        {int[] a = {183,184}; op[182] = getOpcodeCat(a);}
        {char[] a = {46}; op[183] = getOpcodeTbs(a);}
        op[184] = getOpcodeRnm(37, 196); // bnum
        {int[] a = {186,187}; op[185] = getOpcodeAlt(a);}
        {char[] a = {68}; op[186] = getOpcodeTbs(a);}
        {char[] a = {100}; op[187] = getOpcodeTbs(a);}
        {int[] a = {189,190}; op[188] = getOpcodeAlt(a);}
        {char[] a = {88}; op[189] = getOpcodeTbs(a);}
        {char[] a = {120}; op[190] = getOpcodeTbs(a);}
        {int[] a = {192,193}; op[191] = getOpcodeAlt(a);}
        {char[] a = {66}; op[192] = getOpcodeTbs(a);}
        {char[] a = {98}; op[193] = getOpcodeTbs(a);}
        op[194] = getOpcodeRep((char)1, Character.MAX_VALUE, 195);
        op[195] = getOpcodeTrg((char)48, (char)57);
        op[196] = getOpcodeRep((char)1, Character.MAX_VALUE, 197);
        op[197] = getOpcodeTrg((char)48, (char)49);
        op[198] = getOpcodeRep((char)1, Character.MAX_VALUE, 199);
        {int[] a = {200,201,202}; op[199] = getOpcodeAlt(a);}
        op[200] = getOpcodeTrg((char)48, (char)57);
        op[201] = getOpcodeTrg((char)65, (char)70);
        op[202] = getOpcodeTrg((char)97, (char)102);
        {int[] a = {204,207}; op[203] = getOpcodeCat(a);}
        {int[] a = {205,206}; op[204] = getOpcodeAlt(a);}
        op[205] = getOpcodeTrg((char)97, (char)122);
        op[206] = getOpcodeTrg((char)65, (char)90);
        op[207] = getOpcodeRep((char)0, Character.MAX_VALUE, 208);
        {int[] a = {209,210,211,212}; op[208] = getOpcodeAlt(a);}
        op[209] = getOpcodeTrg((char)97, (char)122);
        op[210] = getOpcodeTrg((char)65, (char)90);
        op[211] = getOpcodeTrg((char)48, (char)57);
        {char[] a = {45}; op[212] = getOpcodeTbs(a);}
        {int[] a = {214,215,216}; op[213] = getOpcodeAlt(a);}
        {char[] a = {32}; op[214] = getOpcodeTbs(a);}
        {char[] a = {9}; op[215] = getOpcodeTbs(a);}
        op[216] = getOpcodeRnm(44, 226); // comment
        {int[] a = {218,219,220,221}; op[217] = getOpcodeAlt(a);}
        {char[] a = {32}; op[218] = getOpcodeTbs(a);}
        {char[] a = {9}; op[219] = getOpcodeTbs(a);}
        op[220] = getOpcodeRnm(44, 226); // comment
        op[221] = getOpcodeRnm(46, 236); // LineContinue
        op[222] = getOpcodeRep((char)0, Character.MAX_VALUE, 223);
        op[223] = getOpcodeRnm(41, 217); // fsp
        op[224] = getOpcodeRep((char)1, Character.MAX_VALUE, 225);
        op[225] = getOpcodeRnm(41, 217); // fsp
        {int[] a = {227,228}; op[226] = getOpcodeCat(a);}
        {char[] a = {59}; op[227] = getOpcodeTbs(a);}
        op[228] = getOpcodeRep((char)0, Character.MAX_VALUE, 229);
        {int[] a = {230,231}; op[229] = getOpcodeAlt(a);}
        op[230] = getOpcodeTrg((char)32, (char)127);
        {char[] a = {9}; op[231] = getOpcodeTbs(a);}
        {int[] a = {233,234,235}; op[232] = getOpcodeAlt(a);}
        {char[] a = {13,10}; op[233] = getOpcodeTbs(a);}
        {char[] a = {10}; op[234] = getOpcodeTbs(a);}
        {char[] a = {13}; op[235] = getOpcodeTbs(a);}
        {int[] a = {237,238}; op[236] = getOpcodeCat(a);}
        op[237] = getOpcodeRnm(45, 232); // LineEnd
        {int[] a = {239,240}; op[238] = getOpcodeAlt(a);}
        {char[] a = {32}; op[239] = getOpcodeTbs(a);}
        {char[] a = {9}; op[240] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; apg.GeneratorGrammar");
        out.println(";");
        out.println(";********************************************************************");
        out.println(";  APG - an ABNF Parser Generator");
        out.println(";  Copyright (C) 2011 Lowell D. Thomas, all rights reserved");
        out.println(";");
        out.println(";    author: Lowell D. Thomas");
        out.println(";            lowell@coasttocoastresearch.com");
        out.println(";            http://www.coasttocoastresearch.com");
        out.println(";");
        out.println(";   purpose: ABNF for SABNF");
        out.println(";");
        out.println(";*********************************************************************");
        out.println("; symbol alphabet is ASCII");
        out.println("; code points: 9, 10, 13, 32-126");
        out.println(";");
        out.println("File          = *(BlankLine/Rule/RuleError)");
        out.println("BlankLine     = *sp LineEnd");
        out.println("Rule          = NameDef owsp (IncAlt/DefinedAs) owsp Alternation owsp LineEnd");
        out.println("RuleError     = *fsp LineEnd");
        out.println("Alternation   = Concatenation *(AltOp Concatenation)");
        out.println("Concatenation = owsp Repetition *(CatOp Repetition)");
        out.println("Repetition    = Repeat / Predicate / Element");
        out.println("Repeat        = Rep Element");
        out.println("Predicate     = (AndOp / NotOp) Element");
        out.println("Rep           = (rep-min StarOp rep-max) /");
        out.println("                (rep-min StarOp)         /");
        out.println("                (StarOp rep-max)         /");
        out.println("                 StarOp                  /");
        out.println("                 rep-min-max");
        out.println("Element       = TrgOp   /");
        out.println("                TbsOp   /");
        out.println("                TlsOp   /");
        out.println("                TcsOp   /");
        out.println("                UdtOp   /");
        out.println("                RnmOp   /");
        out.println("                Group   /");
        out.println("                Option  /");
        out.println("                ProsVal");
        out.println("Group         = %d40 owsp  Alternation owsp %d41");
        out.println("Option        = %d91 owsp Alternation owsp %d93");
        out.println("ProsVal       = %d60 *(%d32-61/%d63-127/%d9) %d62");
        out.println("");
        out.println("NameDef       = alphanum");
        out.println("DefinedAs     = %d61");
        out.println("IncAlt        = %d61.47");
        out.println("RnmOp         = alphanum");
        out.println("UdtOp         = (%d117.95/%d101.95) alphanum");
        out.println("AltOp         = owsp %d47");
        out.println("CatOp         = wsp");
        out.println("StarOp        = %d42");
        out.println("AndOp         = %d38");
        out.println("NotOp         = %d33");
        out.println("TrgOp         = %d37 TrgRange");
        out.println("TbsOp         = %d37 TbsString");
        out.println("TlsOp         = [ \"%i\" ] %d34 *(%d32-33/%d35-127/%d9) %d34");
        out.println("TcsOp         = %d39 *(%d32-38/%d40-127/%d9) %d39 / \"%s\" %d34 *(%d32-33/%d35-127/%d9) %d34");
        out.println("");
        out.println("rep-min       = 1*(%d48-57)");
        out.println("rep-min-max   = 1*(%d48-57)");
        out.println("rep-max       = 1*(%d48-57)");
        out.println("TrgRange      = (Dec dnum %d45 dnum) / (Hex xnum %d45 xnum) / (Bin bnum %d45 bnum)");
        out.println("TbsString     = (Dec dnum *(%d46 dnum)) / (Hex xnum *(%d46 xnum)) / (Bin bnum *(%d46 bnum))");
        out.println("Dec           = (%d68/%d100)");
        out.println("Hex           = (%d88/%d120)");
        out.println("Bin           = (%d66/%d98)");
        out.println("dnum          = 1*(%d48-57)");
        out.println("bnum          = 1*%d48-49");
        out.println("xnum          = 1*(%d48-57 / %d65-70 / %d97-102)");
        out.println("");
        out.println("; Basics");
        out.println("alphanum      = (%d97-122/%d65-90) *(%d97-122/%d65-90/%d48-57/%d45)");
        out.println("sp            = %d32    /");
        out.println("                %d9     /");
        out.println("                comment");
        out.println("fsp           = %d32    /");
        out.println("                %d9     /");
        out.println("                comment /");
        out.println("                LineContinue");
        out.println("owsp          = *fsp");
        out.println("wsp           = 1*fsp");
        out.println("comment       = %d59 *(%d32-127 / %d9)");
        out.println("LineEnd       = %d13.10 / %d10 /%d13");
        out.println("LineContinue  = LineEnd (%d32/%d9)");
    }
}
