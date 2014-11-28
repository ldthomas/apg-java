package examples.demo;

import apg.Grammar;
import java.io.PrintStream;
/** This class has been generated automatically from an SABNF grammer by
 * Java APG, the {@link apg.Generator} class.<br>
 * It is an extension of the {@link apg.Grammar}
 * class containing additional members and enums not found
 * in the base class.<br>
 * The function {@link #getInstance()} will return a reference to a static,
 * singleton instance of the class.
 */
public class UExpressions extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new UExpressions(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 5;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>0</code>, name = <code>"E"</code> */
        E("E", 0, 0, 3),
        /** id = <code>1</code>, name = <code>"Eprime"</code> */
        EPRIME("Eprime", 1, 3, 6),
        /** id = <code>4</code>, name = <code>"F"</code> */
        F("F", 4, 18, 6),
        /** id = <code>2</code>, name = <code>"T"</code> */
        T("T", 2, 9, 3),
        /** id = <code>3</code>, name = <code>"Tprime"</code> */
        TPRIME("Tprime", 3, 12, 6);
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
        /** id = <code>0</code>, name = <code>"u_id"</code> */
        U_ID(0, "u_id", false);
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
    private static UExpressions factoryInstance = null;
    private UExpressions(Rule[] rules, Udt[] udts, Opcode[] opcodes){
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
    	Udt[] udts = new Udt[1];
        for(UdtNames r : UdtNames.values()){
            udts[r.udtID()] = getUdt(r.udtID(), r.udtName(), r.udtMayBeEmpty());
        }
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[24];
        {int[] a = {1,2}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(2, 9); // T
        op[2] = getOpcodeRnm(1, 3); // Eprime
        {int[] a = {4,8}; op[3] = getOpcodeAlt(a);}
        {int[] a = {5,6,7}; op[4] = getOpcodeCat(a);}
        {char[] a = {43}; op[5] = getOpcodeTls(a);}
        op[6] = getOpcodeRnm(2, 9); // T
        op[7] = getOpcodeRnm(1, 3); // Eprime
        {char[] a = {}; op[8] = getOpcodeTls(a);}
        {int[] a = {10,11}; op[9] = getOpcodeCat(a);}
        op[10] = getOpcodeRnm(4, 18); // F
        op[11] = getOpcodeRnm(3, 12); // Tprime
        {int[] a = {13,17}; op[12] = getOpcodeAlt(a);}
        {int[] a = {14,15,16}; op[13] = getOpcodeCat(a);}
        {char[] a = {42}; op[14] = getOpcodeTls(a);}
        op[15] = getOpcodeRnm(4, 18); // F
        op[16] = getOpcodeRnm(3, 12); // Tprime
        {char[] a = {}; op[17] = getOpcodeTls(a);}
        {int[] a = {19,23}; op[18] = getOpcodeAlt(a);}
        {int[] a = {20,21,22}; op[19] = getOpcodeCat(a);}
        {char[] a = {40}; op[20] = getOpcodeTls(a);}
        op[21] = getOpcodeRnm(0, 0); // E
        {char[] a = {41}; op[22] = getOpcodeTls(a);}
        op[23] = getOpcodeUdt(0); // u_id
        return op;
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.demo.UExpressions");
        out.println(";");
        out.println(";");
        out.println("; Grammar (4.2) - Modified for SABNF syntax with UDT");
        out.println("; Aho, Lam, Sethi and Ullman, \"Compilers: Principles, Techniques, & Tools\",");
        out.println("; 2nd ed., pp. 193, Addison Wesley, (2007)");
        out.println(";");
        out.println("E      = T Eprime               ; expression = sum of terms");
        out.println("Eprime = \"+\" T Eprime / \"\"");
        out.println("T      = F Tprime               ; term = product of factors");
        out.println("Tprime = \"*\" F Tprime / \"\"");
        out.println("F      = \"(\" E \")\" / u_id       ; factor = parenthesized expression or identifier");
        out.println("                                ; u_id = UDT for the identifier");
    }
}
