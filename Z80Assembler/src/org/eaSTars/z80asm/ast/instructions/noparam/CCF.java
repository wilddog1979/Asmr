package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class CCF extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "CCF";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x3f};
	}

}
