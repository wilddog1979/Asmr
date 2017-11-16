package org.eaSTars.asm.assember;

public class MismatchingParameterSizeException extends RuntimeException {

	private static final long serialVersionUID = -447717482321122348L;

	public MismatchingParameterSizeException(int value, int expectedBitCount) {
		super(String.format("Expected bit count: %d, evaluation result: %xh", expectedBitCount, value));
	}
	
}
