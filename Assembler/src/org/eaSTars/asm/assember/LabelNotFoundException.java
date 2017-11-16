package org.eaSTars.asm.assember;

public class LabelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5692979883104582747L;
	
	private String label;
	
	public LabelNotFoundException(String label) {
		super(String.format("%s label was not found", label));
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
