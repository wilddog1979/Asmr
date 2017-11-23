package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.parameter.Parameter;

public class SET extends BITRESSET {

	public SET() {
	}
	
	public SET(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "SET";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, (byte) 0xc0};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0xc6};
	}

}
