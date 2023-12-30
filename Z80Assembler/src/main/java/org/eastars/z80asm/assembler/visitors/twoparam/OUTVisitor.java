package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.OUT;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.Register;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionOUTparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.OUTContext;

public class OUTVisitor extends TwoParameterInstructionVisitor<OUT, OUTContext, InstructionOUTparametersContext> {

  @Override
  protected OUT getInstruction() {
    return new OUT();
  }

  @Override
  protected InstructionOUTparametersContext getInstructionParameters(OUTContext ctx) {
    return ctx.instructionOUTparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstructionOUTparametersContext paramCtx) {
    return getRegister(paramCtx.A, Register.A)
        .orElseGet(() -> getRegisters(paramCtx.registers())
            .orElseGet(() -> null));
  }

  @Override
  protected Parameter getTargetParameter(InstructionOUTparametersContext paramCtx) {
    return getRegister(paramCtx.C, Register.C)
        .orElseGet(() -> getExpression(paramCtx.hex8bits())
            .orElseGet(() -> null));
  }

}
