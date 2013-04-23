package matcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class IOTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String expression = "((key1'='1|key1'='12)|key3'~'[ab]cd)|key4";
		//expression = expression.replaceAll("key1=12", "kv1");
		////////REMEMBER SORT EXPRESSION SET by EXPRESSION LENGTH, LONGEST FIRST
		BufferedReader br;
		String line;
		
			br = new BufferedReader(new FileReader("test/1.txt"));
			while ((line = br.readLine()) != null) {
				   // process the line.
				System.out.println(line);
			} 
			br.close();
		
		//List<Attribute> l = ExpressionMatcher.AtomExtractor(expression, "ikv");
		//System.out.println(l.get(0).value+" "+l.get(1).value+" "+l.get(2).value+" "+l.get(3).key);
	}

}
