package org.eaSTars.z80asm.ast.instructions.twoparam;

public class BIT extends BITRESSET {

	@Override
	public String getMnemonic() {
		return "BIT";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, 0x40};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, 0x46};
	}

}
