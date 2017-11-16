package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class CPDR extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "CPDR";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, (byte) 0xb9};
	}

}
