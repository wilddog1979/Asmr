package org.eastars.z80asm.ast.expression;

import org.eastars.asm.assember.CompilationContext;

public interface Expression {

  int evaluate(CompilationContext compilationContext);

  String getAssembly();

}
