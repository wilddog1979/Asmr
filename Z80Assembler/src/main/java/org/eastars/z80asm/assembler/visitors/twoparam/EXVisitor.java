package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.EX;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.RegisterIndirectAddressingParameter;
import org.eastars.z80asm.ast.parameter.RegisterPair;
import org.eastars.z80asm.ast.parameter.RegisterPairParameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.*;

public class EXVisitor extends TwoParameterInstructionVisitor<EX, EXContext, InstructionEXparametersContext> {

  @Override
  protected EX getInstruction() {
    return new EX();
  }

  @Override
  protected InstructionEXparametersContext getInstructionParameters(EXContext ctx) {
    return ctx.instructionEXparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstructionEXparametersContext paramCtx) {
    Parameter parameter = null;

    if (paramCtx instanceof ExafafmarkedContext) {
      parameter = new RegisterPairParameter(RegisterPair.AFMarked);
    } else if (paramCtx instanceof ExrefsphlContext
        || paramCtx instanceof ExdehlContext) {
      parameter = new RegisterPairParameter(RegisterPair.HL);
    } else if (paramCtx instanceof ExrefspixContext) {
      parameter = new RegisterPairParameter(RegisterPair.IX);
    } else if (paramCtx instanceof ExrefspiyContext) {
      parameter = new RegisterPairParameter(RegisterPair.IY);
    }

    return parameter;
  }

  @Override
  protected Parameter getTargetParameter(InstructionEXparametersContext paramCtx) {
    Parameter parameter = null;

    if (paramCtx instanceof ExafafmarkedContext) {
      parameter = new RegisterPairParameter(RegisterPair.AF);
    } else if (paramCtx instanceof ExrefsphlContext
        || paramCtx instanceof ExrefspixContext
        || paramCtx instanceof ExrefspiyContext) {
      parameter = new RegisterIndirectAddressingParameter(RegisterPair.SP);
    } else if (paramCtx instanceof ExdehlContext) {
      parameter = new RegisterPairParameter(RegisterPair.DE);
    }

    return parameter;
  }

}
