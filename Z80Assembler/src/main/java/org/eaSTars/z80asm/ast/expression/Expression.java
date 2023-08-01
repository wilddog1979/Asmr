package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.assember.CompilationContext;

public interface Expression {

	int evaluate(CompilationContext compilationContext);
	
	String getAssembly();
	
}
