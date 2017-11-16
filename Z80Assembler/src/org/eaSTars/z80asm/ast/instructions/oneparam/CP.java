package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class CP extends SUBANDXORORCP {

	@Override
	public String getMnemonic() {
		return "CP";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte) 0xb8, (byte) 0xfe);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xbe, 0x00};
	}

}
