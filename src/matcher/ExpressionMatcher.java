package matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;

import obj.Attribute;
import obj.SpecAttribute;

import orbital.logic.functor.Predicates;
import orbital.logic.imp.Formula;
import orbital.logic.imp.Logic;
import orbital.logic.sign.ParseException;
import orbital.moon.logic.ClassicalLogic;

public class ExpressionMatcher {
	Logic logic;
	
	
	public ExpressionMatcher(){
		logic = new ClassicalLogic();
	}
	
	public Map Prove(Formula[] axioms, String l, String r) throws IllegalArgumentException, ParseException{
		Map res = new HashMap();
		System.out.println(l+" "+r);
		String[] CFs = DNFSplit((Formula)logic.createExpression(l));
		Formula right = (Formula)logic.createExpression(r);
		//Formula fal = (Formula)logic.createExpression("false");
		for(String CF : CFs){
			Formula lCNF = (Formula)logic.createExpression(CF);
			//System.out.println(lCNF+"->"+right);
			//System.out.println(axioms[0]+" "+axioms[1]+" "+axioms[2]+" "+axioms[3]);
			if(logic.inference().infer(axioms, lCNF.impl(right))){
				res.put(lCNF.toString(), 0);
			}
			else{
				String[] DFs = CNFSplit(right);
				//System.out.println(DFs[0]+" "+DFs[1]);
				boolean partial = false;
				for(String DF : DFs){
					//System.out.println(DFs[j]);
					Formula rDNF = (Formula)logic.createExpression(DF);
					if(logic.inference().infer(axioms, lCNF.impl(rDNF))){
						res.put(lCNF.toString(), 1);
						partial = true;
						break;
					}
				}
				if(partial == false)
					res.put(lCNF.toString(), 2);
			}
		}	
		return res;
	}
	public Formula[] AxiomsGen(List<Attribute> output, List<Attribute> input) throws IllegalArgumentException, ParseException{
		ArrayList<Formula> statements = new ArrayList<Formula>();
		for(Attribute iAttr : input){
			if (iAttr.getClass().equals(SpecAttribute.class)){
				Attribute attr1 = null;
				Attribute attr2 = null;
				for(Attribute oAttr : output){
					if (oAttr.getKey().equals(iAttr.getKey()))
						attr1 = oAttr;
					if (oAttr.getKey().equals(iAttr.getValue()))
						attr2 = oAttr;
					if (null != attr1 && null != attr2) {
						String res = SpecValueMatch(attr1, attr2, (SpecAttribute)iAttr);
						if (null != res){
							Formula axiom  = (Formula)logic.createExpression(res);
							statements.add(axiom);
						}
						break;
					}
				}
			}
			else if(iAttr.getClass().equals(Attribute.class)){
				for(Attribute oAttr : output){
					if (oAttr.getKey().equals(iAttr.getKey())){
						String sameKeyState = ValueMatcher.ValueMatch(oAttr, iAttr);
						if (null != sameKeyState){
							Formula fState  = (Formula)logic.createExpression(sameKeyState);
							statements.add(fState);
						}
					}
					
				}
			}
		}
		return statements.toArray(new Formula[statements.size()]);
	}
	private String SpecValueMatch(Attribute left, Attribute right, SpecAttribute sa){
		String implies = left.getId()+"->("+right.getId()+")";
		String notImplies = right.getId()+"->~("+left.getId()+")";
		String lUpper = UpperBoundary(left.getOperator(), left.getValue());
		String lLower = LowerBoundary(left.getOperator(), left.getValue());
		String rUpper = UpperBoundary(right.getOperator(), right.getValue());
		String rLower = LowerBoundary(right.getOperator(), right.getValue());
		if (sa.getOperator().equals("=")){
			return ValueMatcher.ValueMatch(left, right).equals(implies) ? sa.getId() : null;
		}
		else if (sa.getOperator().equals(">")){
			if (null != lUpper){
				if (null != rLower){
					if (lUpper.compareTo(rLower) < 1)
						return null;
				}
			}
			return sa.getId();
		}
		else if (sa.getOperator().equals(">=")){
			if (null != lUpper){
				if (null != rLower){
					if (lUpper.compareTo(rLower) < 1)
						return null;
				}
			}
			if (ValueMatcher.ValueMatch(left, right).equals(notImplies))
			return null;
			
			return sa.getId();
		}
		else if (sa.getOperator().equals("<")){
			if (null != lLower){
				if (null != rUpper){
					if (lLower.compareTo(rUpper) > -1)
						return null;
				}
			}
			return sa.getId();
		}
		else if (sa.getOperator().equals("<=")){
			if (null != lLower){
				if (null != rUpper){
					if (lLower.compareTo(rUpper) > -1)
						return null;
				}
			}
			if (ValueMatcher.ValueMatch(left, right).equals(notImplies))
				return null;
				
				return sa.getId();
		}
		return null;
	}
	private String UpperBoundary(String oper, String value){
		if (oper.equals(">") || oper.equals(">=")){
			return null;
		}
		else if (oper.equals("=") || oper.equals("<") || oper.endsWith("<=")){
			return value;
		}
		return null;
	}
	private String LowerBoundary(String oper, String value){
		if (oper.equals("<") || oper.equals("<=")){
			return null;
		}
		else if (oper.equals("=") || oper.equals(">") || oper.endsWith(">=")){
			return value;
		}
		return null;
	}
	private String[] DNFSplit(Formula f){
		Formula formula = ClassicalLogic.Utilities.disjunctiveForm(f, true);
		String DNF = formula.toString();
		String[] CFs = DNF.split("\\s\\|\\s");
		return CFs;
	}
	public String DNFTransfer(String formula) throws IllegalArgumentException, ParseException{
		Formula dnf = ClassicalLogic.Utilities.disjunctiveForm((Formula)logic.createExpression(formula), true);
		return dnf.toString();
	}
	
	private String[] CNFSplit(Formula f){
		Formula fomula = ClassicalLogic.Utilities.conjunctiveForm(f, true);
		String CNF = fomula.toString();
		String[] DFs = CNF.split("\\s\\&\\s");
		return DFs;
	}
	
	
	
}
