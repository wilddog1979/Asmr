package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RLCA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RLCA";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x07};
	}

}
