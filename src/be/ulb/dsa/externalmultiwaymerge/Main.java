package be.ulb.dsa.externalmultiwaymerge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class Main {

	private static EtmMonitor monitor;
	private static String outputFile = "C:/Users/Juan/Documents/IT4BI/ULB/Database Systems Architecture/Project/test/output.txt";
	// CHECK THE FILE LOCATION AND NAME BEFORE EXECUTING
	private static String inputFile = "C:/Users/Juan/Documents/IT4BI/ULB/Database Systems Architecture/Project/test/sample.txt";
	// MODIFY THE M AND d PARAMETERS AS DESIRED BEFORE RUNNING THE PROGRAM
	private final static int M = 100000;
	private final static int d = 100;
	private static final int BUFFERSIZE = 4 * 1024;

	public static void main(String[] args) throws IOException {
		// invoke method to generate data
		// Utilities.GenerateInputData();

		// configure measurement framework
		setup();

		// invoke method to sort (external multiway)
		sortInputExternalMerge();

		// invoke method to sort (external multiway)
		//sortInputBuiltIn();

		// visualize results
		monitor.render(new SimpleTextRenderer());

		// shutdown measurement framework
		tearDown();
	}

	// Method to sort input file using External Multiway Merge Sort
	public static void sortInputExternalMerge() {
		ExternalMultiwayMerge merge = new ExternalMultiwayMerge();
		merge.sort(inputFile, M, d);
	}

	// Configures the JETM
	private static void setup() {
		BasicEtmConfigurator.configure();
		monitor = EtmManager.getEtmMonitor();
		monitor.start();
	}

	// Shut down JETM
	private static void tearDown() {
		monitor.stop();
	}
	
	// Method to sort input file using built-in method
		public static void sortInputBuiltIn() {
			EtmPoint point = monitor.createPoint("1.sortInputBuiltIn");

			Input input = new Input(inputFile, BUFFERSIZE);
			List<Integer> integers = new ArrayList<Integer>();

			try {
				input.open();
				
				while (!input.end_of_stream()) {
					// read the value
					int value = input.read_next();
					integers.add(value);
				}	
				
				Collections.sort(integers);
				
				// write the data to a file
				Output output = new Output(outputFile, BUFFERSIZE);
				try {
					for (int i = 0; i < integers.size(); i++) {
						output.write(integers.get(i));
					}
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("File cannot be found.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			point.collect();
		}
}