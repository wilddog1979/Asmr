package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RETI extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RETI";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, 0x4d};
	}

}
