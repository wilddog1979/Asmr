package org.eaSTars.z80asm.ast.instructions.oneparam;

public class SRA extends BitRotating {

	@Override
	public String getMnemonic() {
		return "SRA";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xca, 0x28};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x2e};
	}

}
