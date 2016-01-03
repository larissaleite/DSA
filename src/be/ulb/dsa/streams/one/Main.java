package be.ulb.dsa.streams.one;

import java.io.IOException;
import java.util.Random;

import be.ulb.dsa.util.Benchmarker;
import etm.core.monitor.EtmPoint;

public class Main {
	
	private static final int FILES = 30;
	private static final String OUTPUTFOLDER = "/Users/larissaleite/Documents/DSAProject/stream1/";
	private static final int NUMBERS = 1000000;

	public static void main(String[] args) throws IOException {
		
		Benchmarker.setup();
		
		EtmPoint point = Benchmarker.addPoint("stream1:"+FILES+"files:"+NUMBERS+"numbers");
		
		outputStream();
		inputStream();
		
		point.collect();
		
		Benchmarker.showResults();
		
		Benchmarker.tearDown();

	}

	private static void outputStream() {
		for (int f = 0; f < FILES; f++) {
			Output output = new Output(OUTPUTFOLDER + "sample"+f+"_"+FILES+"_"+NUMBERS+".txt");
			Random generator = new Random();
	
			try {
				for (int i = 0; i < NUMBERS; i++) {
					int element = generator.nextInt(NUMBERS);
					output.write(element);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void inputStream() {
		for (int f = 0; f < FILES; f++) {
			Input input = new Input(OUTPUTFOLDER + "sample"+f+"_"+FILES+"_"+NUMBERS+".txt");

			try {
				while (!input.end_of_stream()) {
					//System.out.println(input.read_next());
					input.read_next();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
