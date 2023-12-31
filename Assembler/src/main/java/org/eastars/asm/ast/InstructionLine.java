package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;

public class InstructionLine extends AssemblerLine {

  @Getter
  @Setter
  private Instruction instruction;

  @Override
  public String toString() {
    return String.format("%s%s%s",
        label != null ? label + ": " : "",
            instruction != null ? instruction.getAssembly() + " " : "",
                comment != null ? comment : "");
  }

}
