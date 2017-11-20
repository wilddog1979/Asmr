package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class INC extends INCDEC {

	public INC() {
	}
	
	public INC(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "INC";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode(compilationUnit, (byte)0x03, (byte)0x04);
	}
	
	@Override
	protected byte[] getIXIY() {
		return new byte[] {(byte) 0xdd, 0x23};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, 0x34, 0x00};
	}
}
