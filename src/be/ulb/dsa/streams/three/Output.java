package be.ulb.dsa.streams.three;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Output {
	
	private OutputStream outputStream;
	private BufferedOutputStream bufferedOutputStream;
	private DataOutputStream dataOutputStream;
	
	private String filename;
	
	private int bufferSize;
	
	public Output(String filename, int bufferSize) {
		this.filename = filename;
		this.bufferSize = bufferSize;
	}
	
	public void create() throws FileNotFoundException {
		outputStream = new FileOutputStream(filename);
		bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
		dataOutputStream = new DataOutputStream(bufferedOutputStream);
	}
	
	public void write(int element) throws IOException {
		dataOutputStream.writeInt(element);
	}
	
	public void close() throws IOException {
		dataOutputStream.flush();
		outputStream.close();
		dataOutputStream.close();
	}

}
