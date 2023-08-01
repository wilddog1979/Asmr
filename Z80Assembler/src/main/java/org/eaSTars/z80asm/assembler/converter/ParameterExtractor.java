package org.eaSTars.z80asm.assembler.converter;

@FunctionalInterface
public interface ParameterExtractor<T> {

	T extract(T instruction, byte[] values);
	
}
