/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.TreeMap;

import apg.GeneratorSyntax.SyntaxOpcode;
import apg.GeneratorSyntax.SyntaxRule;

class GeneratorAttributes {
	Vector<String> errors;
	Vector<String> warnings;
	TreeMap<String, Integer> ruleMap;
	Vector<SyntaxRule> rules;
	Vector<SyntaxOpcode> opcodes;
	Attrs[][] ruleAttrs;
	int startRuleId;
	boolean traceAttrs = false;

	GeneratorAttributes(Vector<String> errors, Vector<String> warnings,
			TreeMap<String, Integer> ruleMap,
			Vector<SyntaxRule> rules, Vector<SyntaxOpcode> opcodes){
		this.errors = errors;
		this.warnings = warnings;
		this.ruleMap = ruleMap;
		this.rules = rules;
		this.opcodes = opcodes;
		int count = rules.size();
		ruleAttrs = new Attrs[count][count];
		for(int i = 0; i < count; i++){
			for(int j = 0; j < count; j++){
				ruleAttrs[i][j] = new Attrs();
			}
		}
	}

	@Override public String toString(){
		StringBuffer buf = new StringBuffer();
		Comparator<SyntaxRule> compare = new NameComparator();
		SyntaxRule[] ruleList = new SyntaxRule[rules.size()];
		int i = 0;
		for(SyntaxRule rule:rules){
			ruleList[i++] = rule;
		}
		List<SyntaxRule> list = Arrays.asList(ruleList);
		Collections.sort(list, compare);
		buf.append(ruleAttrs[0][0].header());
		for(SyntaxRule rule:ruleList){
			// display unparsable rules first
			Attrs a = ruleAttrs[rule.id][rule.id];
			if(a.left || !a.finite || a.cyclic){
				buf.append(ruleAttrs[rule.id][rule.id].toString());
				buf.append(rules.elementAt(rule.id).name);
				buf.append("\n");
			}
		}
		for(SyntaxRule rule:ruleList){
			// display only parsable rules
			Attrs a = ruleAttrs[rule.id][rule.id];
			if(!(a.left || !a.finite || a.cyclic)){
				buf.append(ruleAttrs[rule.id][rule.id].toString());
				buf.append(rules.elementAt(rule.id).name);
				buf.append("\n");
			}
		}
//		buf.append(ruleAttrs[0][0].legend());
		return buf.toString();
	}
	
	// package APG
	void attributes(){
		Attrs attrs = new Attrs();
		for(int i = 0; i < rules.size(); i++){
			SyntaxRule rule = rules.elementAt(i);
			startRuleId = rule.id;
			attrs.clear();
			computeRuleAttrs(rule, ruleAttrs[i][i], attrs);
		}

		// rules with attribute errors & rules not referenced
		Attrs a;
		GeneratorAttributes.Attrs[] as = ruleAttrs[0];
		boolean isReferenced, hasErrors;
		hasErrors = false;
		SyntaxRule rule;
		for(int i = 0; i < GeneratorSyntax.rules.size(); i++){
			rule = GeneratorSyntax.rules.elementAt(i);
			isReferenced = true;
			a = ruleAttrs[i][i];
			if(i > 0){
				if(as[i].refs == 0){
					warnings.add("line["+rule.lineno+"]: "+rule.name+": rule not referenced by start rule");
					isReferenced = false;
				}
			}
			if(a.left || !a.finite || a.cyclic){
				if(isReferenced){hasErrors = true;}
				String add = "line["+rule.lineno+"]: "+rule.name+": rule has attribute errors: ";
				boolean comma = false;
				if(a.left){
					add += "left recursive";
					comma = true;
				}
				if(!a.finite){
					if(comma){add += ", ";}
					add += "infinite";
					comma = true;
				}
				if(a.cyclic){
					if(comma){add += ", ";}
					add += "cyclic";
				}
				errors.add(add);
			}
		}
		if(hasErrors){
			rule = GeneratorSyntax.rules.elementAt(0);
			errors.add("line["+rule.lineno+"]: "+rule.name+": : start rule has or references a rule with attribute errors");
		}
	}

	// private members
	private class NameComparator implements Comparator<SyntaxRule>{
		@Override public int compare(SyntaxRule lhs, SyntaxRule rhs){
			String lhsLower = lhs.name.toLowerCase();
			String rhsLower = rhs.name.toLowerCase();
			if(lhsLower.compareTo(rhsLower) < 0){return -1;}
			if(lhsLower.compareTo(rhsLower) > 0){return 1;}
			else{return 0;}
		}
	}

	private class Attrs{
		private static final char YES   = 'o';
		private static final char NO    = '-';
		private static final char FATAL = 'x';
		int refs;
		boolean open, left, right, nested, empty, finite, cyclic;
		public Attrs(){clear();}
		void clear(){
			this.refs = 0;
			this.open = false;
			this.left = false;
			this.nested = false;
			this.right = false;
			this.empty = false;
			this.finite = false;
			this.cyclic = false;
		}
		private void copy(Attrs attrs){
			attrs.left = this.left;
			attrs.nested = this.nested;
			attrs.right = this.right;
			attrs.empty = this.empty;
			attrs.finite = this.finite;
			attrs.cyclic = this.cyclic;
		}
		@Override public Attrs clone(){
			Attrs attrs = new Attrs();
			copy(attrs);
			attrs.refs = this.refs;
			attrs.open = this.open;
			return attrs;
		}
		private String header(){
			return "rule attributes: (o = true, - = false, x = unparsable)\n" + String.format("%4s %8s %8s %8s %8s %8s  %s\n",
					"LEFT", "NESTED", "RIGHT", "EMPTY", "INFINITE", "CYCLIC", "Rule Name");
		}
		@Override public String toString(){
			char l = (left) ? FATAL : NO;
			char n = (nested) ? YES : NO;
			char r = (right) ? YES : NO;
			char e = (empty) ? YES : NO;
			char i = (!finite) ? FATAL : NO;
			char c = (cyclic) ? FATAL : NO;
			return String.format("%4s %8s %8s %8s %8s %8s  ", l, n, r, e, i, c);
		}
	}
	private void computeAlt(SyntaxOpcode opcode, Attrs attrs){
		Attrs[] children = new Attrs[opcode.childOpcodes.size()];
		int index = 0;
		for(SyntaxOpcode op:opcode.childOpcodes){
			children[index] = new Attrs();
			computeOpcodeAttrs(op, children[index]);
			index++;
		}
		// if any child attr is true, ALT attr is true
		for(Attrs a:children){
			if(a.left){attrs.left = true;}
			if(a.nested){attrs.nested = true;}
			if(a.right){attrs.right = true;}
			if(a.empty){attrs.empty = true;}
			if(a.finite){attrs.finite = true;}
			if(a.cyclic){attrs.cyclic = true;}
		}
	}

	private boolean isCatEmpty(Attrs[] children){
		boolean ret = true;
		for(Attrs a:children){
            if(!a.empty){ret = false; break;}	// if any child is NOT empty, CAT is not empty
		}
		return ret;
	}
	private boolean isCatCyclic(Attrs[] children){
		boolean ret = true;
		for(Attrs a:children){
            if(!a.cyclic){ret = false; break;}	// if any child is NOT cyclic, CAT is not cyclic (ie. S = SS is cyclic)
		}
		return ret;
	}
	private boolean isCatFinite(Attrs[] children){
		boolean ret = true;
		for(Attrs a:children){
            if(!a.finite){ret = false; break;}	// if any child is NOT finite, CAT is not finite
		}
		return ret;
	}
	private boolean isCatLeft(Attrs[] children){
		boolean ret = false;
		for(Attrs a:children){
            if(a.left){ret = true; break;}		// if left-most, non-empty child is left, CAT is left
            if(!a.empty){ret = false; break;}	// else if this child is not empty, CAT is not left
            									// else this child is empty - keep looking
		}
		return ret;
	}
	private boolean isCatRight(Attrs[] children){
		boolean ret = false;
		for(int i = children.length - 1; i >= 0; i -= 1){
            if(children[i].right){ret = true; break;}	// if right-most, non-empty child is right, CAT is right
            if(!children[i].empty){ret = false; break;}	// else if this child is not empty, CAT is not right
            											// else this child is empty - keep looking
		}
		return ret;
	}
	private boolean isNonEmptyOnly(Attrs a){
		boolean ret = false;
		if(a.empty && !(a.left || a.right || a.nested || a.cyclic || a.finite)){ret = true;}
		return ret;
	}
	private boolean isRecursive(Attrs a){
		boolean ret = false;
		if(a.left || a.right || a.nested || a.cyclic){ret = true;}
		return ret;
	}
	private boolean isCatNested(Attrs[] children){
		boolean nested = false;
        boolean found, foundRecursive;
		while(true){
			// 1.) if any child is nested, CAT is nested
			for(int i = 0; i < children.length; i += 1){
                if(children[i].nested){nested = true; break;}
            }
            if(nested == true){break;}

        	// 2.) the left-most, non-empty-only is right recursive and at least one non-empty only term follows it
            found = false;
            foundRecursive = false;
            for(int i= 0; i < children.length; i+=1){
            	Attrs a = children[i];
            	if(foundRecursive){
            		if(!isNonEmptyOnly(a)){nested = true; break;}
            	} else {
        			if(!isNonEmptyOnly(a)){
        				if(a.right && !a.left){foundRecursive = true;}
        				else{break;}
        			}
            	}
            }
            if(nested == true){break;}

        	// 3.) the right-most, non-empty-only is left-recursive and at least one non-empty only term follows it
            found = false;
            foundRecursive = false;
            for(int i = children.length - 1; i >= 0; i -= 1){
            	Attrs a = children[i];
            	if(foundRecursive){
            		if(!isNonEmptyOnly(a)){nested = true; break;}
            	} else {
        			if(!isNonEmptyOnly(a)){
        				if(a.left && ! a.right){foundRecursive = true;}
        				else{break;}
        			}
            	}
            }
            if(nested == true){break;}

        	// 4.) there is at least one recursive term between the left-most and right-most non-empty-only terms
            found = false;
            foundRecursive = false;
            for(int i= 0; i < children.length; i+=1){
            	Attrs a = children[i];
            	if(foundRecursive){
            		if(!isNonEmptyOnly(a)){nested = true; break;}
            	} else {
            		if(found){
            			if(isRecursive(a)){foundRecursive = true;}
            		} else if(!isNonEmptyOnly(a)){found = true;}
            	}
            }
            if(nested == true){break;}
			break;
		}
		return nested;
	}
	private void computeCat(SyntaxOpcode opcode, Attrs attrs){
		Attrs[] children = new Attrs[opcode.childOpcodes.size()];
		int index = 0;
		for(SyntaxOpcode op:opcode.childOpcodes){
			children[index] = new Attrs();
			computeOpcodeAttrs(op, children[index]);
			index++;
		}
	    attrs.left = isCatLeft(children);
	    attrs.right = isCatRight(children);
	    attrs.nested = isCatNested(children);
	    attrs.empty = isCatEmpty(children);
	    attrs.finite = isCatFinite(children);
	    attrs.cyclic = isCatCyclic(children);
	}

	private void computeRep(SyntaxOpcode opcode, Attrs attrs){
		SyntaxOpcode op = opcode.childOpcodes.elementAt(0);
		computeOpcodeAttrs(op, attrs);	// compute attrs: same as child attribute
		if(opcode.min == 0){			// except possibly empty & finite
			attrs.empty = true;
			attrs.finite = true;
		}
	}

	private void computePrd(SyntaxOpcode opcode, Attrs attrs){
		SyntaxOpcode op = opcode.childOpcodes.elementAt(0);
		computeOpcodeAttrs(op, attrs);	// compute attrs: same as child attribute
	}

	private void computeOpcodeAttrs(SyntaxOpcode op, Attrs attrs){
		switch(op.type){
		case ALT:
			computeAlt(op, attrs);
			break;
		case CAT:
			computeCat(op, attrs);
			break;
		case REP:
			computeRep(op, attrs);
			break;
		case RNM:
			SyntaxRule rule = rules.elementAt(op.id);
			computeRuleAttrs(rule, ruleAttrs[startRuleId][op.id], attrs);
			break;
			
			// effectively terminals
		case AND:
		case NOT:
			attrs.empty = true;
			attrs.finite = true;
			computePrd(op, attrs);
			break;
		case TLS:
			if(op.string.length == 0){attrs.empty = true;}
			attrs.finite = true;
			break;
		case TBS:
		case TRG:
			attrs.finite = true;
			break;
		case UDT:
			String prefix = op.name.toLowerCase().substring(0, 2);
			if(prefix.contentEquals("e_")){attrs.empty = true;}
			attrs.finite = true;
			break;
		}
	}
	
	private void computeRuleAttrs(SyntaxRule rule, Attrs ruleAttrs, Attrs attrs){
		SyntaxOpcode firstOp = opcodes.elementAt(rule.opcodeOffset);
		if(ruleAttrs.refs > 0){
			// return the known attributes
			ruleAttrs.copy(attrs);
			ruleAttrs.refs++;
		} else if(!ruleAttrs.open){
			// open the rule and traverse it
			ruleAttrs.open = true;
			computeOpcodeAttrs(firstOp, attrs);
			attrs.copy(ruleAttrs);
			ruleAttrs.open = false;
			ruleAttrs.refs++;
		} else if(rule.id == startRuleId){
			// handle start rule terminal leaf
			attrs.left = true;
			attrs.right = true;
			attrs.cyclic = true;
		} else{
			// handle non-start rule terminal leaf
			attrs.finite = true;
		}
		if(traceAttrs){
			System.out.print("trace: "+rule.toString());
			System.out.println("startRuleId: "+startRuleId+"   rule id: "+rule.id);
			System.out.println(attrs.toString());
		}
	}
}
