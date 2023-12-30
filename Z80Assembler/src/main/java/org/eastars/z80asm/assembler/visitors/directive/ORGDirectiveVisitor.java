package org.eastars.z80asm.assembler.visitors.directive;

import org.eastars.asm.ast.directives.Org;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.ORGContext;

public class ORGDirectiveVisitor extends Z80AssemblerBaseVisitor<Org> {

  protected int parseHexValue(String value) {
    return Integer.parseInt(value.substring(0, value.length() - 1), 16);
  }

  public Org visitORG(ORGContext ctx) {
    return new Org(parseHexValue(ctx.Hex16Bits().getText()));
  };
}
