package org.eastars.z80asm.ast.directives;

import lombok.Getter;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Directive;
import org.eastars.z80asm.ast.expression.Expression;

public class Equ extends Z80Directive {

  @Getter
  private final Expression expression;

  public Equ(String label, Expression expression) {
    this.label = label;
    this.expression = expression;
  }

  @Override
  public int evaluate(CompilationContext compilationContext) {
    return expression.evaluate(compilationContext);
  }

  @Override
  public String toString() {
    return String.format("%s: equ %s", getLabel(), expression.getAssembly());
  }

}
