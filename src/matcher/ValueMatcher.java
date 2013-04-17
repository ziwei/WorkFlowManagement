package matcher;

import java.util.List;

import dk.brics.automaton.RegExp;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;



public class ValueMatcher {
	public static final int LIMIT = 25;
	public static String ValueMatch(Attribute output, Attribute input){
		String implies = output.id+"->("+input.id+")";
		String notImplies = input.id+"->~("+output.id+")";
		if (output.operator.equals("=")){
			if (input.operator.equals("=")){
				return output.value.equals(input.value) ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				return output.value.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals(">=")){
				return output.value.compareTo(input.value)>-1 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return output.value.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return output.value.compareTo(input.value)<1 ? implies : notImplies;
			}
			else if (input.operator.equals("~")){
				Automaton intersec = BasicOperations.intersection(ToAutomaton(output), ToAutomaton(input));
				return (intersec.getNumberOfTransitions()>0) ? implies : notImplies;
			}
		}
		else if (output.operator.equals("~")){
			//System.out.println("OK till here2");
			Automaton o = ToAutomaton(output);
			String maxStr = MaxString(o, LIMIT);
			String minStr = MinString(o, LIMIT);
			//System.out.println("OK till here3");
			if (input.operator.equals("=")){
				Automaton intersec = BasicOperations.intersection(o, ToAutomaton(input));
				return (intersec.getNumberOfTransitions()>0) ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				//System.out.println("OK till here "+maxStr.compareTo(input.value));
				return maxStr.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals(">=")){
				return maxStr.compareTo(input.value)>-1 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return minStr.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return minStr.compareTo(input.value)<1 ? implies : notImplies;
			}
			else if (input.operator.equals("~")){
				Automaton intersec = BasicOperations.intersection(ToAutomaton(output), ToAutomaton(input));
				return (intersec.getNumberOfTransitions()>0) ? implies : notImplies;
			}
		}
		else if (output.operator.equals("<")){
			if (input.operator.equals("=")){
				return output.value.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				return output.value.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals(">=")){
				return output.value.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return implies;
			}
			else if (input.operator.equals("<=")){
				return implies;
			}
			else if (input.operator.equals("~")){
				Automaton i = ToAutomaton(input);
				String minStr = MinString(i, LIMIT);
				return output.value.compareTo(minStr)>0 ? implies : notImplies;
			}
		}
		else if (output.operator.equals("<=")){
			if (input.operator.equals("=")){
				return output.value.compareTo(input.value)>-1 ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				return output.value.compareTo(input.value)>0 ? implies : notImplies;
			}
			else if (input.operator.equals(">=")){
				return output.value.compareTo(input.value)>-1 ? implies : notImplies;
			}
			else if (input.operator.equals("<")){
				return implies;
			}
			else if (input.operator.equals("<=")){
				return implies;
			}
			else if (input.operator.equals("~")){
				Automaton i = ToAutomaton(input);
				String minStr = MinString(i, LIMIT);
				return output.value.compareTo(minStr)>-1 ? implies : notImplies;
			}
		}
		else if (output.operator.equals(">")){
			if (input.operator.equals("=")){
				return output.value.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				return implies;
			}
			else if (input.operator.equals(">=")){
				return implies;
			}
			else if (input.operator.equals("<")){
				return output.value.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals("<=")){
				return output.value.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals("~")){
				Automaton i = ToAutomaton(input);
				String maxStr = MaxString(i, LIMIT);
				return output.value.compareTo(maxStr)<0 ? implies : notImplies;
			}
		}
		else if (output.operator.equals(">=")){
			if (input.operator.equals("=")){
				return output.value.compareTo(input.value)>-1 ? implies : notImplies;
			}
			else if (input.operator.equals(">")){
				return implies;
			}
			else if (input.operator.equals(">=")){
				return implies;
			}
			else if (input.operator.equals("<")){
				return output.value.compareTo(input.value)<0 ? implies : notImplies;
			}
			else if (input.operator.equals("<=")){
				return output.value.compareTo(input.value)<1 ? implies : notImplies;
			}
			else if (input.operator.equals("~")){
				Automaton i = ToAutomaton(input);
				String maxStr = MaxString(i, LIMIT);
				return output.value.compareTo(maxStr)<1 ? implies : notImplies;
			}
		}
		
		return null;
	}
	
	private static Automaton ToAutomaton(Attribute attr){
		RegExp regex = new RegExp(attr.value);
		return regex.toAutomaton();
	}
	
	private static String MaxString(Automaton a, int limit){// Derive max string of a regex in Automaton format
		String maxStr="";
		State state = a.getInitialState();
		while (!state.isAccept()){
			List<Transition> lTrans = state.getSortedTransitions(true);
			char maxChar='\u0000';
			for (int i = 0; i < lTrans.size(); ++i){
				Transition t = lTrans.get(i);
				if (maxChar <= t.getMax()){
					maxChar = t.getMax();
					state = t.getDest();
					//System.out.println(state);
				}
			}
			maxStr += maxChar;
			if (maxStr.length() >= limit) break;
		}
		return maxStr;
	}
	private static String MinString(Automaton a, int limit){// Derive max string of a regex in Automaton format
		String minStr="";
		State state = a.getInitialState();
		while (!state.isAccept()){
			List<Transition> lTrans = state.getSortedTransitions(true);
			char minChar='\uffff';
			for (int i = 0; i < lTrans.size(); ++i){
				Transition t = lTrans.get(i);
				if (minChar <= t.getMin()){
					minChar = t.getMin();
					state = t.getDest();
					//System.out.println(state);
				}
			}
			minStr += minChar;
			if (minStr.length() >= limit) break;
		}
		return minStr;
	}
	
}
