package be.ulb.dsa.multiwaymerge;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element element1, Element element2) {
		return element1.getValue().compareTo(element2.getValue());
	}

}
