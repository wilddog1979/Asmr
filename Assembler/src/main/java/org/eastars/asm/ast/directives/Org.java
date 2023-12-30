package org.eastars.asm.ast.directives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.ast.Directive;

@AllArgsConstructor
public class Org extends Directive {

  @Getter
  @Setter
  private Integer value;

  @Override
  public int evaluate(CompilationContext compilationContext) {
    return 0;
  }

  @Override
  public String toString() {
    return String.format("org %xh", getValue());
  }
  
}
