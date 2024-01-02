package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class ImmediateAddressingParameter extends AddressingParameter {

  private final ExpressionParameter value;

  @Override
  public String getAssembly() {
    return String.format("[%s]", value.getAssembly());
  }

}
