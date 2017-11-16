package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class SBC extends ADCSBC {

	@Override
	public String getMnemonic() {
		return "SBC";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte) 0x98, (byte) 0xde);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0x9e, 0x00};
	}
	
	@Override
	protected byte[] getSS() {
		return new byte[] {(byte) 0xed, 0x42};
	}

}
