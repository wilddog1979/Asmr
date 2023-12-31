package org.eastars.z80asm.ast.instructions.noparam;

import org.eastars.z80asm.ast.instructions.NoParameterInstruction;

public class OUTD extends NoParameterInstruction {

  @Override
  public String getMnemonic() {
    return "OUTD";
  }

}
