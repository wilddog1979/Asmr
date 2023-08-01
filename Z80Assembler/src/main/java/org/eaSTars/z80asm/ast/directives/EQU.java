package org.eaSTars.z80asm.ast.directives;

import lombok.Getter;
import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.z80asm.ast.Z80Directive;
import org.eaSTars.z80asm.ast.expression.Expression;

public class EQU extends Z80Directive {

	@Getter
	private Expression expression;
	
	public EQU(String label, Expression expression) {
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
