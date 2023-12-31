package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.RLC;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.RLCContext;

public class RLCVisitor extends BitRotatingVisitor<RLC, RLCContext> {

  @Override
  protected RLC getInstruction() {
    return new RLC();
  }

  @Override
  protected InstructionBitRotatingparametersContext getInstructionParameters(RLCContext ctx) {
    return ctx.instructionBitRotatingparameters();
  }

}
