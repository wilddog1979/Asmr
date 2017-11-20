package org.eaSTars.z80asm.assembler.converter;

public class OpcodeMask {

	private byte[] mask;
	
	private byte[] value;
	
	public OpcodeMask(byte[] mask, byte[] value) {
		this.mask = mask;
		this.value = value;
	}

	public byte[] getMask() {
		return mask;
	}

	public byte[] getValue() {
		return value;
	}
}
