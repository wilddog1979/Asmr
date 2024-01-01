package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterIndirectAddressingParameter extends AddressingParameter {

  private final RegisterPair registerPair;

  @Override
  public String getAssembly() {
    return String.format("[%s]", registerPair.getValue());
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof RegisterIndirectAddressingParameter
        && ((registerPair == null && ((RegisterIndirectAddressingParameter) obj).getRegisterPair() == null)
        || (registerPair != null && registerPair == ((RegisterIndirectAddressingParameter) obj).getRegisterPair()));
  }

}
