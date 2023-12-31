package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class RRC extends OneParameterInstruction {

  public RRC() {
  }

  public RRC(Parameter parameter) {
    setParameter(parameter);
  }

  @Override
  public String getMnemonic() {
    return "RRC";
  }

}
