package org.eaSTars.z80asm.ast.instructions.oneparam;

public class RL extends BitRotating {

	@Override
	public String getMnemonic() {
		return "RL";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xca, 0x10};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x16};
	}

}
