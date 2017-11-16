package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class AND extends SUBANDXORORCP {

	@Override
	public String getMnemonic() {
		return "AND";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte)0xa0, (byte)0xe6);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xa6, 0x00};
	}

}
