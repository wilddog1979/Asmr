package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.CP;
import org.eastars.z80asm.parser.Z80AssemblerParser.CPContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;

public class CPVisitor extends SUBANDXORORCPVisitor<CP, CPContext> {

  @Override
  protected CP getInstruction() {
    return new CP();
  }

  @Override
  protected InstructionSUBANDXORORCPparametersContext getInstructionParameters(CPContext ctx) {
    return ctx.instructionSUBANDXORORCPparameters();
  }

}
