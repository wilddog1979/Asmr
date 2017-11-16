package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class SUB extends SUBANDXORORCP {

	@Override
	public String getMnemonic() {
		return "SUB";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte) 0x90, (byte)0xd6);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0x96, 0x00};
	}

}
