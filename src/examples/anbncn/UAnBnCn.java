// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package examples.anbncn;

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

public class UAnBnCn extends Grammar{

    // public API
    /** Called to get a singleton instance of this class.
     * @return a singleton instance of this class, cast as the base class, Grammar. */
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new UAnBnCn(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    /** The number of rules in the grammar */
    public static int ruleCount = 1;
    /** This enum provides easy to remember enum constants for locating the rule identifiers and names.
     * The enum constants have the same spelling as the rule names rendered in all caps with underscores replacing hyphens. */
    public enum RuleNames{
        /** id = <code>0</code>, name = <code>"AnBnCn"</code> */
        ANBNCN("AnBnCn", 0, 0, 1);
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
        /** id = <code>0</code>, name = <code>"u_anbncn"</code> */
        U_ANBNCN(0, "u_anbncn", false);
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
    private static UAnBnCn factoryInstance = null;
    private UAnBnCn(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[1];
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
    	Opcode[] op = new Opcode[1];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        op[0] = getOpcodeUdt(0); // u_anbncn
    }

    /** Displays the original SABNF grammar on the output device.
     * @param out the output device to display on.*/
    public static void display(PrintStream out){
        out.println(";");
        out.println("; examples.anbncn.UAnBnCn");
        out.println(";");
        out.println("AnBnCn    = u_anbncn");
    }
}
