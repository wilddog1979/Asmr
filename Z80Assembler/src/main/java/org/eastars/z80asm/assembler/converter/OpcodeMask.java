package org.eastars.z80asm.assembler.converter;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
class OpcodeMask<T> {

  protected final byte[] mask;
  
  protected final byte[] value;
  
  protected ParameterExtractor<T> parameterExtractor;
  
}
