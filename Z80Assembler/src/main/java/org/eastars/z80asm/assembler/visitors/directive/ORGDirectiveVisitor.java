package org.eastars.z80asm.assembler.visitors.directive;

import org.eastars.asm.ast.directives.ORG;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.ORGContext;

public class ORGDirectiveVisitor extends Z80AssemblerBaseVisitor<ORG> {

  protected int parseHexValue(String value) {
    return Integer.parseInt(value.substring(0, value.length() - 1), 16);
  }

  public ORG visitORG(ORGContext ctx) {
    return new ORG(parseHexValue(ctx.Hex16Bits().getText()));
  }
}
