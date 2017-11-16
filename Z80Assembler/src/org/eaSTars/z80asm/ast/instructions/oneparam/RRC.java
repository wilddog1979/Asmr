package org.eaSTars.z80asm.ast.instructions.oneparam;

public class RRC extends BitRotating {

	@Override
	public String getMnemonic() {
		return "RRC";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xca, 0x08};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x0e};
	}

}
