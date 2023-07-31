package org.eaSTars.asm.assember;

import java.io.Serial;

public class MismatchingParameterSizeException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -447717482321122348L;

	public MismatchingParameterSizeException(int value, int expectedBitCount) {
		super(String.format("Expected bit count: %d, evaluation result: %xh", expectedBitCount, value));
	}
	
}
