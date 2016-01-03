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
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

public class ExternalMultiwayMerge {

	private static final EtmMonitor MONITOR = EtmManager.getEtmMonitor();
	public Queue<String> streamReferences = new LinkedList<String>();
	String outputFolder = "C:/Users/Juan/Documents/IT4BI/ULB/Database Systems Architecture/Project/test/";
	private int streamNo = 1;
	private final int BUFFERSIZE = 4 * 1024;

	/* This method reads the input file according to the memory available (M),
	 * every M values of the file are stored in a single txt file.
	 * */
	public void readAndSplit(String filePath, int M) {

		EtmPoint point = MONITOR.createPoint("2.ExternalMergeSort:readAndSplit");

		Input input = new Input(filePath, BUFFERSIZE);
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
					EtmPoint pointW = MONITOR.createPoint("4.ExternalMergeSort:writeToDisk_initial_stream");
					writeToDisk(integers, "initial_stream");
					pointW.collect();
					integers.clear();
				}
			}

			if (!integers.isEmpty()) {
				EtmPoint pointW = MONITOR.createPoint("4.ExternalMergeSort:writeToDisk_initial_stream");
				writeToDisk(integers, "initial_stream");
				pointW.collect();
				integers.clear();
			}

			streamNo = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		point.collect();
	}

	/* This method writes a stream into a txt file. If the stream comes from the unsorted input,
	 * then the stream is sorted before being written. Every file location is stored in a queue.
	*/
	public void writeToDisk(List<Integer> integers, String type) {
		// set the file name
		String filePath = outputFolder + type + streamNo + ".txt";

		if (type.equals("initial_stream")) {
			EtmPoint point = MONITOR.createPoint("7.ExternalMergeSort:builtIn_stream_mergesort");
			// sort the stream
			Collections.sort(integers);
			point.collect();
		}

		// write the data to a file
		Output output = new Output(filePath, BUFFERSIZE);
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

	/* This method sorts a given d amount of streams by using the multiway merge sort implementation
	 * */
	@SuppressWarnings("unchecked")
	public void mergeStreams(int d) {

		// Check whether d is a valid number
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
		EtmPoint pointM = MONITOR.createPoint("6.ExternalMergeSort:multiway_merge");
		MultiwayMerge multiwayMerge = new MultiwayMerge(25);
		List<Integer> mergedStream = multiwayMerge.sort(inputStreams);
		pointM.collect();

		// UNCOMMENT to print the mergedstream
		/*
		 * System.out.println("OUTPUT"); for (int i = 0; i <
		 * mergedStream.size(); i++) { System.out.println(mergedStream.get(i));
		 * }
		 */

		// Write the result stream to disk
		EtmPoint pointW = MONITOR.createPoint("5.ExternalMergeSort:writeToDisk_merged_stream");
		writeToDisk(mergedStream, "merged_stream");
		pointW.collect();

		// if necessary, merge the next d streams
		// stop condition
		if (streamReferences.size() != 1) {
			mergeStreams(d);
		} else {
			// UNCOMMENT to write the plain numbers to a file. This will affect
			// execution time
			//writeNumbers(mergedStream);
			return;
		}
	}

	// Loads the data in a txt file into memory, the data is stored in an ArrayList
	public List<Integer> loadStream(String filePath) {
		Input input = new Input(filePath, BUFFERSIZE);
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

	//Innitiates the sorting process
	public void sort(String filePath, int M, int d) {

		EtmPoint point = MONITOR.createPoint("1.ExternalMergeSort:sort_input");

		readAndSplit(filePath, M);

		EtmPoint pointM = MONITOR.createPoint("3.ExternalMergeSort:merge_sorted_streams");
		mergeStreams(d);
		pointM.collect();

		point.collect();
	}

	// Complementary method to write the numbers in plain text
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
