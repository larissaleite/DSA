package be.ulb.dsa.streams.three;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String file = "/Users/larissaleite/Documents/integerfile.dat";

		int bufferSize = 1;
		
		Output output = new Output(file, bufferSize);
		output.create();
		
		for (int i=0; i<500; i++) {
			output.write(i*10000000);
		}
		
		output.close();
		
		Input input = new Input(file, bufferSize);

		try {
			input.open();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (!input.end_of_stream()) {
			System.out.println(input.read_next());
		}

	}
}
