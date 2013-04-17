package matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import orbital.logic.imp.Formula;
import orbital.logic.sign.ParseException;

import java.util.regex.*;

public class TriggerMatcher {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, ParseException {
		// TODO Auto-generated method stub
//		List<Attribute> input = new ArrayList();
//		List<Attribute> output = new ArrayList();
//		
//		String iExpr = "(key1~c*ar|key4~[ac]db)&(key3>ab)";
//		String oExpr = "(key1=kind|key4>ab)&(key3~c*ar)";
//		//String impl = "(oa|ob)->(ia&ib)";
//		//String implNC = "(!a&!b)&(a&c)->false";
//		//String implN = "ia&ic->!oa";
//		//String implC = "(ob)&(ia&ic)->false";//T,_,_ definite; T,F,_ might; F,_,T unrelated; F,_,F partial
//		input.add(new Attribute("ikv"+input.size(),"key1", "~","c*ar"));
//		//input.add(new Attribute("a", "~","[kf]ind", 'i'));
//		input.add(new Attribute("ikv"+input.size(),"key2", "=","2"));
//		input.add(new Attribute("ikv"+input.size(),"key3", ">","ab"));
//		input.add(new Attribute("ikv"+input.size(),"key4", "~","[ac]db"));
//		
//		output.add(new Attribute("okv"+output.size(),"key1", "=","kind"));
//		output.add(new Attribute("okv"+output.size(),"key2", "~","c*ar"));
//		output.add(new Attribute("okv"+output.size(),"key3", "~","c*ar"));
//		output.add(new Attribute("okv"+output.size(),"key4", ">","ab"));
		ExpressionMatcher em = new ExpressionMatcher();
		String InputExpr = "((key1'='1|key2'='12)|key3'~'[ab]cd)";
		String OutputExpr = "((key1'~'a*b&key2)|key3'>='a)";
		List<Attribute> input = em.AtomExtractor(InputExpr, "ikv");
		List<Attribute> output = em.AtomExtractor(OutputExpr, "okv");
		Collections.sort(input, new AttrComparator());
		Collections.sort(output, new AttrComparator());
		
		String newiExpr = em.ExpressionFormatter(input, InputExpr);
		String newoExpr = em.ExpressionFormatter(output, OutputExpr);
		//System.out.println(newiExpr);
		
		Formula[] axioms = em.AxiomsGen(output, input);
		System.out.println("implres "+em.Prove(axioms, newoExpr, newiExpr));
		//axiomsSystem.out.println("implNC "+em.prove(output, input, implNC));
		//System.out.println("implN "+em.prove(output, input, implN));
		//System.out.println("implC "+em.prove(output, input, implC));
		//System.out.println(em.prove(output, input, impl) ? "It is a Partial Trigger" : "It is a Complete Trigger");
		
		//System.out.println(input.get(0).id+input.get(1).key+input.get(1).operator+input.get(1).value+input.get(2).id);
		
	}
	
}
