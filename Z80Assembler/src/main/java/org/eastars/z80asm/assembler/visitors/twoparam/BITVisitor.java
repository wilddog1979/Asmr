package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.ast.instructions.twoparam.BIT;
import org.eastars.z80asm.parser.Z80AssemblerParser.BITContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBITRESSETparametersContext;

public class BITVisitor extends BITRESSETVisitor<BIT, BITContext> {

  @Override
  protected BIT getInstruction() {
    return new BIT();
  }
  
  @Override
  protected InstructionBITRESSETparametersContext getInstructionParameters(BITContext ctx) {
    return ctx.instructionBITRESSETparameters();
  }

}
