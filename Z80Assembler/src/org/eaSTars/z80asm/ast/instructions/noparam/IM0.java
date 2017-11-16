package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class IM0 extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "IM0";
	}
	
	@Override
	public byte[] getOpcode() {
		return new byte[] {(byte) 0xed, 0x46};
	}

}
