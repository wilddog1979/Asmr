package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.JP;
import org.eastars.z80asm.ast.parameter.Condition;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.RegisterPair;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstruictionJPparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.JPContext;

public class JPVisitor extends TwoParameterInstructionVisitor<JP, JPContext, InstruictionJPparametersContext> {

  @Override
  protected JP getInstruction() {
    return new JP();
  }

  @Override
  protected InstruictionJPparametersContext getInstructionParameters(JPContext ctx) {
    return ctx.instruictionJPparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstruictionJPparametersContext paramCtx) {
    return getRegisterIndirectAddressing(paramCtx.HL, RegisterPair.HL)
        .orElseGet(() -> getRegisterIndirectAddressing(paramCtx.IX, RegisterPair.IX)
            .orElseGet(() -> getRegisterIndirectAddressing(paramCtx.IY, RegisterPair.IY)
                .orElseGet(() -> getExpression(paramCtx.hex16bits())
                    .orElse(null))));
  }

  @Override
  protected Parameter getTargetParameter(InstruictionJPparametersContext paramCtx) {
    Parameter parameter = null;

    if (paramCtx.NZ != null) {
      parameter = new ConditionParameter(Condition.NZ);
    } else if (paramCtx.Z != null) {
      parameter = new ConditionParameter(Condition.Z);
    } else if (paramCtx.NC != null) {
      parameter = new ConditionParameter(Condition.NC);
    } else if (paramCtx.C != null) {
      parameter = new ConditionParameter(Condition.C);
    } else if (paramCtx.PO != null) {
      parameter = new ConditionParameter(Condition.PO);
    } else if (paramCtx.PE != null) {
      parameter = new ConditionParameter(Condition.PE);
    } else if (paramCtx.P != null) {
      parameter = new ConditionParameter(Condition.P);
    } else if (paramCtx.M != null) {
      parameter = new ConditionParameter(Condition.M);
    }

    return parameter;
  }

}
