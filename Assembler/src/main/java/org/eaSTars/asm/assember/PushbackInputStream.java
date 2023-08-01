package org.eaSTars.asm.assember;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class PushbackInputStream extends InputStream {

	private final InputStream inputStream;
	
	private final Stack<Byte> unreadBuffer = new Stack<>();
	
	public PushbackInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@Override
	public int read() throws IOException {
		return !unreadBuffer.isEmpty() ? unreadBuffer.pop() : inputStream.read();
	}
	
	public void unread(byte value) {
		unreadBuffer.push(value);
	}
	
	public void unread(byte[] data) {
		for (int i = data.length - 1; i >= 0; --i) {
			unread(data[i]);
		}
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
	
}
