package org.eastars.z80asm.ast;

import org.eastars.asm.ast.Instruction;

public abstract class Z80Instruction implements Instruction {
  
  @Override
  public String toString() {
    return getAssembly();
  }
  
}
