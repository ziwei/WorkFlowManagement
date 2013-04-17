package matcher;

public class IOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String expression = "((key1=1|key1=12)|key3~[ab]cd)|key4<=13";
		expression = expression.replaceAll("key1=12", "kv1");
		////////REMEMBER SORT EXPRESSION SET by EXPRESSION LENGTH, LONGEST FIRST
		System.out.println(expression);
	}

}
