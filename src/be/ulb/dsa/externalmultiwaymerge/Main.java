package be.ulb.dsa.externalmultiwaymerge;

import java.io.IOException;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.renderer.SimpleTextRenderer;

public class Main {

	private static EtmMonitor monitor;
	// CHECK THE FILE LOCATION AND NAME BEFORE EXECUTING
	private static String inputFile = "/Users/larissaleite/Documents/DSAProject/sample.txt";
	private final static int M = 10000;
	private final static int d = 50;

	public static void main(String[] args) throws IOException {
		//Utilities.GenerateInputData();
		// configure measurement framework
		setup();

		ExternalMultiwayMerge merge = new ExternalMultiwayMerge();
		merge.sort(inputFile, M, d);
		
		// visualize results
	   monitor.render(new SimpleTextRenderer());
		
		// shutdown measurement framework
		tearDown();
	}

	private static void setup() {
		BasicEtmConfigurator.configure();
		monitor = EtmManager.getEtmMonitor();
		monitor.start();
	}

	private static void tearDown() {
		monitor.stop();
	}
}