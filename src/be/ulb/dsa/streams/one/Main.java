package be.ulb.dsa.streams.one;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String file = "/Users/larissaleite/Documents/integerfile.dat";

		Output output = new Output(file);
		output.create();
		
		for (int i=0; i<5; i++) {
			output.write(i);
		}
		
		output.close();
		
		Input input = new Input(file);

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
