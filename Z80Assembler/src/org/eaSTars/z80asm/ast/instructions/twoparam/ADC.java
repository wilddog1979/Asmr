package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;

public class ADC extends ADCSBC {

	@Override
	public String getMnemonic() {
		return "ADC";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte) 0x88, (byte) 0xce);
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0x8e, 0x00};
	}
	
	@Override
	protected byte[] getSS() {
		return new byte[] {(byte) 0xed, 0x4a};
	}

}
