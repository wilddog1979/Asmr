package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RRA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RRA";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {0x1f};
	}

}
