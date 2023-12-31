package org.eastars.z80asm.assembler.converter;

import java.util.Arrays;

public class MaskedOpcode<T2> {

  protected Class<? extends T2> instruction;
  
  protected byte[] mask;
  
  protected byte[] value;
  
  protected ParameterExtractor<T2> extractor;
  
  public MaskedOpcode(byte[] mask, byte[] value, ParameterExtractor<T2> extractor) {
    this.mask = mask;
    this.value = value;
    this.extractor = extractor;
  }
  
  public MaskedOpcode(byte[] value) {
    this.mask = new byte[value.length];
    Arrays.fill(mask, (byte) 0xff);
    this.value = value;
  }
  
}