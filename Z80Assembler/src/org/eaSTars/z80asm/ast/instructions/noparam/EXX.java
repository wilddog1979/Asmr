package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class EXX extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "EXX";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xd9};
	}

}
