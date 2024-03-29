package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class SRL extends OneParameterInstruction {

  public SRL() {
  }
  
  public SRL(Parameter parameter) {
    setParameter(parameter);
  }
  
  @Override
  public String getMnemonic() {
    return "SRL";
  }

}
