package org.eastars.z80asm.assembler.visitors;

import org.eastars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eastars.z80asm.ast.parameter.RegisterPair;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.IndexedReferenceContext;

public class IndexedReferenceVisitor extends Z80AssemblerBaseVisitor<IndexedAddressingParameter> {

  private final HexValueVisitor hexValueVisitor = new HexValueVisitor();

  @Override
  public IndexedAddressingParameter visitIndexedReference(IndexedReferenceContext ctx) {
    return new IndexedAddressingParameter(
      ctx.IX != null ? RegisterPair.IX : RegisterPair.IY,
      hexValueVisitor.visitHex8bits(ctx.hex8bits()));
  }
}
