package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.IN;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.Register;
import org.eastars.z80asm.parser.Z80AssemblerParser.INContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionINparametersContext;

public class INVisitor extends TwoParameterInstructionVisitor<IN, INContext, InstructionINparametersContext> {

  @Override
  protected IN getInstruction() {
    return new IN();
  }

  @Override
  protected InstructionINparametersContext getInstructionParameters(INContext ctx) {
    return ctx.instructionINparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstructionINparametersContext paramCtx) {
    return getRegister(paramCtx.C, Register.C)
        .orElseGet(() -> getExpression(paramCtx.hex8bits())
            .orElseGet(() -> null));
  }

  @Override
  protected Parameter getTargetParameter(InstructionINparametersContext paramCtx) {
    return getRegister(paramCtx.A, Register.A)
        .orElseGet(() -> getRegisters(paramCtx.registers())
            .orElseGet(() -> null));
  }

}
