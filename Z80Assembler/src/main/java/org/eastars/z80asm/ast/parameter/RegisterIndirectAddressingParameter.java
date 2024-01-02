package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class RegisterIndirectAddressingParameter extends AddressingParameter {

  private final RegisterPair registerPair;

  @Override
  public String getAssembly() {
    return String.format("[%s]", registerPair.getValue());
  }

}
