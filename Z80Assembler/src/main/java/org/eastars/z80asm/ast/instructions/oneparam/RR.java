package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class RR extends OneParameterInstruction {

  public RR() {
  }

  public RR(Parameter parameter) {
    setParameter(parameter);
  }

  @Override
  public String getMnemonic() {
    return "RR";
  }

}
