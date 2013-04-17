package matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;

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
		for(int i=0; i<CFs.length; ++i){
			Formula lCNF = (Formula)logic.createExpression(CFs[i]);
			//System.out.println(lCNF+"->"+right);
			//System.out.println(axioms[0]+" "+axioms[1]+" "+axioms[2]+" "+axioms[3]);
			if(logic.inference().infer(axioms, lCNF.impl(right))){
				res.put(lCNF, 0);
			}
			else{
				String[] DFs = CNFSplit(right);
				//System.out.println(DFs[0]+" "+DFs[1]);
				boolean partial = false;
				for(int j=0; j<DFs.length; ++j){
					//System.out.println(DFs[j]);
					Formula rDNF = (Formula)logic.createExpression(DFs[j]);
					if(logic.inference().infer(axioms, lCNF.impl(rDNF))){
						res.put(lCNF, 1);
						partial = true;
						break;
					}
				}
				if(partial == false)
					res.put(lCNF, 2);
			}
		}	
		//System.out.println(fImpl);
		//return logic.inference().infer(statements.toArray(new Formula[statements.size()]), fImpl);
		return res;
	}
	public Formula[] AxiomsGen(List<Attribute> output, List<Attribute> input) throws IllegalArgumentException, ParseException{
		ArrayList<Formula> statements = new ArrayList<Formula>();
		Iterator o = output.iterator();
		while(o.hasNext()){
			//String diffKeyState = "";
			Attribute oAttr = (Attribute) o.next();
			Iterator i = input.iterator();
			while(i.hasNext()){
				Attribute iAttr = (Attribute)i.next();
				if (oAttr.key.equals(iAttr.key)){
					//System.out.println("OK till here");
					String sameKeyState = ValueMatcher.ValueMatch(oAttr, iAttr);
					//System.out.println("OK till here4");
					if (null != sameKeyState){
						Formula fState  = (Formula)logic.createExpression(sameKeyState);
						statements.add(fState);
					}
				}
			}
			//diffKeyState = "" + diffKeyState.substring(1) + "->!(" + oAttr.id + ")";
		}
		return statements.toArray(new Formula[statements.size()]);
	}
	private String[] DNFSplit(Formula f){
		//Formula left=null;
		//Formula right=null;
		//Formula comp=null;
	
		Formula formula = ClassicalLogic.Utilities.disjunctiveForm(f, true);
		String DNF = formula.toString();
		String[] CFs = DNF.split("\\s\\|\\s");
			//right = (Formula)logic.createExpression(r);
			//comp = left.impl(right);
		//System.out.println(lCNFs[0]+" "+lCNFs[1]);
		return CFs;
	}
	
	private String[] CNFSplit(Formula f){
	
		Formula fomula = ClassicalLogic.Utilities.conjunctiveForm(f, true);
		String CNF = fomula.toString();
		String[] DFs = CNF.split("\\s\\&\\s");
			//right = (Formula)logic.createExpression(r);
			//comp = left.impl(right);
		//System.out.println(lCNFs[0]+" "+lCNFs[1]);
		return DFs;
	}
	
	public String ExpressionFormatter(List kvs, String expr){
		Iterator i = kvs.iterator();
		while (i.hasNext()){
			Attribute attr = (Attribute)i.next();
			String kv;
			if (attr.operator.equals("ALL"))
				kv= attr.key;
			else
				kv=attr.key+"'"+attr.operator+"'"+attr.value;
			//System.out.println("kv "+ Pattern.quote(kv));
			String atom = attr.id;
			expr = expr.replaceAll(Pattern.quote(kv), atom); 
		}
		return expr;
	}
	
	public List<Attribute> AtomExtractor (String expr, String flag){
		List<Attribute> l = new ArrayList();
		expr = expr.replaceAll("[()]", "");
		String exprList[] = expr.split("[&|]");
		for(int index = 0; index<exprList.length; ++index){
			String kvp[] = exprList[index].split("'");
			if (kvp.length == 3){
				l.add(new Attribute(flag+l.size(),kvp[0],kvp[1],kvp[2]));
			}
			else if (kvp.length == 2){
				l.add(new Attribute(flag+l.size(),kvp[0],kvp[1],""));
			}
			else if (kvp.length == 1){
				l.add(new Attribute(flag+l.size(),kvp[0],"ALL",""));
			}
			
		}
		System.out.println(l.get(0).id+l.get(1).id+l.get(2).id);
		return l;
	}
}
