package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.CALL;
import org.eastars.z80asm.ast.parameter.Condition;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.CALLContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionCALLparametersContext;

public class CALLVisitor extends TwoParameterInstructionVisitor<CALL, CALLContext, InstructionCALLparametersContext> {

  @Override
  protected CALL getInstruction() {
    return new CALL();
  }

  @Override
  protected InstructionCALLparametersContext getInstructionParameters(CALLContext ctx) {
    return ctx.instructionCALLparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstructionCALLparametersContext paramCtx) {
    return getExpression(paramCtx.hex16bits()).orElse(null);
  }

  @Override
  protected Parameter getTargetParameter(InstructionCALLparametersContext paramCtx) {
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
