package org.eastars.z80asm.assembler.visitors;

import org.eastars.z80asm.ast.expression.ConstantValueExpression;
import org.eastars.z80asm.ast.expression.Expression;
import org.eastars.z80asm.ast.expression.OneParameterExpression;
import org.eastars.z80asm.ast.expression.TwoOperandExpression;
import org.eastars.z80asm.ast.parameter.ConstantValueParameter;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.*;

import static org.eastars.z80asm.utilities.Utilities.defaultNull;
import static org.eastars.z80asm.utilities.Utilities.defaultValue;

public class ExpressionVisitor extends Z80AssemblerBaseVisitor<Expression> {

  @Override
  public Expression visitExpression(ExpressionContext ctx) {
    return defaultValue(
        ctx.or,
        o -> new TwoOperandExpression(
            visitBitwisexorExpression(ctx.left),
            TwoOperandExpression.Operation.OR,
            visitBitwisexorExpression(ctx.right)),
        () -> visitBitwisexorExpression(ctx.left));
  }

  @Override
  public Expression visitBitwisexorExpression(BitwisexorExpressionContext ctx) {
    return defaultValue(
        ctx.xor,
        x -> new TwoOperandExpression(
            visitBitwiseandExpression(ctx.left),
            TwoOperandExpression.Operation.XOR,
            visitBitwiseandExpression(ctx.right)),
        () -> visitBitwiseandExpression(ctx.left));
  }

  @Override
  public Expression visitBitwiseandExpression(BitwiseandExpressionContext ctx) {
    return defaultValue(
        ctx.and,
        a -> new TwoOperandExpression(
            visitBitwiseshiftExpression(ctx.left),
            TwoOperandExpression.Operation.AND,
            visitBitwiseshiftExpression(ctx.right)),
        () -> visitBitwiseshiftExpression(ctx.left)
    );
  }

  @Override
  public Expression visitBitwiseshiftExpression(BitwiseshiftExpressionContext ctx) {
    return defaultValue(
        ctx.shl,
        s -> new TwoOperandExpression(
            visitAdditiveExpression(ctx.left),
            TwoOperandExpression.Operation.SHL,
            visitAdditiveExpression(ctx.right)),
        () -> defaultValue(
            ctx.shr,
            s -> new TwoOperandExpression(
                visitAdditiveExpression(ctx.left),
                TwoOperandExpression.Operation.SHR,
                visitAdditiveExpression(ctx.right)),
            () -> visitAdditiveExpression(ctx.left)
        )
    );
  }

  @Override
  public Expression visitAdditiveExpression(AdditiveExpressionContext ctx) {
    return defaultValue(
        ctx.plus,
        p -> new TwoOperandExpression(
            visitMultiplicativeExpression(ctx.left),
            TwoOperandExpression.Operation.PLUS,
            visitMultiplicativeExpression(ctx.right)),
        () -> defaultValue(
            ctx.minus,
            m -> new TwoOperandExpression(
                visitMultiplicativeExpression(ctx.left),
                TwoOperandExpression.Operation.MINUS,
                visitMultiplicativeExpression(ctx.right)),
            () -> visitMultiplicativeExpression(ctx.left)
        )
    );
  }

  @Override
  public Expression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
    return defaultValue(
        ctx.mul,
        m -> new TwoOperandExpression(
            visitUnaryExpression(ctx.left),
            TwoOperandExpression.Operation.STAR,
            visitUnaryExpression(ctx.right)),
        () -> defaultValue(
            ctx.div,
            d -> new TwoOperandExpression(
                visitUnaryExpression(ctx.left),
                TwoOperandExpression.Operation.DIV,
                visitUnaryExpression(ctx.right)),
            () -> visitUnaryExpression(ctx.left)
        )
    );
  }

  @Override
  public Expression visitUnaryExpression(UnaryExpressionContext ctx) {
    return defaultValue(
        ctx.minus,
        m -> new OneParameterExpression(
            OneParameterExpression.Operation.MINUS,
            visitUnaryExpression(ctx.unaryExpression())),
        () -> defaultValue(
            ctx.not,
            n -> new OneParameterExpression(
                OneParameterExpression.Operation.NOT,
                visitUnaryExpression(ctx.unaryExpression())),
            () -> visitPrimaryExpression(ctx.primaryExpression())
        )
    );
  }

  @Override
  public Expression visitPrimaryExpression(PrimaryExpressionContext ctx) {
    return defaultValue(
        ctx.expression(),
        this::visitExpression,
        () -> defaultValue(
            ctx.Hex16Bits(),
            h -> new ConstantValueExpression(new ConstantValueParameter(parseValue(h.getText()))),
            () -> defaultNull(
                ctx.LABEL(),
                l -> new ConstantValueExpression(new ConstantValueParameter(l.getText()))
            )
        ));
  }

  private int parseValue(String value) {
    return Integer.parseInt(value.substring(0, value.length() - 1), 16);
  }
}
