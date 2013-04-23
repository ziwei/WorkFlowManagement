package matcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//ExpressionMatcher em = new ExpressionMatcher();
		//em.DNFSplit("a&(b|c)", "a&b|c");
//		/System.out.println(ValueMatcher.ValueMatch(new Attribute("d",">=","4",'o'), new Attribute("d","~","[34]",'i')));
//	String s1 = "11";
//	String s2 = "2";
//	System.out.println(s1.compareTo(s2));
		List<HandlerInfo> handlers = LoadHandlers(2);
		GraphGenerator gg = new GraphGenerator();
		gg.GenVertices(handlers);
		gg.GenEdges(handlers.get(0), handlers.get(1), new TransitionInfo(handlers.get(0), handlers.get(1), new HashMap()));
		//gg.ExportDot();
	}
	public static List<HandlerInfo> LoadHandlers(int num) throws IOException{
		BufferedReader br;
		String name;
		String inputExpr;
		String outputExpr;
		List<HandlerInfo> handlers = new ArrayList();
		for (int i = 1; i <= num; ++i){
			br = new BufferedReader(new FileReader("test/"+i+".txt"));
			//System.out.println("OK till here" + num);
			name = br.readLine();
			inputExpr = br.readLine();
			outputExpr = br.readLine();
			//System.out.println("OK till here");
			br.close();
			handlers.add(new HandlerInfo(name, inputExpr, outputExpr));
		}
		return handlers;
	}
}
