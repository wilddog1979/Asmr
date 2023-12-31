package org.eastars.z80asm.ast.instructions.oneparam;

import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class XOR extends OneParameterInstruction {

  public XOR() {
  }

  public XOR(Parameter parameter) {
    setParameter(parameter);
  }

  @Override
  public String getMnemonic() {
    return "XOR";
  }

}
