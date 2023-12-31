package org.eastars.z80asm.ast.instructions.twoparam;

import lombok.NoArgsConstructor;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

@NoArgsConstructor
public class RES extends TwoParameterInstruction {

  public RES(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }

  @Override
  public String getMnemonic() {
    return "RES";
  }

}
