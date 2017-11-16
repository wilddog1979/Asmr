package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class OTIR extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "OTIR";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, (byte) 0xb3};
	}

}
