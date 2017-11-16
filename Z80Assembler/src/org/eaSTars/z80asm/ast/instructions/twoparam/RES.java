package org.eaSTars.z80asm.ast.instructions.twoparam;

public class RES extends BITRESSET {

	@Override
	public String getMnemonic() {
		return "RES";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, (byte) 0x80};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0x86};
	}

}
