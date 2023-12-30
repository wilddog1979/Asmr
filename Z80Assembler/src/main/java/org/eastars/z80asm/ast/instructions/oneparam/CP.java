package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class CP extends OneParameterInstruction {

  public CP() {
  }

  public CP(Parameter parameter) {
    setParameter(parameter);
  }

  @Override
  public String getMnemonic() {
    return "CP";
  }

}
