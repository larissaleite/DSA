package be.ulb.dsa.multiwaymerge;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
		Output output1 = new Output("/Users/larissaleite/Documents/DSAProject/integerfile1.dat");
		Output output2 = new Output("/Users/larissaleite/Documents/DSAProject/integerfile2.dat");
		Output output3 = new Output("/Users/larissaleite/Documents/DSAProject/integerfile3.dat");
		
		output1.write(4);
		output1.write(10);
		output1.write(20);
		
		output2.write(2);
		output2.write(5);
		output2.write(12);
		output2.write(21);
		
		output3.write(1);
		output3.write(3);
		output3.write(11);
		
		output1.close();
		output2.close();
		output3.close();
		
		int d = Integer.parseInt(args[0]);
		
		List<List<Integer>> inputStreams = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < d; i++) {
			List<Integer> integers = new LinkedList<Integer>();
			
			String filepath = args[i+1];
			//String filepath = "/Users/larissaleite/Documents/DSAProject/test3/Out"+(i+1)+".txt";
			Input input = new Input(filepath);
			
			try {
				input.open();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			while (!input.end_of_stream()) {
				try {
					int value = input.read_next();
					integers.add(value);
				} catch (EOFException e) {
				}
			}
			
			inputStreams.add(integers);
		}
		
		MultiwayMerge multiwayMerge = new MultiwayMerge(25);
		List<Integer> sorted = multiwayMerge.sort(inputStreams);
		
		Output sortedOutput = new Output("/Users/larissaleite/Documents/DSAProject/test3/integerfileoutput.dat");
		
		for (int i = 0; i < sorted.size(); i++) {
			int element = sorted.get(i);

			sortedOutput.write(element);
			System.out.println(element);
		}
		
		sortedOutput.close();
	}

}
