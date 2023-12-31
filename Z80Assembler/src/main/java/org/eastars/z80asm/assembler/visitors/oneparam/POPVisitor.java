package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.ast.instructions.oneparam.POP;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionPUSHPOPparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.POPContext;

public class POPVisitor extends PUSHPOPVisitor<POP, POPContext> {

  @Override
  protected POP getInstruction() {
    return new POP();
  }

  @Override
  protected InstructionPUSHPOPparametersContext getInstructionParameters(POPContext ctx) {
    return ctx.instructionPUSHPOPparameters();
  }

}
