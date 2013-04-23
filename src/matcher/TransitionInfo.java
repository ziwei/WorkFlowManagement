package matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TransitionInfo {
	HandlerInfo from;
	HandlerInfo to;
	ArrayList complete = new ArrayList();
	ArrayList partial = new ArrayList();
	ArrayList unrelated = new ArrayList();
	boolean acyclic;
	public TransitionInfo(HandlerInfo f, HandlerInfo t, Map result){
		from = f;
		to = t;
		acyclic = true;
		Set s = result.entrySet();
		Iterator i = s.iterator();
		while (i.hasNext()){
			Entry temp = (Entry) i.next();
			if ((Integer)temp.getValue() == 0){
				complete.add(temp.getKey());
			}
			else if ((Integer)temp.getValue() == 1){
				partial.add(temp.getKey());
			}
			else if ((Integer)temp.getValue() == 2){
				unrelated.add(temp.getKey());
			}
		}
	}
	
}
