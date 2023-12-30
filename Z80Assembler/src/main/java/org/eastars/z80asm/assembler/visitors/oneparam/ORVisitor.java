package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.OR;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.ORContext;

public class ORVisitor extends SUBANDXORORCPVisitor<OR, ORContext> {

  @Override
  protected OR getInstruction() {
    return new OR();
  }

  @Override
  protected InstructionSUBANDXORORCPparametersContext getInstructionParameters(ORContext ctx) {
    return ctx.instructionSUBANDXORORCPparameters();
  }
  
}
