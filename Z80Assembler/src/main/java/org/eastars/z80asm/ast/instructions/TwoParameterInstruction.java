package org.eastars.z80asm.ast.instructions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.eastars.z80asm.ast.Z80Instruction;
import org.eastars.z80asm.ast.parameter.Parameter;

@Getter
@Setter
@Accessors(chain = true)
public abstract class TwoParameterInstruction extends Z80Instruction {

  private Parameter target;

  private Parameter source;
  
  @Override
  public String getAssembly() {
    return String.format("%s %s, %s", getMnemonic(), target.getAssembly(), source.getAssembly());
  }
  
}
