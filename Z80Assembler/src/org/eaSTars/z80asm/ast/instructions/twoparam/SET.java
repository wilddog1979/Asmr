package org.eaSTars.z80asm.ast.instructions.twoparam;

public class SET extends BITRESSET {

	@Override
	public String getMnemonic() {
		return "SET";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xcb, (byte) 0xc0};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0xc6};
	}

}
