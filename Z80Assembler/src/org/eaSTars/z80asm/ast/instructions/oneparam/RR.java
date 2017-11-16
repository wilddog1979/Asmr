package org.eaSTars.z80asm.ast.instructions.oneparam;

public class RR extends BitRotating {

	@Override
	public String getMnemonic() {
		return "RR";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xca, 0x18};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x1e};
	}

}
