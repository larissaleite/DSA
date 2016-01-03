package be.ulb.dsa.streams.three;

import java.io.IOException;
import java.util.Random;

import be.ulb.dsa.util.Benchmarker;
import etm.core.monitor.EtmPoint;

public class Main {
	
	private static final int FILES = 30;
	private static final String OUTPUTFOLDER = "/Users/larissaleite/Documents/DSAProject/stream3/";
	private static final int NUMBERS = 1000000;
	
	private static final int BUFFER_SIZE = 4000*1024;

	public static void main(String[] args) throws IOException {
		
		Benchmarker.setup();
		
		EtmPoint point = Benchmarker.addPoint("stream3:"+FILES+"files:"+NUMBERS+"numbers");
		
		outputStream();
		inputStream();
		
		point.collect();
		
		Benchmarker.showResults();
		
		Benchmarker.tearDown();
	}

	private static void inputStream() {
		for (int f = 0; f < FILES; f++) {
			Input input = new Input(OUTPUTFOLDER + "sample"+f+"_"+FILES+"_"+NUMBERS+"_"+BUFFER_SIZE+".txt", BUFFER_SIZE);
	
			try {
				while (!input.end_of_stream()) {
					input.read_next();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void outputStream() {
		for (int f = 0; f < FILES; f++) {
			Output output = new Output(OUTPUTFOLDER + "sample"+f+"_"+FILES+"_"+NUMBERS+"_"+BUFFER_SIZE+".txt", BUFFER_SIZE);
			Random generator = new Random();
			
			for (int i = 0; i < NUMBERS; i++) {
				int element = generator.nextInt(NUMBERS);
				
				try {
					output.write(element);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
