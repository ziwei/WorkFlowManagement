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
		List<Attribute> input = new ArrayList();
		List<Attribute> output = new ArrayList();
		ExpressionMatcher em = new ExpressionMatcher();
		//String impl = "(oa|ob)&oc&od->!((ia|ib)&(ic|id))";
		//String impl = "(oa|ob)->(ia&ib)";
		//String implNC = "(!a&!b)&(a&c)->false";
		//String implN = "ia&ic->!oa";
		//String implC = "(ob)&(ia&ic)->false";//T,_,_ definite; T,F,_ might; F,_,T unrelated; F,_,F partial
		input.add(new Attribute("ikv"+input.size(),"a", "=","1"));
		//input.add(new Attribute("a", "~","[kf]ind", 'i'));
		input.add(new Attribute("ikv"+input.size(),"b", "~","[ce]ar"));
		input.add(new Attribute("ikv"+input.size(),"c", ">","cccccar"));
		input.add(new Attribute("ikv"+input.size(),"d", ">","a"));
		
		output.add(new Attribute("okv"+output.size(),"a", "=","kind"));
		output.add(new Attribute("okv"+output.size(),"b", "~","c*ar"));
		output.add(new Attribute("okv"+output.size(),"c", "~","c*ar"));
		output.add(new Attribute("okv"+output.size(),"d", "<","ab"));
		
		//System.out.println(impl.substring(0, 1));
		Formula[] axioms = em.AxiomsGen(output, input);
		//System.out.println("implres "+em.Prove(axioms, "(okv0|okv1)&(okv2&okv3)", "okv0&okv1&okv2"));
		//axiomsSystem.out.println("implNC "+em.prove(output, input, implNC));
		//System.out.println("implN "+em.prove(output, input, implN));
		//System.out.println("implC "+em.prove(output, input, implC));
		//System.out.println(em.prove(output, input, impl) ? "It is a Partial Trigger" : "It is a Complete Trigger");
		Collections.sort(input, AttrComparator.class);
		System.out.println(input.get(0).id+input.get(1).id+input.get(2).id+input.get(3).id);
		
	}
	public class AttrComparator implements Comparator<Attribute> {

		@Override
		public int compare(Attribute arg0, Attribute arg1) {
			// TODO Auto-generated method stub
			int length0 = (arg0.key+arg0.operator+arg0.value).length();
			int length1 = (arg1.key+arg1.operator+arg1.value).length();
			return length0 > length1 ? 1 : length0 == length1 ? 0 : -1;
		}
	}
}
