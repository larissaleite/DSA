package be.ulb.dsa.externalmultiwaymerge;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import be.ulb.dsa.multiwaymerge.MultiwayMerge;

public class ExternalMultiwayMerge {

	public Queue<String> streamReferences = new LinkedList<String>();
	String outputFolder = "/Users/larissaleite/Documents/DSAProject/test4";
	int streamNo = 0;

	public void readAndSplit(String filePath, int M) {

		Input input = new Input(filePath);
		List<Integer> integers = new ArrayList<Integer>();

		try {
			input.open();
		} catch (FileNotFoundException e) {
			System.out.println("File cannot be found.");
		}

		try {
			while (!input.end_of_stream()) {
				// read the value
				int value = input.read_next();
				integers.add(value);
				// check whether main memory capacity has been reached
				if (integers.size() == M) {
					// System.out.println("stream" + streamNo + ".txt");
					writeToDisk(integers, "stream");
					integers.clear();
				}
			}

			if (!integers.isEmpty()) {
				// System.out.println("stream" + streamNo + ".txt");
				writeToDisk(integers, "stream");
				integers.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToDisk(List<Integer> integers, String type) {
		// set the file name
		String filePath = outputFolder + type + streamNo + ".txt";

		// sort the stream
		Collections.sort(integers);

		// write the data to a file
		Output output = new Output(filePath);
		try {
			for (int i = 0; i < integers.size(); i++) {
				output.write(integers.get(i));
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Store the reference to the stream in a queue
		streamReferences.add(filePath);
		streamNo++;
	}

	@SuppressWarnings("unchecked")
	public void mergeStreams(int d) {

		//Check whether d is a valid number
		if (d > streamReferences.size()) {
			d = streamReferences.size();
		}
		List<List<Integer>> inputStreams = new ArrayList<List<Integer>>();
		
		// Load in memory the d first streams
		for (int i = 0; i < d; i++) {
			List<Integer> integers = loadStream(streamReferences.poll());
			inputStreams.add(integers);
		}

		// Merge the d streams
		MultiwayMerge multiwayMerge = new MultiwayMerge(25);
		List<Integer> mergedStream = multiwayMerge.sort(inputStreams);

		System.out.println("OUTPUT");
		for (int i = 0; i < mergedStream.size(); i++) {
			System.out.println(mergedStream.get(i));
		}

		// Write the result stream to disk
		writeToDisk(mergedStream, "output");

		// if necessary, merge the next d streams
		// stop condition
		if (streamReferences.size() != 1) {
			mergeStreams(d);
		} else {
			//write the plain numbers to a file
			writeNumbers(mergedStream);
			return;
		}
	}

	public List<Integer> loadStream(String filePath) {
		Input input = new Input(filePath);
		List<Integer> integers = new ArrayList<Integer>();

		try {
			input.open();

			while (!input.end_of_stream()) {
				// read the value
				int value = input.read_next();
				integers.add(value);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File cannot be found.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return integers;
	}

	public void sort(String filePath, int M, int d) {
		readAndSplit(filePath, M);
		mergeStreams(d);
	}
	
	//Complementary method to write the numbers in plain text
	public void writeNumbers(List<Integer> integers) {
		String filePath = outputFolder + "SortedNumbers.txt";
		try {
			Writer writer = new FileWriter(filePath);
			for (int i = 0; i < integers.size(); i++) {
				writer.write(integers.get(i) + " ");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
