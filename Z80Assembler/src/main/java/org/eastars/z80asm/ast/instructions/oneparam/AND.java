package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class AND extends OneParameterInstruction {

  public AND() {
  }
  
  public AND(Parameter parameter) {
    setParameter(parameter);
  }
  
  @Override
  public String getMnemonic() {
    return "AND";
  }

}
