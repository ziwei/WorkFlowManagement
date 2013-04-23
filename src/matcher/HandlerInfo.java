package matcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerInfo {
private String name;
private String inputExpr;
private String outputExpr;
private String newiExpr;
private String newoExpr;
private List<Attribute> inputAtoms;
private List<Attribute> outputAtoms;
private Map atoms;
private boolean acyclic;
public HandlerInfo(String n, String i, String o){
	setAtoms(new HashMap());
	setName(n);
	setInputExpr(i);
	setOutputExpr(o);
	setInputAtoms(ExpressionMatcher.AtomExtractor(getInputExpr(), "ikv", getAtoms()));
	setOutputAtoms(ExpressionMatcher.AtomExtractor(getOutputExpr(), "okv", getAtoms()));
	Collections.sort(getInputAtoms(), new AttrComparator());
	Collections.sort(getOutputAtoms(), new AttrComparator());
	setNewiExpr(ExpressionMatcher.ExpressionFormatter(getInputAtoms(), getInputExpr()));
	setNewoExpr(ExpressionMatcher.ExpressionFormatter(getOutputAtoms(), getOutputExpr()));
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
}
