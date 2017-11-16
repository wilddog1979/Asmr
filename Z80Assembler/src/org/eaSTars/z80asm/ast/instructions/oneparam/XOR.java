package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class XOR extends SUBANDXORORCP {

	@Override
	public String getMnemonic() {
		return "XOR";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte)0xa8, (byte)0xee);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xae, 0x00};
	}

}
