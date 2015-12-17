package be.ulb.dsa.streams.three;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Input {
	
	private InputStream inputStream;
	private DataInputStream dataInputStream;
	private BufferedInputStream bufferedInputStream;
	
	private String path;
	
	private int bufferSize;
	
	public Input(String path, int bufferSize) {
		this.path = path;
		this.bufferSize = bufferSize;
	}
	
	public void open() throws FileNotFoundException {
		inputStream = new FileInputStream(path);
		bufferedInputStream = new BufferedInputStream(inputStream, bufferSize);
		dataInputStream = new DataInputStream(bufferedInputStream);
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
