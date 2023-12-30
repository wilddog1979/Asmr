package org.eastars.z80asm.ast.instructions.twoparam;

import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class JP extends TwoParameterInstruction {

  public JP() {
  }
  
  public JP(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }
  
  @Override
  public String getMnemonic() {
    return "JP";
  }
  
  @Override
  public String getAssembly() {
    Parameter target = getTarget();
    return String.format("%s%s %s", getMnemonic(), target != null ? target.getAssembly() : "", getSource().getAssembly());
  }

}
