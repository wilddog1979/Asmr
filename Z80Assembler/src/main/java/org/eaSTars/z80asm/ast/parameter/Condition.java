package org.eaSTars.z80asm.ast.parameter;

public enum Condition {

	NZ("NZ", (byte)0),
	Z("Z", (byte)1),
	NC("NC", (byte)2),
	C("C", (byte)3),
	PO("PO", (byte)4),
	PE("PE", (byte)5),
	P("P", (byte)6),
	M("M", (byte)7);
	
	private String value;
	
	private byte opcode;
	
	private Condition(String value, byte opcode) {
		this.value = value;
		this.opcode = opcode;
	}

	public String getValue() {
		return value;
	}

	public byte getOpcode() {
		return opcode;
	}
}
