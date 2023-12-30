package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.RL;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.RLContext;

public class RLVisitor extends BitRotatingVisitor<RL, RLContext> {

  @Override
  protected RL getInstruction() {
    return new RL();
  }
  
  @Override
  protected InstructionBitRotatingparametersContext getInstructionParameters(RLContext ctx) {
    return ctx.instructionBitRotatingparameters();
  }

}
