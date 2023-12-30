package org.eastars.z80asm.ast.instructions.twoparam;

import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class ADC extends TwoParameterInstruction {

  public ADC() {
  }
  
  public ADC(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }
  
  @Override
  public String getMnemonic() {
    return "ADC";
  }

}