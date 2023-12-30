package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;

public abstract class AssemblerLine {

  @Getter
  @Setter
  protected String label;

  @Getter
  @Setter
  protected String comment;

}
