package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AssemblerLine {

  protected String label;

  protected String comment;

}
