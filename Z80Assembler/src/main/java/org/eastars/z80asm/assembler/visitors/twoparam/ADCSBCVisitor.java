package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.Register;
import org.eastars.z80asm.ast.parameter.RegisterPair;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionADCSBCparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class ADCSBCVisitor<T extends TwoParameterInstruction, C extends InstructionContext>
    extends TwoParameterInstructionVisitor<T, C, InstructionADCSBCparametersContext> {

  @Override
  protected Parameter getSourceParameter(InstructionADCSBCparametersContext paramCtx) {
    return getRegistersWithReference(paramCtx.registersWithReference())
        .orElseGet(() -> getRegisterSsParameter(paramCtx.registerSS())
            .orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
                .orElseGet(() -> getExpression(paramCtx.hex8bits())
                    .orElse(null))));
  }

  @Override
  protected Parameter getTargetParameter(InstructionADCSBCparametersContext paramCtx) {
    return getRegister(paramCtx.A, Register.A)
        .orElseGet(() -> getRegisterPair(paramCtx.HL, RegisterPair.HL)
            .orElse(null));
  }

}
