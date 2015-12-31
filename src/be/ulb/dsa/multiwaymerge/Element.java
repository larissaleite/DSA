package be.ulb.dsa.multiwaymerge;

public class Element {
	
	Integer data;
	Integer fromListIndex;

	public Element(int data, int indexList) {
		this.data = data;
		this.fromListIndex = indexList;
	}

	public Integer getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getIndexList() {
		return fromListIndex;
	}

	public void setIndexList(int indexList) {
		this.fromListIndex = indexList;
	}


}
