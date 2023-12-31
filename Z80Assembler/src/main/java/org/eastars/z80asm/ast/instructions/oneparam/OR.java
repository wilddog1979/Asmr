package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class OR extends OneParameterInstruction {

  public OR() {
  }

  public OR(Parameter parameter) {
    setParameter(parameter);
  }

  @Override
  public String getMnemonic() {
    return "OR";
  }

}
