package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RLD extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RLD";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, 0x6f};
	}

}
