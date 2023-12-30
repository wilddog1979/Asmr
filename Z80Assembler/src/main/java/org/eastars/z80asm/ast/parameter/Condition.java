package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Condition {

  NZ("NZ", (byte)0),
  Z("Z", (byte)1),
  NC("NC", (byte)2),
  C("C", (byte)3),
  PO("PO", (byte)4),
  PE("PE", (byte)5),
  P("P", (byte)6),
  M("M", (byte)7);

  @Getter
  private final String value;

  @Getter
  private final byte opcode;

}
