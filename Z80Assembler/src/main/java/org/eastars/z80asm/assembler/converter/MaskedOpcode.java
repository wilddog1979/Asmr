package org.eastars.z80asm.assembler.converter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public class MaskedOpcode<T2> {

  @Setter
  private Class<? extends T2> instruction;

  private final byte[] mask;

  private final byte[] value;

  private final ParameterExtractor<T2> extractor;
  
  public MaskedOpcode(byte[] value) {
    this(new byte[value.length], value, null);
    Arrays.fill(mask, (byte) 0xff);
  }
  
}
