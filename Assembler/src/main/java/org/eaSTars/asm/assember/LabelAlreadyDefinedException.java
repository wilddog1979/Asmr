package org.eaSTars.asm.assember;

import java.io.Serial;

public class LabelAlreadyDefinedException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -7531480576547923852L;

	private final String label;
	
	public LabelAlreadyDefinedException(String label) {
		super(String.format("%s label was already defined", label));
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
