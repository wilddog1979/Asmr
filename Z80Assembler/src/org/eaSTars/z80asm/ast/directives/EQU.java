package org.eaSTars.z80asm.ast.directives;

import org.eaSTars.z80asm.ast.Z80Directive;
import org.eaSTars.z80asm.ast.expression.Expression;

public class EQU extends Z80Directive {

	private Expression expression;
	
	public EQU() {
	}
	
	public EQU(String label, Expression expression) {
		setLabel(label);
		setExpression(expression);
	}

	@Override
	public String toString() {
		return String.format("%s: equ %s", getLabel(), expression.getAssembly());
	}
	
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

}
