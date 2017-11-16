package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RRCA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RRCA";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x0f};
	}

}
