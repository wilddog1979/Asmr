package org.eastars.z80asm.ast.instructions.noparam;

import org.eastars.z80asm.ast.instructions.NoParameterInstruction;

public class CPL extends NoParameterInstruction {

  @Override
  public String getMnemonic() {
    return "CPL";
  }

}
