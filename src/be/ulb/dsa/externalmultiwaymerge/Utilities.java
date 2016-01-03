package be.ulb.dsa.externalmultiwaymerge;

import java.io.IOException;
import java.util.Random;

public class Utilities {

	private static final int AMOUNT = 3000000;
	private static final String FILENAME = "sample1.txt";
	private static final String OUTPUTFOLDER = "C:/Users/Juan/Documents/IT4BI/ULB/Database Systems Architecture/Project/data/";
	private static final int BUFFERSIZE = 4*1024; 
	
	public static void GenerateInputData() {
		Output output = new Output(OUTPUTFOLDER + FILENAME, BUFFERSIZE);
		Random generator = new Random();

		try {
			for (int i = 0; i < AMOUNT; i++) {
				int element = generator.nextInt(AMOUNT) - (AMOUNT/2);
				//System.out.println(element);
				output.write(element);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
