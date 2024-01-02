package org.eastars.z80asm.assembler.converter;

import lombok.*;

import java.util.Arrays;

@Getter
@Builder
@AllArgsConstructor
public class MaskedOpcode<T2> {

  private byte[] mask;

  private byte[] value;

  private ParameterExtractor<T2> extractor;

  @Setter
  private Class<? extends T2> instruction;

  public MaskedOpcode(byte[] mask, byte[] value, ParameterExtractor<T2> extractor) {
    this.mask = mask;
    this.value = value;
    this.extractor = extractor;
  }

  public MaskedOpcode(byte[] value) {
    this(new byte[value.length], value, null);
    Arrays.fill(mask, (byte) 0xff);
  }
  
}
