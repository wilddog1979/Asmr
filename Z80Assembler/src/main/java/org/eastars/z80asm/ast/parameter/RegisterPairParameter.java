package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterPairParameter extends Parameter {

  @Getter
  private final RegisterPair registerPair;

  @Override
  public String getAssembly() {
    return registerPair.getValue();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof RegisterPairParameter &&
        ((registerPair == null && ((RegisterPairParameter)obj).getRegisterPair() == null) ||
            (registerPair != null && registerPair == ((RegisterPairParameter)obj).getRegisterPair()));
  }
  
}
