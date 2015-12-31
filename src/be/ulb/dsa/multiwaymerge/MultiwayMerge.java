package be.ulb.dsa.multiwaymerge;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MultiwayMerge {

	PriorityQueue<Element> priorityQueue;

	public MultiwayMerge(int capacity) {
		priorityQueue = new PriorityQueue<Element>(capacity, new ElementComparator());
	}

	@SuppressWarnings("rawtypes")
	public List sort(List<List<Integer>> input) {
		List<Integer> output = new ArrayList<Integer>();

		int[] index = new int[input.size()];
		
		createHeap(input, index);

		while (!priorityQueue.isEmpty()) {
			Element element = priorityQueue.remove();
			
			int listIndex = element.getIndexList();
			
			output.add(element.getData());
			
			if (index[listIndex] < input.get(listIndex).size()) {
				priorityQueue.add(new Element(input.get(listIndex).get(index[listIndex]), listIndex));
				index[listIndex] = ++index[listIndex];
			}
		}
		
		return output;
	}

	private void createHeap(List<List<Integer>> input, int[] index) {
		for (int i = 0; i < input.size(); i++) {
			if (!input.get(i).isEmpty()) {
				priorityQueue.add(new Element(input.get(i).get(0), i));
				index[i] = ++index[i];
			} else
				input.remove(i);
		}
	}

}
