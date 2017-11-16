package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class OTDR extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "OTDR";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, (byte) 0xbb};
	}

}
