package org.eaSTars.z80asm.assembler.converter;

class MaskedOpcode<T2> {
	protected Class<? extends T2> instruction;
	
	protected byte[] mask;
	
	protected byte[] value;
	
	protected ParameterExtractor<T2> extractor;
	
	public MaskedOpcode(byte[] mask, byte[] value, ParameterExtractor<T2> extractor) {
		this(mask, value);
		this.extractor = extractor;
	}
	
	public MaskedOpcode(byte[] mask, byte[] value) {
		this.mask = mask;
		this.value = value;
	}
	
	public MaskedOpcode(byte[] value) {
		this.mask = new byte[value.length];
		for (int i = 0; i < mask.length; ++i) {
			mask[i] = (byte) 0xff;
		}
		this.value = value;
	}
	
}