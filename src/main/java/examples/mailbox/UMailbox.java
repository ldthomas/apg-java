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

public class UMailbox extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new UMailbox(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 21;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>4</code>, name = <code>"address-literal"</code> */
        ADDRESS_LITERAL("address-literal", 4, 16, 7),
        /** id = <code>17</code>, name = <code>"compression"</code> */
        COMPRESSION("compression", 17, 87, 1),
        /** id = <code>2</code>, name = <code>"Domain"</code> */
        DOMAIN("Domain", 2, 7, 6),
        /** id = <code>1</code>, name = <code>"Domain-part"</code> */
        DOMAIN_PART("Domain-part", 1, 4, 3),
        /** id = <code>6</code>, name = <code>"Dot-string"</code> */
        DOT_STRING("Dot-string", 6, 26, 6),
        /** id = <code>20</code>, name = <code>"DQUOTE"</code> */
        DQUOTE("DQUOTE", 20, 114, 1),
        /** id = <code>13</code>, name = <code>"General-address-literal"</code> */
        GENERAL_ADDRESS_LITERAL("General-address-literal", 13, 56, 4),
        /** id = <code>11</code>, name = <code>"IPv4-address-literal"</code> */
        IPV4_ADDRESS_LITERAL("IPv4-address-literal", 11, 47, 6),
        /** id = <code>14</code>, name = <code>"IPv6-addr"</code> */
        IPV6_ADDR("IPv6-addr", 14, 60, 5),
        /** id = <code>12</code>, name = <code>"IPv6-address-literal"</code> */
        IPV6_ADDRESS_LITERAL("IPv6-address-literal", 12, 53, 3),
        /** id = <code>16</code>, name = <code>"IPv6-comp"</code> */
        IPV6_COMP("IPv6-comp", 16, 71, 16),
        /** id = <code>15</code>, name = <code>"IPv6-full"</code> */
        IPV6_FULL("IPv6-full", 15, 65, 6),
        /** id = <code>19</code>, name = <code>"IPv6v4-comp"</code> */
        IPV6V4_COMP("IPv6v4-comp", 19, 96, 18),
        /** id = <code>18</code>, name = <code>"IPv6v4-full"</code> */
        IPV6V4_FULL("IPv6v4-full", 18, 88, 8),
        /** id = <code>5</code>, name = <code>"Local-part"</code> */
        LOCAL_PART("Local-part", 5, 23, 3),
        /** id = <code>0</code>, name = <code>"Mailbox"</code> */
        MAILBOX("Mailbox", 0, 0, 4),
        /** id = <code>8</code>, name = <code>"QcontentSMTP"</code> */
        QCONTENTSMTP("QcontentSMTP", 8, 37, 3),
        /** id = <code>10</code>, name = <code>"qtextSMTP"</code> */
        QTEXTSMTP("qtextSMTP", 10, 43, 4),
        /** id = <code>9</code>, name = <code>"quoted-pairSMTP"</code> */
        QUOTED_PAIRSMTP("quoted-pairSMTP", 9, 40, 3),
        /** id = <code>7</code>, name = <code>"Quoted-string"</code> */
        QUOTED_STRING("Quoted-string", 7, 32, 5),
        /** id = <code>3</code>, name = <code>"sub-domain"</code> */
        SUB_DOMAIN("sub-domain", 3, 13, 3);
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
    public static int udtCount = 7;
    /** This enum provides easy to remember enum constants for locating the UDT identifiers and names.
     * The enum constants have the same spelling as the UDT names rendered in all caps with underscores replacing hyphens. */
    public enum UdtNames{
        /** id = <code>1</code>, name = <code>"e_ldh-str"</code> */
        E_LDH_STR(1, "e_ldh-str", true),
        /** id = <code>2</code>, name = <code>"u_atom"</code> */
        U_ATOM(2, "u_atom", false),
        /** id = <code>5</code>, name = <code>"u_dcontent"</code> */
        U_DCONTENT(5, "u_dcontent", false),
        /** id = <code>6</code>, name = <code>"u_ipv6-hex"</code> */
        U_IPV6_HEX(6, "u_ipv6-hex", false),
        /** id = <code>0</code>, name = <code>"u_let-dig"</code> */
        U_LET_DIG(0, "u_let-dig", false),
        /** id = <code>3</code>, name = <code>"u_snum"</code> */
        U_SNUM(3, "u_snum", false),
        /** id = <code>4</code>, name = <code>"u_standardized-tag"</code> */
        U_STANDARDIZED_TAG(4, "u_standardized-tag", false);
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
    private static UMailbox factoryInstance = null;
    private UMailbox(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[21];
        for(RuleNames r : RuleNames.values()){
            rules[r.ruleID()] = getRule(r.ruleID(), r.ruleName(), r.opcodeOffset(), r.opcodeCount());
        }
        return rules;
    }

    private static Udt[] getUdts(){
    	Udt[] udts = new Udt[7];
        for(UdtNames r : UdtNames.values()){
            udts[r.udtID()] = getUdt(r.udtID(), r.udtName(), r.udtMayBeEmpty());
        }
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[115];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2,3}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(5, 23); // Local-part
        {char[] a = {64}; op[2] = getOpcodeTls(a);}
        op[3] = getOpcodeRnm(1, 4); // Domain-part
        {int[] a = {5,6}; op[4] = getOpcodeAlt(a);}
        op[5] = getOpcodeRnm(2, 7); // Domain
        op[6] = getOpcodeRnm(4, 16); // address-literal
        {int[] a = {8,9}; op[7] = getOpcodeCat(a);}
        op[8] = getOpcodeRnm(3, 13); // sub-domain
        op[9] = getOpcodeRep((char)0, Character.MAX_VALUE, 10);
        {int[] a = {11,12}; op[10] = getOpcodeCat(a);}
        {char[] a = {46}; op[11] = getOpcodeTls(a);}
        op[12] = getOpcodeRnm(3, 13); // sub-domain
        {int[] a = {14,15}; op[13] = getOpcodeCat(a);}
        op[14] = getOpcodeUdt(0); // u_Let-dig
        op[15] = getOpcodeUdt(1); // e_Ldh-str
        {int[] a = {17,18,22}; op[16] = getOpcodeCat(a);}
        {char[] a = {91}; op[17] = getOpcodeTls(a);}
        {int[] a = {19,20,21}; op[18] = getOpcodeAlt(a);}
        op[19] = getOpcodeRnm(11, 47); // IPv4-address-literal
        op[20] = getOpcodeRnm(12, 53); // IPv6-address-literal
        op[21] = getOpcodeRnm(13, 56); // General-address-literal
        {char[] a = {93}; op[22] = getOpcodeTls(a);}
        {int[] a = {24,25}; op[23] = getOpcodeAlt(a);}
        op[24] = getOpcodeRnm(6, 26); // Dot-string
        op[25] = getOpcodeRnm(7, 32); // Quoted-string
        {int[] a = {27,28}; op[26] = getOpcodeCat(a);}
        op[27] = getOpcodeUdt(2); // u_Atom
        op[28] = getOpcodeRep((char)0, Character.MAX_VALUE, 29);
        {int[] a = {30,31}; op[29] = getOpcodeCat(a);}
        {char[] a = {46}; op[30] = getOpcodeTls(a);}
        op[31] = getOpcodeUdt(2); // u_Atom
        {int[] a = {33,34,36}; op[32] = getOpcodeCat(a);}
        op[33] = getOpcodeRnm(20, 114); // DQUOTE
        op[34] = getOpcodeRep((char)0, Character.MAX_VALUE, 35);
        op[35] = getOpcodeRnm(8, 37); // QcontentSMTP
        op[36] = getOpcodeRnm(20, 114); // DQUOTE
        {int[] a = {38,39}; op[37] = getOpcodeAlt(a);}
        op[38] = getOpcodeRnm(10, 43); // qtextSMTP
        op[39] = getOpcodeRnm(9, 40); // quoted-pairSMTP
        {int[] a = {41,42}; op[40] = getOpcodeCat(a);}
        {char[] a = {92}; op[41] = getOpcodeTbs(a);}
        op[42] = getOpcodeTrg((char)32, (char)126);
        {int[] a = {44,45,46}; op[43] = getOpcodeAlt(a);}
        op[44] = getOpcodeTrg((char)32, (char)33);
        op[45] = getOpcodeTrg((char)35, (char)91);
        op[46] = getOpcodeTrg((char)93, (char)126);
        {int[] a = {48,49}; op[47] = getOpcodeCat(a);}
        op[48] = getOpcodeUdt(3); // u_Snum
        op[49] = getOpcodeRep((char)3, (char)3, 50);
        {int[] a = {51,52}; op[50] = getOpcodeCat(a);}
        {char[] a = {46}; op[51] = getOpcodeTls(a);}
        op[52] = getOpcodeUdt(3); // u_Snum
        {int[] a = {54,55}; op[53] = getOpcodeCat(a);}
        {char[] a = {73,80,118,54,58}; op[54] = getOpcodeTls(a);}
        op[55] = getOpcodeRnm(14, 60); // IPv6-addr
        {int[] a = {57,58,59}; op[56] = getOpcodeCat(a);}
        op[57] = getOpcodeUdt(4); // u_Standardized-tag
        {char[] a = {58}; op[58] = getOpcodeTls(a);}
        op[59] = getOpcodeUdt(5); // u_dcontent
        {int[] a = {61,62,63,64}; op[60] = getOpcodeAlt(a);}
        op[61] = getOpcodeRnm(15, 65); // IPv6-full
        op[62] = getOpcodeRnm(16, 71); // IPv6-comp
        op[63] = getOpcodeRnm(18, 88); // IPv6v4-full
        op[64] = getOpcodeRnm(19, 96); // IPv6v4-comp
        {int[] a = {66,67}; op[65] = getOpcodeCat(a);}
        op[66] = getOpcodeUdt(6); // u_IPv6-hex
        op[67] = getOpcodeRep((char)7, (char)7, 68);
        {int[] a = {69,70}; op[68] = getOpcodeCat(a);}
        {char[] a = {58}; op[69] = getOpcodeTls(a);}
        op[70] = getOpcodeUdt(6); // u_IPv6-hex
        {int[] a = {72,79,80}; op[71] = getOpcodeCat(a);}
        op[72] = getOpcodeRep((char)0, (char)1, 73);
        {int[] a = {74,75}; op[73] = getOpcodeCat(a);}
        op[74] = getOpcodeUdt(6); // u_IPv6-hex
        op[75] = getOpcodeRep((char)0, (char)5, 76);
        {int[] a = {77,78}; op[76] = getOpcodeCat(a);}
        {char[] a = {58}; op[77] = getOpcodeTls(a);}
        op[78] = getOpcodeUdt(6); // u_IPv6-hex
        op[79] = getOpcodeRnm(17, 87); // compression
        op[80] = getOpcodeRep((char)0, (char)1, 81);
        {int[] a = {82,83}; op[81] = getOpcodeCat(a);}
        op[82] = getOpcodeUdt(6); // u_IPv6-hex
        op[83] = getOpcodeRep((char)0, (char)5, 84);
        {int[] a = {85,86}; op[84] = getOpcodeCat(a);}
        {char[] a = {58}; op[85] = getOpcodeTls(a);}
        op[86] = getOpcodeUdt(6); // u_IPv6-hex
        {char[] a = {58,58}; op[87] = getOpcodeTls(a);}
        {int[] a = {89,90,94,95}; op[88] = getOpcodeCat(a);}
        op[89] = getOpcodeUdt(6); // u_IPv6-hex
        op[90] = getOpcodeRep((char)5, (char)5, 91);
        {int[] a = {92,93}; op[91] = getOpcodeCat(a);}
        {char[] a = {58}; op[92] = getOpcodeTls(a);}
        op[93] = getOpcodeUdt(6); // u_IPv6-hex
        {char[] a = {58}; op[94] = getOpcodeTls(a);}
        op[95] = getOpcodeRnm(11, 47); // IPv4-address-literal
        {int[] a = {97,104,105,113}; op[96] = getOpcodeCat(a);}
        op[97] = getOpcodeRep((char)0, (char)1, 98);
        {int[] a = {99,100}; op[98] = getOpcodeCat(a);}
        op[99] = getOpcodeUdt(6); // u_IPv6-hex
        op[100] = getOpcodeRep((char)0, (char)3, 101);
        {int[] a = {102,103}; op[101] = getOpcodeCat(a);}
        {char[] a = {58}; op[102] = getOpcodeTls(a);}
        op[103] = getOpcodeUdt(6); // u_IPv6-hex
        {char[] a = {58,58}; op[104] = getOpcodeTls(a);}
        op[105] = getOpcodeRep((char)0, (char)1, 106);
        {int[] a = {107,108,112}; op[106] = getOpcodeCat(a);}
        op[107] = getOpcodeUdt(6); // u_IPv6-hex
        op[108] = getOpcodeRep((char)0, (char)3, 109);
        {int[] a = {110,111}; op[109] = getOpcodeCat(a);}
        {char[] a = {58}; op[110] = getOpcodeTls(a);}
        op[111] = getOpcodeUdt(6); // u_IPv6-hex
        {char[] a = {58}; op[112] = getOpcodeTls(a);}
        op[113] = getOpcodeRnm(11, 47); // IPv4-address-literal
        {char[] a = {34}; op[114] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.mailbox.UMailbox");
        out.println(";");
        out.println(";");
        out.println("; From RFC 5321");
        out.println("; Rules not referenced by Mailbox removed");
        out.println("; \"atext\" taken from RFC 5322");
        out.println("; Core rules ALPHA, DIGIT, etc. from RFC 5234");
        out.println(";");
        out.println(";");
        out.println("Mailbox        = Local-part \"@\" Domain-part");
        out.println("Domain-part    = Domain / address-literal");
        out.println("Domain         = sub-domain *(\".\" sub-domain)");
        out.println(";sub-domain     = Let-dig [Ldh-str]");
        out.println(";sub-domain     = 1*Let-dig *Ldh-str");
        out.println("sub-domain     = u_Let-dig e_Ldh-str");
        out.println(";Let-dig        = ALPHA / DIGIT");
        out.println(";Ldh-str        = *( ALPHA / DIGIT / \"-\" ) Let-dig");
        out.println(";Ldh-str        = *\"-\" 1*Let-dig");
        out.println("address-literal  = \"[\" ( IPv4-address-literal /");
        out.println("                    IPv6-address-literal /");
        out.println("                    General-address-literal ) \"]\"");
        out.println("                    ; See Section 4.1.3");
        out.println("Local-part     = Dot-string / Quoted-string");
        out.println("                  ; MAY be case-sensitive");
        out.println("Dot-string     = u_Atom *(\".\"  u_Atom)");
        out.println(";Atom           = 1*atext");
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
        out.println("IPv4-address-literal  = u_Snum 3(\".\"  u_Snum)");
        out.println("IPv6-address-literal  = \"IPv6:\" IPv6-addr");
        out.println(";General-address-literal  = Standardized-tag \":\" 1*dcontent");
        out.println("General-address-literal  = u_Standardized-tag \":\" u_dcontent");
        out.println(";Standardized-tag  = 1*Ldh-str");
        out.println(";Standardized-tag  = u_Ldh-str");
        out.println("                     ; Standardized-tag MUST be specified in a");
        out.println("                     ; Standards-Track RFC and registered with IANA");
        out.println(";dcontent       = %d33-90 / ; Printable US-ASCII");
        out.println(";                  %d94-126 ; excl. \"[\", \"\\\", \"]\"");
        out.println(";Snum           = 1*3DIGIT");
        out.println("                  ; representing a decimal integer");
        out.println("                  ; value in the range 0 through 255");
        out.println("IPv6-addr      = IPv6-full / IPv6-comp / IPv6v4-full / IPv6v4-comp");
        out.println(";IPv6-hex       = 1*4HEXDIG");
        out.println("IPv6-full      = u_IPv6-hex 7(\":\" u_IPv6-hex)");
        out.println("IPv6-comp      = [u_IPv6-hex *5(\":\" u_IPv6-hex)] compression");
        out.println("                  [u_IPv6-hex *5(\":\" u_IPv6-hex)]");
        out.println("                  ; The \"::\" represents at least 2 16-bit groups of");
        out.println("                  ; zeros.  No more than 6 groups in addition to the");
        out.println("                  ; \"::\" may be present.");
        out.println("compression    = \"::\";                   ");
        out.println("IPv6v4-full    = u_IPv6-hex 5(\":\" u_IPv6-hex) \":\" IPv4-address-literal");
        out.println("IPv6v4-comp    = [u_IPv6-hex *3(\":\" u_IPv6-hex)] \"::\"");
        out.println("                  [u_IPv6-hex *3(\":\" u_IPv6-hex) \":\"]");
        out.println("                  IPv4-address-literal");
        out.println("                  ; The \"::\" represents at least 2 16-bit groups of");
        out.println("                  ; zeros.  No more than 4 groups in addition to the");
        out.println("                  ; \"::\" and IPv4-address-literal may be present.");
        out.println(";");
        out.println(";RFC 5322");
        out.println(";atext           =   ALPHA / DIGIT /    ; Printable US-ASCII");
        out.println(";                       \"!\" / \"#\" /     ; characters not including");
        out.println(";                       \"$\" / \"%\" /     ; specials.  Used for atoms.");
        out.println(";                       \"&\" / \"'\" /");
        out.println(";                       \"*\" / \"+\" /");
        out.println(";                       \"-\" / \"/\" /");
        out.println(";                       \"=\" / \"?\" /");
        out.println(";                       \"^\" / \"_\" /");
        out.println(";                       \"`\" / \"{\" /");
        out.println(";                       \"|\" / \"}\" /");
        out.println(";                       \"~\"");
        out.println(";");
        out.println(";RFC 5234                          ABNF                      January 2008");
        out.println(";B.1.  Core Rules");
        out.println(";");
        out.println(";   Certain basic rules are in uppercase, such as SP, HTAB, CRLF, DIGIT,");
        out.println(";   ALPHA, etc.");
        out.println(";");
        out.println(";ALPHA          =  %x41-5A / %x61-7A   ; A-Z / a-z");
        out.println(";DIGIT          =  %x30-39");
        out.println("                       ; 0-9");
        out.println("DQUOTE         =  %x22");
        out.println("                       ; \" (Double Quote)");
        out.println(";HEXDIG         =  DIGIT / \"A\" / \"B\" / \"C\" / \"D\" / \"E\" / \"F\"");
    }
}
