package org.eastars.z80asm.assembler.visitors;

import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser;
import org.eastars.z80asm.parser.Z80AssemblerParser.Hex16bitsContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.Hex3bitsContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.Hex8bitsContext;

public class HexValueVisitor extends Z80AssemblerBaseVisitor<ExpressionParameter> {

  private final ExpressionVisitor expressionVisitor = new ExpressionVisitor();

  private ExpressionParameter createExpressionParameter(
      Z80AssemblerParser.ExpressionContext expressionContext, int expectedBitCount) {
    return new ExpressionParameter(expressionVisitor.visitExpression(expressionContext), expectedBitCount);
  }

  @Override
  public ExpressionParameter visitHex16bits(Hex16bitsContext ctx) {
    return createExpressionParameter(ctx.expression(), 16);
  }

  @Override
  public ExpressionParameter visitHex8bits(Hex8bitsContext ctx) {
    return createExpressionParameter(ctx.expression(), 8);
  }

  @Override
  public ExpressionParameter visitHex3bits(Hex3bitsContext ctx) {
    return createExpressionParameter(ctx.expression(), 3);
  }

}
