package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.CALL;
import org.eastars.z80asm.ast.parameter.Condition;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class TwoParametersCALLConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(CALL.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xc4, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x07))
                        .setSource(reverseImmediate16(v[2], v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xcd, 0x00, 0x00})
                    .extractor((r, v) -> r.setSource(reverseImmediate16(v[2], v[1]))).build()
            ))
            .generator(TwoParametersCALLConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    if (sourceparameter instanceof ExpressionParameter) {
      if (targetparameter instanceof ConditionParameter) {
        result = generateWithExpressionValue(
            compilationContext, selectMask(masks.get(0)), (ExpressionParameter) sourceparameter, 1);
        Condition condition = ((ConditionParameter) targetparameter).getCondition();
        result[0] |= (byte) (condition.getOpcode() << 3);
      } else if (targetparameter == null) {
        result = generateWithExpressionValue(
            compilationContext, selectMask(masks.get(1)), (ExpressionParameter) sourceparameter, 1);
      }
    }

    return result;
  }

  @Override
  public byte[] convert(CompilationContext compilationContext, TwoParameterInstruction instruction) {
    return new byte[0];
  }

  @Override
  protected MaskedOpcodeMap<TwoParameterInstruction> getReverse(int index) {
    return null;
  }

}
