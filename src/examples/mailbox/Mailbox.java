// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.mailbox;

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

public class Mailbox extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new Mailbox(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 30;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>5</code>, name = <code>"address-literal"</code> */
        ADDRESS_LITERAL("address-literal", 5, 25, 7),
        /** id = <code>26</code>, name = <code>"ALPHA"</code> */
        ALPHA("ALPHA", 26, 156, 3),
        /** id = <code>25</code>, name = <code>"atext"</code> */
        ATEXT("atext", 25, 134, 22),
        /** id = <code>8</code>, name = <code>"Atom"</code> */
        ATOM("Atom", 8, 41, 2),
        /** id = <code>17</code>, name = <code>"dcontent"</code> */
        DCONTENT("dcontent", 17, 74, 3),
        /** id = <code>27</code>, name = <code>"DIGIT"</code> */
        DIGIT("DIGIT", 27, 159, 1),
        /** id = <code>1</code>, name = <code>"Domain"</code> */
        DOMAIN("Domain", 1, 6, 6),
        /** id = <code>7</code>, name = <code>"Dot-string"</code> */
        DOT_STRING("Dot-string", 7, 35, 6),
        /** id = <code>28</code>, name = <code>"DQUOTE"</code> */
        DQUOTE("DQUOTE", 28, 160, 1),
        /** id = <code>15</code>, name = <code>"General-address-literal"</code> */
        GENERAL_ADDRESS_LITERAL("General-address-literal", 15, 67, 5),
        /** id = <code>29</code>, name = <code>"HEXDIG"</code> */
        HEXDIG("HEXDIG", 29, 161, 8),
        /** id = <code>13</code>, name = <code>"IPv4-address-literal"</code> */
        IPV4_ADDRESS_LITERAL("IPv4-address-literal", 13, 58, 6),
        /** id = <code>19</code>, name = <code>"IPv6-addr"</code> */
        IPV6_ADDR("IPv6-addr", 19, 79, 5),
        /** id = <code>14</code>, name = <code>"IPv6-address-literal"</code> */
        IPV6_ADDRESS_LITERAL("IPv6-address-literal", 14, 64, 3),
        /** id = <code>22</code>, name = <code>"IPv6-comp"</code> */
        IPV6_COMP("IPv6-comp", 22, 92, 16),
        /** id = <code>21</code>, name = <code>"IPv6-full"</code> */
        IPV6_FULL("IPv6-full", 21, 86, 6),
        /** id = <code>20</code>, name = <code>"IPv6-hex"</code> */
        IPV6_HEX("IPv6-hex", 20, 84, 2),
        /** id = <code>24</code>, name = <code>"IPv6v4-comp"</code> */
        IPV6V4_COMP("IPv6v4-comp", 24, 116, 18),
        /** id = <code>23</code>, name = <code>"IPv6v4-full"</code> */
        IPV6V4_FULL("IPv6v4-full", 23, 108, 8),
        /** id = <code>4</code>, name = <code>"Ldh-str"</code> */
        LDH_STR("Ldh-str", 4, 20, 5),
        /** id = <code>3</code>, name = <code>"Let-dig"</code> */
        LET_DIG("Let-dig", 3, 17, 3),
        /** id = <code>6</code>, name = <code>"Local-part"</code> */
        LOCAL_PART("Local-part", 6, 32, 3),
        /** id = <code>0</code>, name = <code>"Mailbox"</code> */
        MAILBOX("Mailbox", 0, 0, 6),
        /** id = <code>10</code>, name = <code>"QcontentSMTP"</code> */
        QCONTENTSMTP("QcontentSMTP", 10, 48, 3),
        /** id = <code>12</code>, name = <code>"qtextSMTP"</code> */
        QTEXTSMTP("qtextSMTP", 12, 54, 4),
        /** id = <code>11</code>, name = <code>"quoted-pairSMTP"</code> */
        QUOTED_PAIRSMTP("quoted-pairSMTP", 11, 51, 3),
        /** id = <code>9</code>, name = <code>"Quoted-string"</code> */
        QUOTED_STRING("Quoted-string", 9, 43, 5),
        /** id = <code>18</code>, name = <code>"Snum"</code> */
        SNUM("Snum", 18, 77, 2),
        /** id = <code>16</code>, name = <code>"Standardized-tag"</code> */
        STANDARDIZED_TAG("Standardized-tag", 16, 72, 2),
        /** id = <code>2</code>, name = <code>"sub-domain"</code> */
        SUB_DOMAIN("sub-domain", 2, 12, 5);
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
    private static Mailbox factoryInstance = null;
    private Mailbox(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[30];
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
    	Opcode[] op = new Opcode[169];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2,3}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(6, 32); // Local-part
        {char[] a = {64}; op[2] = getOpcodeTls(a);}
        {int[] a = {4,5}; op[3] = getOpcodeAlt(a);}
        op[4] = getOpcodeRnm(1, 6); // Domain
        op[5] = getOpcodeRnm(5, 25); // address-literal
        {int[] a = {7,8}; op[6] = getOpcodeCat(a);}
        op[7] = getOpcodeRnm(2, 12); // sub-domain
        op[8] = getOpcodeRep((char)0, Character.MAX_VALUE, 9);
        {int[] a = {10,11}; op[9] = getOpcodeCat(a);}
        {char[] a = {46}; op[10] = getOpcodeTls(a);}
        op[11] = getOpcodeRnm(2, 12); // sub-domain
        {int[] a = {13,15}; op[12] = getOpcodeCat(a);}
        op[13] = getOpcodeRep((char)1, Character.MAX_VALUE, 14);
        op[14] = getOpcodeRnm(3, 17); // Let-dig
        op[15] = getOpcodeRep((char)0, Character.MAX_VALUE, 16);
        op[16] = getOpcodeRnm(4, 20); // Ldh-str
        {int[] a = {18,19}; op[17] = getOpcodeAlt(a);}
        op[18] = getOpcodeRnm(26, 156); // ALPHA
        op[19] = getOpcodeRnm(27, 159); // DIGIT
        {int[] a = {21,23}; op[20] = getOpcodeCat(a);}
        op[21] = getOpcodeRep((char)0, Character.MAX_VALUE, 22);
        {char[] a = {45}; op[22] = getOpcodeTls(a);}
        op[23] = getOpcodeRep((char)1, Character.MAX_VALUE, 24);
        op[24] = getOpcodeRnm(3, 17); // Let-dig
        {int[] a = {26,27,31}; op[25] = getOpcodeCat(a);}
        {char[] a = {91}; op[26] = getOpcodeTls(a);}
        {int[] a = {28,29,30}; op[27] = getOpcodeAlt(a);}
        op[28] = getOpcodeRnm(13, 58); // IPv4-address-literal
        op[29] = getOpcodeRnm(14, 64); // IPv6-address-literal
        op[30] = getOpcodeRnm(15, 67); // General-address-literal
        {char[] a = {93}; op[31] = getOpcodeTls(a);}
        {int[] a = {33,34}; op[32] = getOpcodeAlt(a);}
        op[33] = getOpcodeRnm(7, 35); // Dot-string
        op[34] = getOpcodeRnm(9, 43); // Quoted-string
        {int[] a = {36,37}; op[35] = getOpcodeCat(a);}
        op[36] = getOpcodeRnm(8, 41); // Atom
        op[37] = getOpcodeRep((char)0, Character.MAX_VALUE, 38);
        {int[] a = {39,40}; op[38] = getOpcodeCat(a);}
        {char[] a = {46}; op[39] = getOpcodeTls(a);}
        op[40] = getOpcodeRnm(8, 41); // Atom
        op[41] = getOpcodeRep((char)1, Character.MAX_VALUE, 42);
        op[42] = getOpcodeRnm(25, 134); // atext
        {int[] a = {44,45,47}; op[43] = getOpcodeCat(a);}
        op[44] = getOpcodeRnm(28, 160); // DQUOTE
        op[45] = getOpcodeRep((char)0, Character.MAX_VALUE, 46);
        op[46] = getOpcodeRnm(10, 48); // QcontentSMTP
        op[47] = getOpcodeRnm(28, 160); // DQUOTE
        {int[] a = {49,50}; op[48] = getOpcodeAlt(a);}
        op[49] = getOpcodeRnm(12, 54); // qtextSMTP
        op[50] = getOpcodeRnm(11, 51); // quoted-pairSMTP
        {int[] a = {52,53}; op[51] = getOpcodeCat(a);}
        {char[] a = {92}; op[52] = getOpcodeTbs(a);}
        op[53] = getOpcodeTrg((char)32, (char)126);
        {int[] a = {55,56,57}; op[54] = getOpcodeAlt(a);}
        op[55] = getOpcodeTrg((char)32, (char)33);
        op[56] = getOpcodeTrg((char)35, (char)91);
        op[57] = getOpcodeTrg((char)93, (char)126);
        {int[] a = {59,60}; op[58] = getOpcodeCat(a);}
        op[59] = getOpcodeRnm(18, 77); // Snum
        op[60] = getOpcodeRep((char)3, (char)3, 61);
        {int[] a = {62,63}; op[61] = getOpcodeCat(a);}
        {char[] a = {46}; op[62] = getOpcodeTls(a);}
        op[63] = getOpcodeRnm(18, 77); // Snum
        {int[] a = {65,66}; op[64] = getOpcodeCat(a);}
        {char[] a = {73,80,118,54,58}; op[65] = getOpcodeTls(a);}
        op[66] = getOpcodeRnm(19, 79); // IPv6-addr
        {int[] a = {68,69,70}; op[67] = getOpcodeCat(a);}
        op[68] = getOpcodeRnm(16, 72); // Standardized-tag
        {char[] a = {58}; op[69] = getOpcodeTls(a);}
        op[70] = getOpcodeRep((char)1, Character.MAX_VALUE, 71);
        op[71] = getOpcodeRnm(17, 74); // dcontent
        op[72] = getOpcodeRep((char)1, Character.MAX_VALUE, 73);
        op[73] = getOpcodeRnm(4, 20); // Ldh-str
        {int[] a = {75,76}; op[74] = getOpcodeAlt(a);}
        op[75] = getOpcodeTrg((char)33, (char)90);
        op[76] = getOpcodeTrg((char)94, (char)126);
        op[77] = getOpcodeRep((char)1, (char)3, 78);
        op[78] = getOpcodeRnm(27, 159); // DIGIT
        {int[] a = {80,81,82,83}; op[79] = getOpcodeAlt(a);}
        op[80] = getOpcodeRnm(21, 86); // IPv6-full
        op[81] = getOpcodeRnm(22, 92); // IPv6-comp
        op[82] = getOpcodeRnm(23, 108); // IPv6v4-full
        op[83] = getOpcodeRnm(24, 116); // IPv6v4-comp
        op[84] = getOpcodeRep((char)1, (char)4, 85);
        op[85] = getOpcodeRnm(29, 161); // HEXDIG
        {int[] a = {87,88}; op[86] = getOpcodeCat(a);}
        op[87] = getOpcodeRnm(20, 84); // IPv6-hex
        op[88] = getOpcodeRep((char)7, (char)7, 89);
        {int[] a = {90,91}; op[89] = getOpcodeCat(a);}
        {char[] a = {58}; op[90] = getOpcodeTls(a);}
        op[91] = getOpcodeRnm(20, 84); // IPv6-hex
        {int[] a = {93,100,101}; op[92] = getOpcodeCat(a);}
        op[93] = getOpcodeRep((char)0, (char)1, 94);
        {int[] a = {95,96}; op[94] = getOpcodeCat(a);}
        op[95] = getOpcodeRnm(20, 84); // IPv6-hex
        op[96] = getOpcodeRep((char)0, (char)5, 97);
        {int[] a = {98,99}; op[97] = getOpcodeCat(a);}
        {char[] a = {58}; op[98] = getOpcodeTls(a);}
        op[99] = getOpcodeRnm(20, 84); // IPv6-hex
        {char[] a = {58,58}; op[100] = getOpcodeTls(a);}
        op[101] = getOpcodeRep((char)0, (char)1, 102);
        {int[] a = {103,104}; op[102] = getOpcodeCat(a);}
        op[103] = getOpcodeRnm(20, 84); // IPv6-hex
        op[104] = getOpcodeRep((char)0, (char)5, 105);
        {int[] a = {106,107}; op[105] = getOpcodeCat(a);}
        {char[] a = {58}; op[106] = getOpcodeTls(a);}
        op[107] = getOpcodeRnm(20, 84); // IPv6-hex
        {int[] a = {109,110,114,115}; op[108] = getOpcodeCat(a);}
        op[109] = getOpcodeRnm(20, 84); // IPv6-hex
        op[110] = getOpcodeRep((char)5, (char)5, 111);
        {int[] a = {112,113}; op[111] = getOpcodeCat(a);}
        {char[] a = {58}; op[112] = getOpcodeTls(a);}
        op[113] = getOpcodeRnm(20, 84); // IPv6-hex
        {char[] a = {58}; op[114] = getOpcodeTls(a);}
        op[115] = getOpcodeRnm(13, 58); // IPv4-address-literal
        {int[] a = {117,124,125,133}; op[116] = getOpcodeCat(a);}
        op[117] = getOpcodeRep((char)0, (char)1, 118);
        {int[] a = {119,120}; op[118] = getOpcodeCat(a);}
        op[119] = getOpcodeRnm(20, 84); // IPv6-hex
        op[120] = getOpcodeRep((char)0, (char)3, 121);
        {int[] a = {122,123}; op[121] = getOpcodeCat(a);}
        {char[] a = {58}; op[122] = getOpcodeTls(a);}
        op[123] = getOpcodeRnm(20, 84); // IPv6-hex
        {char[] a = {58,58}; op[124] = getOpcodeTls(a);}
        op[125] = getOpcodeRep((char)0, (char)1, 126);
        {int[] a = {127,128,132}; op[126] = getOpcodeCat(a);}
        op[127] = getOpcodeRnm(20, 84); // IPv6-hex
        op[128] = getOpcodeRep((char)0, (char)3, 129);
        {int[] a = {130,131}; op[129] = getOpcodeCat(a);}
        {char[] a = {58}; op[130] = getOpcodeTls(a);}
        op[131] = getOpcodeRnm(20, 84); // IPv6-hex
        {char[] a = {58}; op[132] = getOpcodeTls(a);}
        op[133] = getOpcodeRnm(13, 58); // IPv4-address-literal
        {int[] a = {135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155}; op[134] = getOpcodeAlt(a);}
        op[135] = getOpcodeRnm(26, 156); // ALPHA
        op[136] = getOpcodeRnm(27, 159); // DIGIT
        {char[] a = {33}; op[137] = getOpcodeTls(a);}
        {char[] a = {35}; op[138] = getOpcodeTls(a);}
        {char[] a = {36}; op[139] = getOpcodeTls(a);}
        {char[] a = {37}; op[140] = getOpcodeTls(a);}
        {char[] a = {38}; op[141] = getOpcodeTls(a);}
        {char[] a = {39}; op[142] = getOpcodeTls(a);}
        {char[] a = {42}; op[143] = getOpcodeTls(a);}
        {char[] a = {43}; op[144] = getOpcodeTls(a);}
        {char[] a = {45}; op[145] = getOpcodeTls(a);}
        {char[] a = {47}; op[146] = getOpcodeTls(a);}
        {char[] a = {61}; op[147] = getOpcodeTls(a);}
        {char[] a = {63}; op[148] = getOpcodeTls(a);}
        {char[] a = {94}; op[149] = getOpcodeTls(a);}
        {char[] a = {95}; op[150] = getOpcodeTls(a);}
        {char[] a = {96}; op[151] = getOpcodeTls(a);}
        {char[] a = {123}; op[152] = getOpcodeTls(a);}
        {char[] a = {124}; op[153] = getOpcodeTls(a);}
        {char[] a = {125}; op[154] = getOpcodeTls(a);}
        {char[] a = {126}; op[155] = getOpcodeTls(a);}
        {int[] a = {157,158}; op[156] = getOpcodeAlt(a);}
        op[157] = getOpcodeTrg((char)65, (char)90);
        op[158] = getOpcodeTrg((char)97, (char)122);
        op[159] = getOpcodeTrg((char)48, (char)57);
        {char[] a = {34}; op[160] = getOpcodeTbs(a);}
        {int[] a = {162,163,164,165,166,167,168}; op[161] = getOpcodeAlt(a);}
        op[162] = getOpcodeRnm(27, 159); // DIGIT
        {char[] a = {65}; op[163] = getOpcodeTls(a);}
        {char[] a = {66}; op[164] = getOpcodeTls(a);}
        {char[] a = {67}; op[165] = getOpcodeTls(a);}
        {char[] a = {68}; op[166] = getOpcodeTls(a);}
        {char[] a = {69}; op[167] = getOpcodeTls(a);}
        {char[] a = {70}; op[168] = getOpcodeTls(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.mailbox.Mailbox");
        out.println(";");
        out.println(";");
        out.println("; From RFC 5321");
        out.println("; Rules not referenced by Mailbox removed");
        out.println("; \"atext\" taken from RFC 5322");
        out.println("; Core rules ALPHA, DIGIT, etc. from RFC 5234");
        out.println(";");
        out.println(";");
        out.println("Mailbox        = Local-part \"@\" ( Domain / address-literal )");
        out.println("Domain         = sub-domain *(\".\" sub-domain)");
        out.println(";sub-domain     = Let-dig [Ldh-str]");
        out.println("sub-domain     = 1*Let-dig *Ldh-str");
        out.println("Let-dig        = ALPHA / DIGIT");
        out.println(";Ldh-str        = *( ALPHA / DIGIT / \"-\" ) Let-dig");
        out.println("Ldh-str        = *\"-\" 1*Let-dig");
        out.println("address-literal  = \"[\" ( IPv4-address-literal /");
        out.println("                    IPv6-address-literal /");
        out.println("                    General-address-literal ) \"]\"");
        out.println("                    ; See Section 4.1.3");
        out.println("Local-part     = Dot-string / Quoted-string");
        out.println("                  ; MAY be case-sensitive");
        out.println("Dot-string     = Atom *(\".\"  Atom)");
        out.println("Atom           = 1*atext");
        out.println("Quoted-string  = DQUOTE *QcontentSMTP DQUOTE");
        out.println("QcontentSMTP   = qtextSMTP / quoted-pairSMTP");
        out.println("quoted-pairSMTP  = %d92 %d32-126");
        out.println("                    ; i.e., backslash followed by any ASCII");
        out.println("                    ; graphic (including itself) or SPace");
        out.println("qtextSMTP      = %d32-33 / %d35-91 / %d93-126");
        out.println("                  ; i.e., within a quoted string, any");
        out.println("                  ; ASCII graphic or space is permitted");
        out.println("                  ; without blackslash-quoting except");
        out.println("                  ; double-quote and the backslash itself.");
        out.println("IPv4-address-literal  = Snum 3(\".\"  Snum)");
        out.println("IPv6-address-literal  = \"IPv6:\" IPv6-addr");
        out.println("General-address-literal  = Standardized-tag \":\" 1*dcontent");
        out.println("Standardized-tag  = 1*Ldh-str");
        out.println("                     ; Standardized-tag MUST be specified in a");
        out.println("                     ; Standards-Track RFC and registered with IANA");
        out.println("dcontent       = %d33-90 / ; Printable US-ASCII");
        out.println("                  %d94-126 ; excl. \"[\", \"\\\", \"]\"");
        out.println("Snum           = 1*3DIGIT");
        out.println("                  ; representing a decimal integer");
        out.println("                  ; value in the range 0 through 255");
        out.println("IPv6-addr      = IPv6-full / IPv6-comp / IPv6v4-full / IPv6v4-comp");
        out.println("IPv6-hex       = 1*4HEXDIG");
        out.println("IPv6-full      = IPv6-hex 7(\":\" IPv6-hex)");
        out.println("IPv6-comp      = [IPv6-hex *5(\":\" IPv6-hex)] \"::\"");
        out.println("                  [IPv6-hex *5(\":\" IPv6-hex)]");
        out.println("                  ; The \"::\" represents at least 2 16-bit groups of");
        out.println("                  ; zeros.  No more than 6 groups in addition to the");
        out.println("                  ; \"::\" may be present.");
        out.println("IPv6v4-full    = IPv6-hex 5(\":\" IPv6-hex) \":\" IPv4-address-literal");
        out.println("IPv6v4-comp    = [IPv6-hex *3(\":\" IPv6-hex)] \"::\"");
        out.println("                  [IPv6-hex *3(\":\" IPv6-hex) \":\"]");
        out.println("                  IPv4-address-literal");
        out.println("                  ; The \"::\" represents at least 2 16-bit groups of");
        out.println("                  ; zeros.  No more than 4 groups in addition to the");
        out.println("                  ; \"::\" and IPv4-address-literal may be present.");
        out.println(";");
        out.println(";RFC 5322");
        out.println("atext           =   ALPHA / DIGIT /    ; Printable US-ASCII");
        out.println("                       \"!\" / \"#\" /     ; characters not including");
        out.println("                       \"$\" / \"%\" /     ; specials.  Used for atoms.");
        out.println("                       \"&\" / \"'\" /");
        out.println("                       \"*\" / \"+\" /");
        out.println("                       \"-\" / \"/\" /");
        out.println("                       \"=\" / \"?\" /");
        out.println("                       \"^\" / \"_\" /");
        out.println("                       \"`\" / \"{\" /");
        out.println("                       \"|\" / \"}\" /");
        out.println("                       \"~\"");
        out.println(";");
        out.println(";RFC 5234                          ABNF                      January 2008");
        out.println(";B.1.  Core Rules");
        out.println(";");
        out.println(";   Certain basic rules are in uppercase, such as SP, HTAB, CRLF, DIGIT,");
        out.println(";   ALPHA, etc.");
        out.println(";");
        out.println("ALPHA          =  %x41-5A / %x61-7A   ; A-Z / a-z");
        out.println("DIGIT          =  %x30-39");
        out.println("                       ; 0-9");
        out.println("DQUOTE         =  %x22");
        out.println("                       ; \" (Double Quote)");
        out.println("HEXDIG         =  DIGIT / \"A\" / \"B\" / \"C\" / \"D\" / \"E\" / \"F\"");
    }
}
