package org.eastars.z80asm.ast.instructions.twoparam;

import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class ADD extends TwoParameterInstruction {

  public ADD() {
  }
  
  public ADD(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }
  
  @Override
  public String getMnemonic() {
    return "ADD";
  }

}
