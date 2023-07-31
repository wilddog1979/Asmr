package org.eaSTars.asm.assember;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class PushbackInputStream extends InputStream {

	private final InputStream inputStream;
	
	private final Stack<Byte> unreadbuffer = new Stack<Byte>();
	
	public PushbackInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@Override
	public int read() throws IOException {
		int result = -1;
		if (!unreadbuffer.isEmpty()) {
			result = unreadbuffer.pop();
		} else {
			result = inputStream.read();
		}
		return result;
	}
	
	public void unread(byte value) {
		unreadbuffer.push(value);
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
