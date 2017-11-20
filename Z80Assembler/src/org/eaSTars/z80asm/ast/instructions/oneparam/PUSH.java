package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.parameter.Parameter;

public class PUSH extends PUSHPOP {

	public PUSH() {
	}
	
	public PUSH(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "PUSH";
	}
	
	@Override
	public byte[] getOpcode() {
		return getOpcode((byte) 0xc5);
	}
	
	@Override
	protected byte[] getIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xe5};
	}

}
