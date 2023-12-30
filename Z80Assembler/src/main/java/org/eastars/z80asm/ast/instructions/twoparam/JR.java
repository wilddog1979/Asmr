package org.eastars.z80asm.ast.instructions.twoparam;

import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;

public class JR extends TwoParameterInstruction {

  public JR() {
  }

  public JR(Parameter target, Parameter source) {
    setTarget(target);
    setSource(source);
  }

  @Override
  public String getMnemonic() {
    return "JR";
  }

  @Override
  public String getAssembly() {
    Parameter target = getTarget();
    return String.format(
        "%s%s %s",
        getMnemonic(),
        target == null ? "" : target.getAssembly(),
            getSource().getAssembly());
  }

}
