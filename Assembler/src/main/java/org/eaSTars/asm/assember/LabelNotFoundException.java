package org.eaSTars.asm.assember;

import java.io.Serial;

public class LabelNotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -5692979883104582747L;
	
	private final String label;
	
	public LabelNotFoundException(String label) {
		super(String.format("%s label was not found", label));
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
