package be.ulb.dsa.multiwaymerge;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Input {
	
	private InputStream inputStream;
	private DataInputStream dataInputStream;
	
	private String path;
	
	public Input(String path) {
		this.path = path;
	}
	
	public void open() throws FileNotFoundException {
		inputStream = new FileInputStream(path);
		dataInputStream = new DataInputStream(inputStream);
	}
	
	public int read_next() throws IOException {
		int readInt = dataInputStream.readInt();
		return readInt;
	}
	
	public boolean end_of_stream() throws IOException {
		if (dataInputStream.available() == 0) {
			return true;
		}
		return false;
	}

}
