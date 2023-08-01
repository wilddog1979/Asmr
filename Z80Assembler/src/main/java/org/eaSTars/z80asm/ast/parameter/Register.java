package org.eaSTars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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

	@Getter
	private final String value;

}
