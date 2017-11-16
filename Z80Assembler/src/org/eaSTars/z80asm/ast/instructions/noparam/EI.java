package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class EI extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "EI";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xfb};
	}

}
