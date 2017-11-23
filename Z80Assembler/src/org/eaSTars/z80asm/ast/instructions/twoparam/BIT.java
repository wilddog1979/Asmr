package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.parameter.Parameter;

public class BIT extends BITRESSET {

	public BIT() {
	}
	
	public BIT(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "BIT";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, 0x40};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, 0x46};
	}

}
