package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class CPI extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "CPI";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, (byte) 0xa1};
	}

}
