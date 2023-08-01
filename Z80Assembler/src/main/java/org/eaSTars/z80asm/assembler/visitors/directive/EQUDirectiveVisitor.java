package org.eaSTars.z80asm.assembler.visitors.directive;

import lombok.Getter;
import org.eaSTars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eaSTars.z80asm.ast.directives.EQU;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.EQUContext;

public class EQUDirectiveVisitor extends Z80AssemblerBaseVisitor<EQU> {

	@Getter
	private ExpressionVisitor expressionVisitor = new ExpressionVisitor();
	
	@Override
	public EQU visitEQU(EQUContext ctx) {
		return new EQU(ctx.LABEL().getText(), expressionVisitor.visitExpression(ctx.expression()));
	}

}
