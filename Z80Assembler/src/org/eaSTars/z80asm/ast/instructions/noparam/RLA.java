package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RLA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RLA";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x17};
	}

}
