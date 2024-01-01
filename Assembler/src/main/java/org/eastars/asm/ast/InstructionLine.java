package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructionLine extends AssemblerLine {

  private Instruction instruction;

  @Override
  public String toString() {
    return String.format("%s%s%s",
        label != null ? label + ": " : "",
            instruction != null ? instruction.getAssembly() + " " : "",
                comment != null ? comment : "");
  }

}
