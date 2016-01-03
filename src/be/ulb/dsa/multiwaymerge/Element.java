package be.ulb.dsa.multiwaymerge;

public class Element {
	
	private Integer value;
	private Integer listIndex;

	public Element(int value, int listIndex) {
		this.value = value;
		this.listIndex = listIndex;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}
}
