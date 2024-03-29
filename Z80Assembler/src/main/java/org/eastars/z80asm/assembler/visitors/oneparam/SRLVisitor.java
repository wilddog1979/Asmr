package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.SRL;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.SRLContext;

public class SRLVisitor extends BitRotatingVisitor<SRL, SRLContext> {

  @Override
  protected SRL getInstruction() {
    return new SRL();
  }
  
  @Override
  protected InstructionBitRotatingparametersContext getInstructionParameters(SRLContext ctx) {
    return ctx.instructionBitRotatingparameters();
  }

}
