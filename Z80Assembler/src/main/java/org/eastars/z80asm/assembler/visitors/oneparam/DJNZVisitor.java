package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eastars.z80asm.ast.instructions.oneparam.DJNZ;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.DJNZContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionDJNZparametersContext;

public class DJNZVisitor extends OneParameterVisitor<DJNZ, DJNZContext, InstructionDJNZparametersContext> {

  @Override
  protected DJNZ getInstruction() {
    return new DJNZ();
  }

  @Override
  protected InstructionDJNZparametersContext getInstructionParameters(DJNZContext ctx) {
    return ctx.instructionDJNZparameters();
  }

  @Override
  protected Parameter getParameter(InstructionDJNZparametersContext paramCtx) {
    return getExpression(paramCtx.hex8bits()).orElseGet(() -> null);
  }

}
