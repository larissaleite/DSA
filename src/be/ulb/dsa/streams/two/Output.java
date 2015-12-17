package be.ulb.dsa.streams.two;

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
	
	public Output(String filename) {
		this.filename = filename;
	}
	
	public void create() throws FileNotFoundException {
		outputStream = new FileOutputStream(filename);
		bufferedOutputStream = new BufferedOutputStream(outputStream);
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
