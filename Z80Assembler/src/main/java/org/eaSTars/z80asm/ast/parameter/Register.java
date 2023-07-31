package org.eaSTars.z80asm.ast.parameter;

public enum Register {
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	H("H"),
	L("L"),
	I("I"),
	R("R"),
	AMarked("A'"),
	BMarked("B'"),
	CMarked("C'"),
	DMarked("D'"),
	EMarked("E'"),
	HMarked("H'"),
	LMarked("L'");
	
	private final String value;
	
	private Register(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
