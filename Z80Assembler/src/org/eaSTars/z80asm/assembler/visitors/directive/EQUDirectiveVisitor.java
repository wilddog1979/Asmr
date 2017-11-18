package org.eaSTars.z80asm.assembler.visitors.directive;

import org.eaSTars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eaSTars.z80asm.ast.directives.EQU;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.EQUContext;

public class EQUDirectiveVisitor extends Z80AssemblerBaseVisitor<EQU> {

	private ExpressionVisitor expressionVisitor;
	
	@Override
	public EQU visitEQU(EQUContext ctx) {
		return new EQU(ctx.LABEL().getText(), getExpressionVisitor().visitExpression(ctx.expression()));
	}

	public ExpressionVisitor getExpressionVisitor() {
		if (expressionVisitor == null) {
			expressionVisitor = new ExpressionVisitor();
		}
		return expressionVisitor;
	}
}
