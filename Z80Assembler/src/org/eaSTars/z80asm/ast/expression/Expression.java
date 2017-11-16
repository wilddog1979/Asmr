package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.ast.CompilationUnit;

public interface Expression {

	public int evaluate(CompilationUnit compilationUnit);
	
	public String getAssembly();
	
}
