package be.ulb.dsa.multiwaymerge;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MultiwayMerge {

	private PriorityQueue<Element> priorityQueue;

	public MultiwayMerge(int capacity) {
		priorityQueue = new PriorityQueue<Element>(capacity, new ElementComparator());
	}

	@SuppressWarnings("rawtypes")
	public List sort(List<List<Integer>> input) {
		List<Integer> output = new LinkedList<Integer>();

		int[] index = new int[input.size()];
		
		createInitialHeap(input, index);

		while (!priorityQueue.isEmpty()) {
			Element element = priorityQueue.remove();
			
			int listIndex = element.getListIndex();
			
			output.add(element.getValue());
			
			if (index[listIndex] < input.get(listIndex).size()) {
				priorityQueue.add(new Element(input.get(listIndex).get(index[listIndex]), listIndex));
				index[listIndex] = ++index[listIndex];
			}
		}
		
		return output;
	}

	private void createInitialHeap(List<List<Integer>> input, int[] index) {
		for (int i = 0; i < input.size(); i++) {
			if (!input.get(i).isEmpty()) {
				priorityQueue.add(new Element(input.get(i).get(0), i));
				index[i] = ++index[i];
			} else {
				input.remove(i);
			}
		}
	}

}
