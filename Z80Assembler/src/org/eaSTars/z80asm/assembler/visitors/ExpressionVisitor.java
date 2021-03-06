package org.eaSTars.z80asm.assembler.visitors;

import java.util.Optional;

import org.eaSTars.z80asm.ast.expression.ConstantValueExpression;
import org.eaSTars.z80asm.ast.expression.Expression;
import org.eaSTars.z80asm.ast.expression.OneParameterExpression;
import org.eaSTars.z80asm.ast.expression.TwoOperandExpression;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.AdditiveExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.BitwiseandExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.BitwiseshiftExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.BitwisexorExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.MultiplicativeExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.PrimaryExpressionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.UnaryExpressionContext;

public class ExpressionVisitor extends Z80AssemblerBaseVisitor<Expression> {

	@Override
	public Expression visitExpression(ExpressionContext ctx) {
		return Optional.ofNullable(ctx.or).map(b -> {
			return (Expression)new TwoOperandExpression(visitBitwisexorExpression(ctx.left), TwoOperandExpression.Operation.OR, visitBitwisexorExpression(ctx.right));
		}).orElseGet(() -> visitBitwisexorExpression(ctx.left));
	}
	
	@Override
	public Expression visitBitwisexorExpression(BitwisexorExpressionContext ctx) {
		return Optional.ofNullable(ctx.xor).map(b -> {
			return (Expression)new TwoOperandExpression(visitBitwiseandExpression(ctx.left), TwoOperandExpression.Operation.XOR, visitBitwiseandExpression(ctx.right));
		}).orElseGet(() -> visitBitwiseandExpression(ctx.left));
	}
	
	@Override
	public Expression visitBitwiseandExpression(BitwiseandExpressionContext ctx) {
		return Optional.ofNullable(ctx.and).map(b -> {
			return (Expression)new TwoOperandExpression(visitBitwiseshiftExpression(ctx.left), TwoOperandExpression.Operation.AND, visitBitwiseshiftExpression(ctx.right));
		}).orElseGet(() -> visitBitwiseshiftExpression(ctx.left));
	}
	
	@Override
	public Expression visitBitwiseshiftExpression(BitwiseshiftExpressionContext ctx) {
		return Optional.ofNullable(ctx.shl).map(l -> (Expression)new TwoOperandExpression(visitAdditiveExpression(ctx.left), TwoOperandExpression.Operation.SHL, visitAdditiveExpression(ctx.right)))
				.orElseGet(() -> Optional.ofNullable(ctx.shr).map(r -> (Expression)new TwoOperandExpression(visitAdditiveExpression(ctx.left), TwoOperandExpression.Operation.SHR, visitAdditiveExpression(ctx.right)))
						.orElseGet(() -> visitAdditiveExpression(ctx.left)));
	}

	@Override
	public Expression visitAdditiveExpression(AdditiveExpressionContext ctx) {
		return Optional.ofNullable(ctx.plus).map(p -> (Expression)new TwoOperandExpression(visitMultiplicativeExpression(ctx.left), TwoOperandExpression.Operation.PLUS, visitMultiplicativeExpression(ctx.right)))
				.orElseGet(() -> Optional.ofNullable(ctx.minus).map(m -> (Expression)new TwoOperandExpression(visitMultiplicativeExpression(ctx.left), TwoOperandExpression.Operation.MINUS, visitMultiplicativeExpression(ctx.right)))
						.orElseGet(() -> visitMultiplicativeExpression(ctx.left)));
	}
	
	@Override
	public Expression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
		return Optional.ofNullable(ctx.mul).map(m -> (Expression)new TwoOperandExpression(visitUnaryExpression(ctx.left), TwoOperandExpression.Operation.STAR, visitUnaryExpression(ctx.right)))
				.orElseGet(() -> Optional.ofNullable(ctx.div).map(d -> (Expression)new TwoOperandExpression(visitUnaryExpression(ctx.left), TwoOperandExpression.Operation.DIV, visitUnaryExpression(ctx.right)))
						.orElseGet(() -> visitUnaryExpression(ctx.left)));
	}
	
	@Override
	public Expression visitUnaryExpression(UnaryExpressionContext ctx) {
		return Optional.ofNullable(ctx.minus).map(m -> (Expression)new OneParameterExpression(OneParameterExpression.Operation.MINUS, visitUnaryExpression(ctx.unaryExpression())))
				.orElseGet(() -> Optional.ofNullable(ctx.not).map(n -> (Expression)new OneParameterExpression(OneParameterExpression.Operation.NOT, visitUnaryExpression(ctx.unaryExpression())))
						.orElseGet(() -> visitPrimaryExpression(ctx.primaryExpression())));
	}
	
	@Override
	public Expression visitPrimaryExpression(PrimaryExpressionContext ctx) {
		return Optional.ofNullable(ctx.expression()).map(e -> visitExpression(e))
				.orElseGet(() -> Optional.ofNullable(ctx.Hex16Bits()).map(h -> (Expression)new ConstantValueExpression(new ConstantValueParameter(parseValue(h.getText()))))
						.orElseGet(() -> Optional.ofNullable(ctx.LABEL()).map(l -> (Expression)new ConstantValueExpression(new ConstantValueParameter(l.getText())))
								.orElseGet(() -> null)));
	}
	
	private int parseValue(String value) {
		return Integer.parseInt(value.substring(0, value.length() - 1), 16); 
	}
}
