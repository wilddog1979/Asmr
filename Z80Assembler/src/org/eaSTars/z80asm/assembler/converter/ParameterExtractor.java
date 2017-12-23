package org.eaSTars.z80asm.assembler.converter;

@FunctionalInterface
public interface ParameterExtractor<T> {

	public T extract(T instruction, byte[] values);
	
}
