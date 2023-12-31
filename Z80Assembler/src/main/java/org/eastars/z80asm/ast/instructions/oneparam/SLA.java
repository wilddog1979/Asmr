package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class SLA extends OneParameterInstruction {

  public SLA() { 
  }
  
  public SLA(Parameter parameter) {
    setParameter(parameter);
  }
  
  @Override
  public String getMnemonic() {
    return "SLA";
  }

}
