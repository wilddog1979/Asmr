package org.eaSTars.z80asm.ast.instructions.oneparam;

public class PUSH extends PUSHPOP {

	@Override
	public String getMnemonic() {
		return "PUSH";
	}
	
	@Override
	public byte[] getOpcode() {
		return getOpcode((byte) 0xc5);
	}
	
	@Override
	protected byte[] getIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xe5};
	}

}
