package org.eastars.z80asm.ast.instructions.twoparam;

import lombok.NoArgsConstructor;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

@NoArgsConstructor
public class SET extends TwoParameterInstruction {
  
  public SET(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }
  
  @Override
  public String getMnemonic() {
    return "SET";
  }

}
