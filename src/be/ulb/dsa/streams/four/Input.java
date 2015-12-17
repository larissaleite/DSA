package be.ulb.dsa.streams.four;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Input {
	
	private MappedByteBuffer byteBuffer;
	private RandomAccessFile memoryMappedFile;
	private String path;
	
	private int bufferCapacity;
	
	public Input(String path, int bufferCapacity) {
		this.path = path;
		this.bufferCapacity = bufferCapacity;
	}
	
	public void open() throws IOException {
		memoryMappedFile = new RandomAccessFile(path, "rw");
		
		byteBuffer = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufferCapacity);
	}
	
	public int read_next() throws IOException {
		int readInt = byteBuffer.getInt();
		return readInt;
	}
	
	public boolean end_of_stream() throws IOException {
		if (byteBuffer.hasRemaining()) {
			return false;
		}
		return true;
	}

}
