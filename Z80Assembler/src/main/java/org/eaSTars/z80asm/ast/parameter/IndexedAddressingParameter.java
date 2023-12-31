package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IndexedAddressingParameter extends AddressingParameter {

  private final RegisterPair registerPair;

  private final ExpressionParameter displacement;

  @Override
  public String getAssembly() {
    return String.format("[%s+%s]", registerPair.getValue(), displacement.getAssembly());
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof IndexedAddressingParameter
        && ((registerPair == null && ((IndexedAddressingParameter) obj).getRegisterPair() == null)
        || (registerPair != null && registerPair == ((IndexedAddressingParameter) obj).getRegisterPair()))
        && ((displacement == null && ((IndexedAddressingParameter) obj).getDisplacement() == null)
        || (displacement != null && displacement.equals(((IndexedAddressingParameter) obj).getDisplacement())));
  }
  
}
