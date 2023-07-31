package org.eaSTars.z80asm.ast.parameter;

public enum RegisterPair {
	BC("BC"),
	DE("DE"),
	HL("HL"),
	SP("SP"),
	AF("AF"),
	IX("IX"),
	IY("IY"),
	AFMarked("AF'");
	
	private final String value;
	
	private RegisterPair(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
