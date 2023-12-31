package org.eastars.z80asm.ast.instructions.noparam;

import org.eastars.z80asm.ast.instructions.NoParameterInstruction;

public class RETI extends NoParameterInstruction {

  @Override
  public String getMnemonic() {
    return "RETI";
  }

}
