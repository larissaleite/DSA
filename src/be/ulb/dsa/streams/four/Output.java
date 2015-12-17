package be.ulb.dsa.streams.four;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Output {
	
	private MappedByteBuffer byteBuffer;
	private RandomAccessFile memoryMappedFile;
	
	private int bufferCapacity;
	
	private String filename;
	
	public Output(String filename, int bufferCapacity) {
		this.filename = filename;
		this.bufferCapacity = bufferCapacity;
	}
	
	public void create() throws IOException {
		memoryMappedFile = new RandomAccessFile(filename, "rw");
		
		byteBuffer = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufferCapacity);
	}
	
	public void write(Integer element) throws IOException {
		if (byteBuffer.hasRemaining()) {
			byteBuffer.putInt(element);
		}
	}
	
	public void close() throws IOException {
		memoryMappedFile.close();
	}
	
	public void print() throws IOException {
		while (byteBuffer.hasRemaining()) {
			System.out.println((int) byteBuffer.get());;
		}
	}

}
