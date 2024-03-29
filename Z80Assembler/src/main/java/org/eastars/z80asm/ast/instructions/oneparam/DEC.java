package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class DEC extends OneParameterInstruction {

  public DEC() {
  }
  
  public DEC(Parameter parameter) {
    setParameter(parameter);
  }
  
  @Override
  public String getMnemonic() {
    return "DEC";
  }

}
