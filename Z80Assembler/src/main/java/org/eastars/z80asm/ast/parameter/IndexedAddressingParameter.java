package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class IndexedAddressingParameter extends AddressingParameter {

  private final RegisterPair registerPair;

  private final ExpressionParameter displacement;

  @Override
  public String getAssembly() {
    return String.format("[%s+%s]", registerPair.getValue(), displacement.getAssembly());
  }
  
}
