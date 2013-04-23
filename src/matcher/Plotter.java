package matcher;

import java.io.File;

import org.jgrapht.Graph;

public class Plotter {
	 GraphViz gv;
	 Graph<HandlerInfo, TransitionInfo> graph;
	 public Plotter(Graph g)
	   {   
		   gv = new GraphViz();
		   gv.addln(gv.start_graph());
		   gv.addln("graph [style=bold fontname=\"Bold\" compound=true];" +
		   	"node [shape=record color=black fontname=\"Bold\" fontcolor=blue];" +
		   	"edge [color=black fontname=\"Bold\" fontcolor=red];");
		   graph = g;
		   //VerticesToDOT();
		   //EdgesToDOT();
	   }
	 public void VerticesToDOT(){
		 for (HandlerInfo hi : graph.vertexSet()){
			 if (hi.acyclic == true){
				 gv.add(hi.name+"[label=\"<Handler>"+hi.name+"|{<Input>"+Escape(hi.newiExpr)+"|<Output>"+Escape(hi.newoExpr)+"}\"]");
			 }
			 else {
				 gv.add(hi.name+"[color=red label=\"<Handler>"+hi.name+"|{<Input>"+Escape(hi.newiExpr)+"|<Output>"+Escape(hi.newoExpr)+"}\"]");
			 }
		 }
	 }
	 
	 public void EdgesToDOT(){
		 for (TransitionInfo ti : graph.edgeSet()){
			 if (ti.acyclic == true){
				 if (ti.complete.size() > 0)
					 gv.add(ti.from.name+":Output->"+ti.to.name+":Input");
				 else
					 gv.add(ti.from.name+":Output->"+ti.to.name+":Input[style=dotted]");
			 }
			 else {
				 if (ti.complete.size() > 0)
					 gv.add(ti.from.name+":Output->"+ti.to.name+":Input[color=red]");
				 else
					 gv.add(ti.from.name+":Output->"+ti.to.name+":Input[color=red style=dotted]");
			 }
		 }
	 }
	 private String Escape(String expr){
		 //String newstr = expr.replaceAll("\\|", "\\\\|");
		 return expr.replaceAll("\\|", "\\\\|");
	 }
	 public void ExportDot(){
		 String type = "png";
		 File out = new File("plot." + type);
		 gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	 }
	   		
//	      String type = "gif";
//	    String type = "dot";
//	    String type = "fig";    // open with xfig
//	    String type = "pdf";
//		    String type = "ps";
//	    String type = "svg";    // open with inkscape
	   // String type = "png";
//	      String type = "plain";
		 //  File out = new File("auto." + type);   // Linux
//		   File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
		//   gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	  // }
}
