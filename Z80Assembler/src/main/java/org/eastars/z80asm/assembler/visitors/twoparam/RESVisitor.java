package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.ast.instructions.twoparam.RES;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBITRESSETparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.RESContext;

public class RESVisitor extends BITRESSETVisitor<RES, RESContext> {

  @Override
  protected RES getInstruction() {
    return new RES();
  }

  @Override
  protected InstructionBITRESSETparametersContext getInstructionParameters(RESContext ctx) {
    return ctx.instructionBITRESSETparameters();
  }

}
