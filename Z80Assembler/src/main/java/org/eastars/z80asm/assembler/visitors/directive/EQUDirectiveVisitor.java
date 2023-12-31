package org.eastars.z80asm.assembler.visitors.directive;

import lombok.Getter;
import org.eastars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eastars.z80asm.ast.directives.EQU;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.EQUContext;

@Getter
public class EQUDirectiveVisitor extends Z80AssemblerBaseVisitor<EQU> {

  private final ExpressionVisitor expressionVisitor = new ExpressionVisitor();

  @Override
  public EQU visitEQU(EQUContext ctx) {
    return new EQU(ctx.LABEL().getText(), expressionVisitor.visitExpression(ctx.expression()));
  }

}
