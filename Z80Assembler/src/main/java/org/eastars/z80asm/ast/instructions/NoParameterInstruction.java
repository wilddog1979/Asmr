package org.eastars.z80asm.ast.instructions;

import org.eastars.z80asm.ast.Z80Instruction;

public abstract class NoParameterInstruction extends Z80Instruction {

  @Override
  public String getAssembly() {
    return getMnemonic();
  }
  
}
