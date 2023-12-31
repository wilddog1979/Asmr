package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class SUB extends OneParameterInstruction {

  public SUB() {
  }
  
  public SUB(Parameter parameter) {
    setParameter(parameter);
  }
  
  @Override
  public String getMnemonic() {
    return "SUB";
  }

}
