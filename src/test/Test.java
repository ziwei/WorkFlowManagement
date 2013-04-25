package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matcher.ExpressionMatcher;
import matcher.GraphGenerator;
import obj.Attribute;
import obj.HandlerInfo;
import obj.SpecAttribute;
import obj.TransitionInfo;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//em.DNFSplit("a&(b|c)", "a&b|c");
//		/System.out.println(ValueMatcher.ValueMatch(new Attribute("d",">=","4",'o'), new Attribute("d","~","[34]",'i')));
//	String s1 = "11";
//	String s2 = "2";
//	System.out.println(s1.compareTo(s2));
//		ExpressionMatcher em = new ExpressionMatcher();
//		List<HandlerInfo> handlers = LoadHandlers();
//		GraphGenerator gg = new GraphGenerator();
//		gg.GenVertices(handlers);
//		for (HandlerInfo from : handlers){
//			for (HandlerInfo to : handlers){
//				gg.GenEdges(from, to, new TransitionInfo(from, to, new HashMap()));
//			}
//		}
//		System.out.println(handlers.get(0).getName()+" "+handlers.get(4).getName());
//		List<GraphPath> lPaths = gg.getPaths(handlers.get(0), handlers.get(4));
//		for (GraphPath<HandlerInfo, TransitionInfo> gpl : lPaths){
//			for (TransitionInfo ti : gpl.getEdgeList())
//				System.out.println(ti.getFrom().getName()+" "+ti.getTo().getName());
//		}
		String test = "abc";
		System.out.println(test);
		
		Attribute a = new SpecAttribute("","","","");
		System.out.println(a.getClass());
		//gg.ExportDot();
	}
	public static List<HandlerInfo> LoadHandlers() throws IOException{
		BufferedReader br;
		String name;
		String inputExpr;
		String outputExpr;
		List<HandlerInfo> handlers = new ArrayList();
		//for (int i = 1; i <= num; ++i){
			br = new BufferedReader(new FileReader("test/1.txt"));
			//System.out.println("OK till here" + num);
			while ((name = br.readLine()) != null){
				inputExpr = br.readLine();
				outputExpr = br.readLine();
				handlers.add(new HandlerInfo(name, inputExpr, outputExpr));
				br.readLine();
			}
			//System.out.println("OK till here");
			br.close();
		
		//}
		return handlers;
	}
}
