package matcher;

import java.util.List;

public class IOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String expression = "((key1'='1|key1'='12)|key3'~'[ab]cd)|key4";
		//expression = expression.replaceAll("key1=12", "kv1");
		////////REMEMBER SORT EXPRESSION SET by EXPRESSION LENGTH, LONGEST FIRST
		
		//List<Attribute> l = ExpressionMatcher.AtomExtractor(expression, "ikv");
		//System.out.println(l.get(0).value+" "+l.get(1).value+" "+l.get(2).value+" "+l.get(3).key);
	}

}
