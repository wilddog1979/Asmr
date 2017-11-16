package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class INI extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "INI";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, (byte) 0xa2};
	}

}
