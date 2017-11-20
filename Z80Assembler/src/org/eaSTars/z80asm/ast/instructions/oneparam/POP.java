package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.parameter.Parameter;

public class POP extends PUSHPOP {

	public POP() {
	}
	
	public POP(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "POP";
	}
	
	@Override
	public byte[] getOpcode() {
		return getOpcode((byte) 0xc1);
	}
	
	@Override
	protected byte[] getIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xe1};
	}

}
