package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RES extends BITRESSET {

	public RES() {
	}
	
	public RES(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "RES";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, (byte) 0x80};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0x86};
	}

}
