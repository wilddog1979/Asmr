package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class DAA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "DAA";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x27};
	}

}
