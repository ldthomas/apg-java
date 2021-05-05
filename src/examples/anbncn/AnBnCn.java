// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.anbncn;

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

public class AnBnCn extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new AnBnCn(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 8;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>5</code>, name = <code>"A"</code> */
        A("A", 5, 20, 1),
        /** id = <code>3</code>, name = <code>"AnBn"</code> */
        ANBN("AnBn", 3, 10, 5),
        /** id = <code>0</code>, name = <code>"AnBnCn"</code> */
        ANBNCN("AnBnCn", 0, 0, 5),
        /** id = <code>6</code>, name = <code>"B"</code> */
        B("B", 6, 21, 1),
        /** id = <code>4</code>, name = <code>"BnCn"</code> */
        BNCN("BnCn", 4, 15, 5),
        /** id = <code>7</code>, name = <code>"C"</code> */
        C("C", 7, 22, 1),
        /** id = <code>2</code>, name = <code>"ConsumeAs"</code> */
        CONSUMEAS("ConsumeAs", 2, 8, 2),
        /** id = <code>1</code>, name = <code>"Prefix"</code> */
        PREFIX("Prefix", 1, 5, 3);
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
    private static AnBnCn factoryInstance = null;
    private AnBnCn(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[8];
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
    	Opcode[] op = new Opcode[23];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,3,4}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeAnd(2);
        op[2] = getOpcodeRnm(1, 5); // Prefix
        op[3] = getOpcodeRnm(2, 8); // ConsumeAs
        op[4] = getOpcodeRnm(4, 15); // BnCn
        {int[] a = {6,7}; op[5] = getOpcodeCat(a);}
        op[6] = getOpcodeRnm(3, 10); // AnBn
        op[7] = getOpcodeRnm(7, 22); // C
        op[8] = getOpcodeRep((char)0, Character.MAX_VALUE, 9);
        op[9] = getOpcodeRnm(5, 20); // A
        {int[] a = {11,12,14}; op[10] = getOpcodeCat(a);}
        op[11] = getOpcodeRnm(5, 20); // A
        op[12] = getOpcodeRep((char)0, (char)1, 13);
        op[13] = getOpcodeRnm(3, 10); // AnBn
        op[14] = getOpcodeRnm(6, 21); // B
        {int[] a = {16,17,19}; op[15] = getOpcodeCat(a);}
        op[16] = getOpcodeRnm(6, 21); // B
        op[17] = getOpcodeRep((char)0, (char)1, 18);
        op[18] = getOpcodeRnm(4, 15); // BnCn
        op[19] = getOpcodeRnm(7, 22); // C
        {char[] a = {97}; op[20] = getOpcodeTbs(a);}
        {char[] a = {98}; op[21] = getOpcodeTbs(a);}
        {char[] a = {99}; op[22] = getOpcodeTbs(a);}
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.anbncn.AnBnCn");
        out.println(";");
        out.println("AnBnCn    = &Prefix ConsumeAs BnCn");
        out.println("Prefix    = AnBn C");
        out.println("ConsumeAs = *A");
        out.println("AnBn      = A [AnBn] B");
        out.println("BnCn      = B [BnCn] C");
        out.println("A         = %d97");
        out.println("B         = %d98");
        out.println("C         = %d99");
    }
}
