package org.eaSTars.asm.assember;

import java.io.Serial;

public class AssemblerException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1938435212235153829L;

	public AssemblerException(Throwable e) {
		super(e);
	}
}
