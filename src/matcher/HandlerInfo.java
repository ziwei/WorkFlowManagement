package matcher;

import java.util.Collections;
import java.util.List;

public class HandlerInfo {
String name;
String inputExpr;
String outputExpr;
String newiExpr;
String newoExpr;
List<Attribute> inputAtoms;
List<Attribute> outputAtoms;
boolean acyclic;
public HandlerInfo(String n, String i, String o){
	name = n;
	inputExpr = i;
	outputExpr = o;
	inputAtoms = ExpressionMatcher.AtomExtractor(inputExpr, "ikv");
	outputAtoms = ExpressionMatcher.AtomExtractor(outputExpr, "okv");
	Collections.sort(inputAtoms, new AttrComparator());
	Collections.sort(outputAtoms, new AttrComparator());
	newiExpr = ExpressionMatcher.ExpressionFormatter(inputAtoms, inputExpr);
	newoExpr = ExpressionMatcher.ExpressionFormatter(outputAtoms, outputExpr);
	acyclic = true;
}
}
