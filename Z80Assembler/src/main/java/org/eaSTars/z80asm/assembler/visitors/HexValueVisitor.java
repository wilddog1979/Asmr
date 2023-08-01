package org.eaSTars.z80asm.assembler.visitors;

import lombok.Getter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Hex16bitsContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Hex3bitsContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Hex8bitsContext;

public class HexValueVisitor extends Z80AssemblerBaseVisitor<ExpressionParameter> {

	private final ExpressionVisitor expressionVisitor = new ExpressionVisitor();
	
	@Override
	public ExpressionParameter visitHex16bits(Hex16bitsContext ctx) {
		return new ExpressionParameter(expressionVisitor.visitExpression(ctx.expression()), 16);
	}
	
	@Override
	public ExpressionParameter visitHex8bits(Hex8bitsContext ctx) {
		return new ExpressionParameter(expressionVisitor.visitExpression(ctx.expression()), 8);
	}
	
	@Override
	public ExpressionParameter visitHex3bits(Hex3bitsContext ctx) {
		return new ExpressionParameter(expressionVisitor.visitExpression(ctx.expression()), 3);
	}
	
}
