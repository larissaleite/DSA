package be.ulb.dsa.util;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class Benchmarker {

	private static EtmMonitor monitor;

	public static void setup() {
		BasicEtmConfigurator.configure();
		monitor = EtmManager.getEtmMonitor();
		monitor.start();
	}
	
	public static EtmPoint addPoint(String name) {
		EtmPoint point = monitor.createPoint(name);
		return point;
	}

	public static void showResults() {
		monitor.render(new SimpleTextRenderer());
	}

	public static void tearDown() {
		monitor.stop();
	}

}
