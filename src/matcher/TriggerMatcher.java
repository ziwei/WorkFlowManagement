package matcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import orbital.logic.imp.Formula;
import orbital.logic.sign.ParseException;

import java.util.regex.*;

public class TriggerMatcher {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, ParseException, IOException {
		int NUMBER = 5;
		// TODO Auto-generated method stub
		ExpressionMatcher em = new ExpressionMatcher();
		GraphGenerator gg = new GraphGenerator();
		Plotter plotter = new Plotter(gg.graph);
		List<HandlerInfo> handlers = LoadHandlers(NUMBER);
		gg.GenVertices(handlers);
		for (int i = 0; i < handlers.size(); ++i){
			for (int j = 0; j < handlers.size(); ++j){
				System.out.println("From " + handlers.get(i).name + " To " + handlers.get(j).name);
				Map result = MatchExpr(handlers.get(i), handlers.get(j), em);
				System.out.println(handlers.get(i).outputExpr + " " + handlers.get(j).inputExpr);
				System.out.println(result);
				if (result.containsValue(0)||result.containsValue(1)){
					gg.GenEdges(handlers.get(i), handlers.get(j), new TransitionInfo(handlers.get(i), handlers.get(j), result));
				}
				TriggerDisplay(result);
			}
		}
		gg.ExportDot();
		List cycles = gg.CycleDetection();
		System.out.println(cycles);
		plotter.VerticesToDOT();
		plotter.EdgesToDOT();
		plotter.ExportDot();
	}
	public static void TriggerDisplay(Map<String, Integer> triggers){
		
		Set<String> keyset = triggers.keySet();
		Iterator<String> i = keyset.iterator();
		while (i.hasNext()){
			String subExpr = i.next();
			switch(triggers.get(subExpr)){
			case 0: System.out.println(subExpr + " is a complete trigger");break;
			case 1: System.out.println(subExpr + " is a partial trigger");break;
			case 2: System.out.println(subExpr + " is not a trigger");break;
			default: break;
			}
		}
	}
	public static Map MatchExpr(HandlerInfo handlerFrom, HandlerInfo handlerTo, ExpressionMatcher em){
		try {
			Formula[] axioms = em.AxiomsGen(handlerFrom.outputAtoms, handlerTo.inputAtoms);
			return em.Prove(axioms, handlerFrom.newoExpr, handlerTo.newiExpr);
		} catch (IllegalArgumentException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}//This is to extract 
		
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
