package org.eastars.z80asm.assembler.visitors.directive;

import lombok.Getter;
import org.eastars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eastars.z80asm.ast.directives.Equ;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.EQUContext;

public class EQUDirectiveVisitor extends Z80AssemblerBaseVisitor<Equ> {

  @Getter
  private ExpressionVisitor expressionVisitor = new ExpressionVisitor();

  @Override
  public Equ visitEQU(EQUContext ctx) {
    return new Equ(ctx.LABEL().getText(), expressionVisitor.visitExpression(ctx.expression()));
  }

}
