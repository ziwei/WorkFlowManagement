package matcher;

import java.util.Comparator;

public class AttrComparator implements Comparator<Attribute> {

	@Override
	public int compare(Attribute arg0, Attribute arg1) {
		// TODO Auto-generated method stub
		int length0 = (arg0.key+arg0.operator+arg0.value).length();
		int length1 = (arg1.key+arg1.operator+arg1.value).length();
		return length0 > length1 ? -1 : length0 == length1 ? 0 : 1;
	}
}
