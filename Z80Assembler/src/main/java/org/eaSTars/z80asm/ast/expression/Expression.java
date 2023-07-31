package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.assember.CompilationContext;

public interface Expression {

	public int evaluate(CompilationContext compilationContext);
	
	public String getAssembly();
	
}
