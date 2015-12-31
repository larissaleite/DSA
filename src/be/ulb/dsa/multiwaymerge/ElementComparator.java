package be.ulb.dsa.multiwaymerge;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element arg0, Element arg1) {
		return arg0.getData().compareTo(arg1.getData());
	}

}
