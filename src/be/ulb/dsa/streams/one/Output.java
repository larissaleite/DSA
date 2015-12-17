package be.ulb.dsa.streams.one;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Output {
	
	private DataOutputStream dataOutputStream;
	private OutputStream outputStream;
	
	private String filename;
	
	public Output(String filename) {
		this.filename = filename;
	}
	
	public void create() throws FileNotFoundException {
		outputStream = new FileOutputStream(filename);
		dataOutputStream = new DataOutputStream(outputStream);
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
