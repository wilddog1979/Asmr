package org.eaSTars.z80asm.ast.instructions.oneparam;

public class SRL extends BitRotating {

	@Override
	public String getMnemonic() {
		return "SRL";
	}
	
	@Override
	protected byte[] getRH() {
		return new byte[] {(byte) 0xca, 0x38};
	}
	
	@Override
	protected byte[] getRefIXIY() {
		return new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x3e};
	}

}
