package be.ulb.dsa.streams.four;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String file = "/Users/larissaleite/Documents/integerfile.dat";

		int bufferSize = 16;
		
		Output output = new Output(file, bufferSize);
		output.create();
		
		for (int i=0; i<4; i++) {
			output.write(i);
		}
		
		//output.print();
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
