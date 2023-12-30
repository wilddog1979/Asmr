package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImmediateAddressingParameter extends AddressingParameter {

  @Getter
  private final ExpressionParameter value;

  @Override
  public String getAssembly() {
    return String.format("[%s]", value.getAssembly());
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ImmediateAddressingParameter &&
        ((value == null && ((ImmediateAddressingParameter)obj).value == null) ||
            (value != null && value.equals(((ImmediateAddressingParameter)obj).value)));
  }

}
