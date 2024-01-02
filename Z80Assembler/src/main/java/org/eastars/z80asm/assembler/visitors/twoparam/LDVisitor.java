package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.LD;
import org.eastars.z80asm.ast.parameter.*;
import org.eastars.z80asm.parser.Z80AssemblerParser.*;

public class LDVisitor extends TwoParameterInstructionVisitor<LD, LDContext, InstructionLDparametersContext> {

  @Override
  protected LD getInstruction() {
    return new LD();
  }

  @Override
  protected InstructionLDparametersContext getInstructionParameters(LDContext ctx) {
    return ctx.instructionLDparameters();
  }

  @Override
  protected Parameter getSourceParameter(InstructionLDparametersContext paramCtx) {
    Parameter parameter = null;

    if (paramCtx instanceof LDfromAToAddressContext
        || paramCtx instanceof LDiaContext
        || paramCtx instanceof LDraContext
        || paramCtx instanceof LDrefnum16aContext) {
      parameter = new RegisterParameter(Register.A);
    } else if (paramCtx instanceof LDaiContext) {
      parameter = new RegisterParameter(Register.I);
    } else if (paramCtx instanceof LDarContext) {
      parameter = new RegisterParameter(Register.R);
    } else if (paramCtx instanceof LDfromAddressToAContext) {
      parameter = new RegisterIndirectAddressingParameter(
          ((LDfromAddressToAContext) paramCtx).refbc != null ? RegisterPair.BC : RegisterPair.DE);
    } else if (paramCtx instanceof LDrefhltoregContext) {
      parameter = new RegisterIndirectAddressingParameter(RegisterPair.HL);
    } else if (paramCtx instanceof LDsphlContext
        || paramCtx instanceof LDrefnum16hlContext) {
      parameter = new RegisterPairParameter(RegisterPair.HL);
    } else if (paramCtx instanceof LDrhnum8Context) {
      parameter = getExpression(((LDrhnum8Context) paramCtx).hex8bits()).orElse(null);
    } else if (paramCtx instanceof LDssnum16Context) {
      parameter = getExpression(((LDssnum16Context) paramCtx).hex16bits()).orElse(null);
    } else if (paramCtx instanceof LDidxnum8Context) {
      parameter = getExpression(((LDidxnum8Context) paramCtx).hex8bits()).orElse(null);
    } else if (paramCtx instanceof LDixnum16Context) {
      parameter = getExpression(((LDixnum16Context) paramCtx).hex16bits()).orElse(null);
    } else if (paramCtx instanceof LDiynum16Context) {
      parameter = getExpression(((LDiynum16Context) paramCtx).hex16bits()).orElse(null);
    } else if (paramCtx instanceof LDspixContext
        || paramCtx instanceof LDrefnum16ixContext) {
      parameter = new RegisterPairParameter(RegisterPair.IX);
    } else if (paramCtx instanceof LDspiyContext
        || paramCtx instanceof LDrefnum16iyContext) {
      parameter = new RegisterPairParameter(RegisterPair.IY);
    } else if (paramCtx instanceof LDhlrefnum16Context) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDhlrefnum16Context) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDarefnum16Context) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDarefnum16Context) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDssrefnum16Context) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDssrefnum16Context) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDixrefnum16Context) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDixrefnum16Context) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDiyrefnum16Context) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDiyrefnum16Context) paramCtx).hex16bits()).orElse(null));
    } else {
      parameter = getRegisters(paramCtx, LDregtorefhlContext.class)
          .orElseGet(() -> getRegistersMarked(paramCtx, LDregregmarkedContext.class)
              .orElseGet(() -> getRegisters(paramCtx, LDidxregsContext.class)
                  .orElseGet(() -> getIndexedReference(paramCtx, LDregsidxContext.class)
                      .orElseGet(() -> getRegisterSSParameter(paramCtx, LDrefnum16ssContext.class)
                          .orElse(null)))));
    }

    return parameter;
  }

  @Override
  protected Parameter getTargetParameter(InstructionLDparametersContext paramCtx) {
    Parameter parameter = null;

    if (paramCtx instanceof LDfromAToAddressContext) {
      parameter = new RegisterIndirectAddressingParameter(
          ((LDfromAToAddressContext) paramCtx).refbc != null ? RegisterPair.BC : RegisterPair.DE);
    } else if (paramCtx instanceof LDfromAddressToAContext
        || paramCtx instanceof LDaiContext
        || paramCtx instanceof LDarContext
        || paramCtx instanceof LDarefnum16Context) {
      parameter = new RegisterParameter(Register.A);
    } else if (paramCtx instanceof LDiaContext) {
      parameter = new RegisterParameter(Register.I);
    } else if (paramCtx instanceof LDraContext) {
      parameter = new RegisterParameter(Register.R);
    } else if (paramCtx instanceof LDhlrefnum16Context) {
      parameter = new RegisterPairParameter(RegisterPair.HL);
    } else if (paramCtx instanceof LDregtorefhlContext) {
      parameter = new RegisterIndirectAddressingParameter(RegisterPair.HL);
    } else if (paramCtx instanceof LDsphlContext
        || paramCtx instanceof LDspixContext
        || paramCtx instanceof LDspiyContext) {
      parameter = new RegisterPairParameter(RegisterPair.SP);
    } else if (paramCtx instanceof LDixnum16Context
        || paramCtx instanceof LDixrefnum16Context) {
      parameter = new RegisterPairParameter(RegisterPair.IX);
    } else if (paramCtx instanceof LDiynum16Context
        || paramCtx instanceof LDiyrefnum16Context) {
      parameter = new RegisterPairParameter(RegisterPair.IY);
    } else if (paramCtx instanceof LDrefnum16hlContext) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDrefnum16hlContext) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDrefnum16aContext) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDrefnum16aContext) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDrefnum16ssContext) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDrefnum16ssContext) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDrefnum16ixContext) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDrefnum16ixContext) paramCtx).hex16bits()).orElse(null));
    } else if (paramCtx instanceof LDrefnum16iyContext) {
      parameter = new ImmediateAddressingParameter(
          getExpression(((LDrefnum16iyContext) paramCtx).hex16bits()).orElse(null));
    } else {
      parameter = getRegisters(paramCtx, LDrefhltoregContext.class)
          .orElseGet(() -> getRegisters(paramCtx, LDregregmarkedContext.class)
              .orElseGet(() -> getRegistersWithReference(paramCtx, LDrhnum8Context.class)
                  .orElseGet(() -> getRegisterSSParameter(paramCtx, LDssnum16Context.class)
                      .orElseGet(() -> getIndexedReference(paramCtx, LDidxregsContext.class)
                          .orElseGet(() -> getRegisters(paramCtx, LDregsidxContext.class)
                              .orElseGet(() -> getIndexedReference(paramCtx, LDidxnum8Context.class)
                                  .orElseGet(() -> getRegisterSSParameter(paramCtx, LDssrefnum16Context.class)
                                      .orElse(null))))))));
    }

    return parameter;
  }

}
