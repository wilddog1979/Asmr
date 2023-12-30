package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.DEC;
import org.eastars.z80asm.parser.Z80AssemblerParser.DECContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionINCDECparametersContext;

public class DECVisitor extends INCDECVisitor<DEC, DECContext> {

  @Override
  protected DEC getInstruction() {
    return new DEC();
  }
  
  @Override
  protected InstructionINCDECparametersContext getInstructionParameters(DECContext ctx) {
    return ctx.instructionINCDECparameters();
  }
  
}
