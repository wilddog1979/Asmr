package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;
import org.eastars.asm.assember.CompilationContext;

@Setter
@Getter
public abstract class Directive {

  protected String label;

  public abstract int evaluate(CompilationContext compilationContext);
  
}
