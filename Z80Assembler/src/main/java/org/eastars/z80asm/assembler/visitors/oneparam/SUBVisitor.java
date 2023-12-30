package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.SUB;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.SUBContext;

public class SUBVisitor extends SUBANDXORORCPVisitor<SUB, SUBContext> {

  @Override
  protected SUB getInstruction() {
    return new SUB();
  }

  @Override
  protected InstructionSUBANDXORORCPparametersContext getInstructionParameters(SUBContext ctx) {
    return ctx.instructionSUBANDXORORCPparameters();
  }

}
