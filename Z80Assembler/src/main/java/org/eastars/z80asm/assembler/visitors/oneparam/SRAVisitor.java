package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.SRA;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.SRAContext;

public class SRAVisitor extends BitRotatingVisitor<SRA, SRAContext> {

  @Override
  protected SRA getInstruction() {
    return new SRA();
  }

  @Override
  protected InstructionBitRotatingparametersContext getInstructionParameters(SRAContext ctx) {
    return ctx.instructionBitRotatingparameters();
  }

}
