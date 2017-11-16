package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class DEC extends INCDEC {

	@Override
	public String getMnemonic() {
		return "DEC";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte)0x0b, (byte)0x05);
	}
	
	@Override
	protected byte[] getIXIY() {
		return new byte[] {(byte) 0xdd, 0x2b};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xdd, 0x35, 0x00};
	}

}
