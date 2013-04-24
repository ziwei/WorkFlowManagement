package obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class HandlerInfo {
private String name;
private String inputExpr;
private String outputExpr;
private String newiExpr;
private String newoExpr;
private List<Attribute> inputAtoms;
private List<Attribute> outputAtoms;
private List<Attribute> specialAttr;
private Map atoms;
private boolean acyclic;
public HandlerInfo(String n, String i, String o){
	setAtoms(new HashMap());
	setName(n);
	setInputExpr(i);
	setOutputExpr(o);
	setInputAtoms(AtomExtractor(getInputExpr(), "ikv"));
	setOutputAtoms(AtomExtractor(getOutputExpr(), "okv"));
	Collections.sort(getInputAtoms(), new AttrComparator());
	Collections.sort(getOutputAtoms(), new AttrComparator());
	setNewiExpr(ExpressionFormatter(getInputAtoms(), getInputExpr()));
	setNewoExpr(ExpressionFormatter(getOutputAtoms(), getOutputExpr()));
	setAcyclic(true);
}
public boolean isAcyclic() {
	return acyclic;
}
public void setAcyclic(boolean acyclic) {
	this.acyclic = acyclic;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getInputExpr() {
	return inputExpr;
}
public void setInputExpr(String inputExpr) {
	this.inputExpr = inputExpr;
}
public String getOutputExpr() {
	return outputExpr;
}
public void setOutputExpr(String outputExpr) {
	this.outputExpr = outputExpr;
}
public Map getAtoms() {
	return atoms;
}
public void setAtoms(Map atoms) {
	this.atoms = atoms;
}
public String getNewoExpr() {
	return newoExpr;
}
public void setNewoExpr(String newoExpr) {
	this.newoExpr = newoExpr;
}
public List<Attribute> getOutputAtoms() {
	return outputAtoms;
}
public void setOutputAtoms(List<Attribute> outputAtoms) {
	this.outputAtoms = outputAtoms;
}
public List<Attribute> getInputAtoms() {
	return inputAtoms;
}
public void setInputAtoms(List<Attribute> inputAtoms) {
	this.inputAtoms = inputAtoms;
}
public String getNewiExpr() {
	return newiExpr;
}
public void setNewiExpr(String newiExpr) {
	this.newiExpr = newiExpr;
}
public List<Attribute> AtomExtractor (String expr, String flag){
	List<Attribute> l = new ArrayList();
	expr = expr.replaceAll("[()]", "");
	String exprList[] = expr.split("[&|]");
	for(int index = 0; index<exprList.length; ++index){
		String kvp[] = exprList[index].split("'");
		if (kvp.length == 3){
			l.add(new Attribute(flag+l.size(),kvp[0],kvp[1],kvp[2]));
		}
		else if (kvp.length == 2){
			l.add(new Attribute(flag+l.size(),kvp[0],kvp[1],""));
		}
		else if (kvp.length == 1){
			l.add(new Attribute(flag+l.size(),kvp[0],"ALL",""));
		}
		atoms.put(flag+l.size(), exprList[index]);
	}
	
	//System.out.println(l.get(0).id+l.get(1).id+l.get(2).id);
	return l;
}
public String ExpressionFormatter(List kvs, String expr){
	Iterator i = kvs.iterator();
	while (i.hasNext()){
		Attribute attr = (Attribute)i.next();
		String kv;
		if (attr.getOperator().equals("ALL"))
			kv= attr.getKey();
		else
			kv=attr.getKey()+"'"+attr.getOperator()+"'"+attr.getValue();
		//System.out.println("kv "+ Pattern.quote(kv));
		String atom = attr.getId();
		expr = expr.replaceAll(Pattern.quote(kv), atom); //literally escapping
	}
	return expr;
}
}
