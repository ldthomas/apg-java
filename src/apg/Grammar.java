package apg;

import apg.Opcode.Type;

/**
 * The Grammar class is the interface between the Parser Generator and the Parser.
 * The Parser Generator will generate extensions of this base class.
 * The generated Grammar class can then be used to initialize the Parser.
 * @see GeneratorGrammar
 */
public abstract class Grammar {
	protected static Rule getRule(int id, String name, int opcodeOffset, int opcodeCount){
		 return new Rule(id, name, opcodeOffset, opcodeCount);
	}
	protected static Udt getUdt(int id, String name, boolean mayBeEmpty){
		 return new Udt(id, name, mayBeEmpty);
	}
	protected static OpcodeAlt getOpcodeAlt(int[] children){
		 return new OpcodeAlt(children);
	}
	protected static OpcodeCat getOpcodeCat(int[] children){
		 return new OpcodeCat(children);
	}
	protected static OpcodeAnd getOpcodeAnd(int child){
		 return new OpcodeAnd(child);
	}
	protected static OpcodeNot getOpcodeNot(int child){
		 return new OpcodeNot(child);
	}
	protected static OpcodeRep getOpcodeRep(char min, char max, int child){
		 return new OpcodeRep(min, max, child);
	}
	protected static OpcodeRnm getOpcodeRnm(int id, int child){
		 return new OpcodeRnm(id, child);
	}
	protected static OpcodeUdt getOpcodeUdt(int id){
		 return new OpcodeUdt(id);
	}
	protected static OpcodeTls getOpcodeTls(char[] string){
		 return new OpcodeTls(string);
	}
	protected static OpcodeTbs getOpcodeTbs(char[] string){
		 return new OpcodeTbs(string);
	}
	protected static OpcodeTrg getOpcodeTrg(char min, char max){
		 return new OpcodeTrg(min, max);
	}
	protected static class Rule{
		final int ID;
		final String NAME;
		final int CHILD;
		final int COUNT;
		
		Rule(int id, String name, int child, int count){
			ID = id;
			NAME = name;
			CHILD = child;
			COUNT = count;
		}
		Rule(Rule rule){
			this.ID = rule.ID;
			this.NAME = rule.NAME;
			this.CHILD = rule.CHILD;
			this.COUNT = rule.COUNT;
		}
	}

	protected static class Udt{
		final int ID;
		final String NAME;
		final boolean MAYBEEMPTY;
		
		Udt(int id, String name, boolean empty){
			this.ID = id;
			this.NAME = name;
			this.MAYBEEMPTY = empty;
		}
		Udt(Udt rule){
			this.ID = rule.ID;
			this.NAME = rule.NAME;
			this.MAYBEEMPTY = rule.MAYBEEMPTY;
		}
	}

	protected int getOpcodeCount(){return opcodes.length;}
	protected int getRuleCount(){return rules.length;}
	protected int getUdtCount(){return udts.length;}
	protected Opcode getOpcode(int i){
		if(i >= opcodes.length || i < 0){return null;}
		return opcodes[i];
	}
	protected Rule getRule(int i){
		if(i >= rules.length || i < 0){return null;}
		return new Rule(rules[i]);
	}
	protected Udt getUdt(int i){
		if(i >= udts.length || i < 0){return null;}
		return new Udt(udts[i]);
	}
	
	
	protected static abstract class Opcode {
		final Type type;
		Opcode(){
			this.type = Type.ALT;
		}
		Opcode(Type type){
			this.type = type;
		}
	}
	
	protected static class OpcodeAlt extends Opcode { 
		final int[] children;
		protected OpcodeAlt(int[] children){
			super(Type.ALT);
			this.children = children;
		}
		protected OpcodeAlt(OpcodeAlt op){
			super(Type.ALT);
			this.children = op.children;
		}
	}
	protected static class OpcodeCat extends Opcode { 
		final int[] children;
		protected OpcodeCat(int[] children){
			super(Type.CAT);
			this.children = children;
		}
		protected OpcodeCat(OpcodeCat op){
			super(Type.CAT);
			this.children = op.children;
		}
	}
	protected static class OpcodeRep extends Opcode { 
		final int child;
		final char min;
		final char max;
		protected OpcodeRep(char min, char max, int child){
			super(Type.REP);
			this.child = child;
			this.min = min;
			this.max = max;
		}
		protected OpcodeRep(OpcodeRep op){
			super(Type.REP);
			this.child = op.child;
			this.min = op.min;
			this.max = op.max;
		}
	}
	protected static class OpcodeAnd extends Opcode { 
		final int child;
		protected OpcodeAnd(int child){
			super(Type.AND);
			this.child = child;
		}
		protected OpcodeAnd(OpcodeAnd op){
			super(Type.AND);
			this.child = op.child;
		}
	}
	protected static class OpcodeNot extends Opcode { 
		final int child;
		protected OpcodeNot(int child){
			super(Type.NOT);
			this.child = child;
		}
		protected OpcodeNot(OpcodeNot op){
			super(Type.NOT);
			this.child = op.child;
		}
	}
	protected static class OpcodeRnm extends Opcode { 
		final int child;
		final int id;
		protected OpcodeRnm(int id, int child){
			super(Type.RNM);
			this.child = child;
			this.id = id;
		}
		protected OpcodeRnm(OpcodeRnm op){
			super(Type.RNM);
			this.child = op.child;
			this.id = op.id;
		}
	}
	protected static class OpcodeUdt extends Opcode { 
		final int id;
		protected OpcodeUdt(int id){
			super(Type.UDT);
			this.id = id;
		}
		protected OpcodeUdt(OpcodeUdt op){
			super(Type.UDT);
			this.id = op.id;
		}
	}
	protected static class OpcodeTrg extends Opcode { 
		final char min;
		final char max;
		protected OpcodeTrg(char min, char max){
			super(Type.TRG);
			this.min = min;
			this.max = max;
		}
		protected OpcodeTrg(OpcodeTrg op){
			super(Type.TRG);
			this.min = op.min;
			this.max = op.max;
		}
	}
	protected static class OpcodeTbs extends Opcode { 
		final char[] string;
		protected OpcodeTbs(char[] string){
			super(Type.TBS);
			this.string = string.clone();
		}
		protected OpcodeTbs(OpcodeTbs op){
			super(Type.TBS);
			this.string = op.string.clone();
		}
	}
	protected static class OpcodeTls extends Opcode { 
		final char[] string;
		protected OpcodeTls(char[] string){
			super(Type.TLS);
			this.string = toLower(string);
		}
		protected OpcodeTls(OpcodeTls op){
			super(Type.TLS);
			this.string = toLower(op.string);
		}
		private char[] toLower(char[] string){
			int n = string.length;
			char[] temp = new char[n];
			for(int i = 0; i < n; i++) {
				if(string[i] >= (char)65 && string[i] <= (char)90){
					temp[i] = (char)(string[i] + (char)32);
				} else {
					temp[i] = string[i];
				}
			}
			return temp;
		}
	}

	private Rule[] rules;
	private Udt[] udts;
	private Opcode[] opcodes;
	protected Grammar(Rule[] rules, Udt[] udts, Opcode[] opcodes){
		this.rules = rules;
		this.udts = udts;
		this.opcodes = opcodes;
	}	
}
