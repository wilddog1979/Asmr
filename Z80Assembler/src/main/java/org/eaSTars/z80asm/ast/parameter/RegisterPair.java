package org.eaSTars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RegisterPair {
	BC("BC"),
	DE("DE"),
	HL("HL"),
	SP("SP"),
	AF("AF"),
	IX("IX"),
	IY("IY"),
	AFMarked("AF'");

	@Getter
	private final String value;
	
}
