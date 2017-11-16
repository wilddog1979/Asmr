package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class IM2 extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "IM2";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, 0x5e};
	}

}
