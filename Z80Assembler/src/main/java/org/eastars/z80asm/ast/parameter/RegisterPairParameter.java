package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class RegisterPairParameter extends Parameter {

  private final RegisterPair registerPair;

  @Override
  public String getAssembly() {
    return registerPair.getValue();
  }
  
}
