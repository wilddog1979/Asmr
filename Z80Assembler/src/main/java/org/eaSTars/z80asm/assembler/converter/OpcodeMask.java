package org.eaSTars.z80asm.assembler.converter;

class OpcodeMask<T> {

	protected byte[] mask;
	
	protected byte[] value;
	
	protected ParameterExtractor<T> parameterExtractor;
	
	public OpcodeMask(byte[] mask, byte[] value) {
		this.mask = mask;
		this.value = value;
	}
	
	public OpcodeMask(byte[] mask, byte[] value, ParameterExtractor<T> parameterExtractor) {
		this(mask, value);
		this.parameterExtractor = parameterExtractor;
	}
	
}
