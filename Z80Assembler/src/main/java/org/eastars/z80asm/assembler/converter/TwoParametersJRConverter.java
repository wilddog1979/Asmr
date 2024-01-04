package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.JR;
import org.eastars.z80asm.ast.parameter.Condition;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class TwoParametersJRConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(JR.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xe7, 0x00})
                    .value(new byte[] {0x20, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x03))
                        .setSource(reverseImmediate8(v[1] + 2))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {0x18, 0x00})
                    .extractor((r, v) -> r.setSource(reverseImmediate8(v[1] + 2))).build()
            ))
            .generator(TwoParametersJRConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetParameter = twoParameters.target();
    Parameter sourceParameter = twoParameters.source();
    if (targetParameter instanceof ConditionParameter
        && sourceParameter instanceof ExpressionParameter) {
      Condition condition = ((ConditionParameter) targetParameter).getCondition();
      int value = ((ExpressionParameter) sourceParameter).getExpressionValue(compilationContext) - 2;
      if (condition == Condition.NZ || condition == Condition.Z
          || condition == Condition.NC || condition == Condition.C) {
        result = selectMask(masks.get(0));
        result[0] |= (byte) (condition.getOpcode() << 3);
        result[1] = (byte) (value & 0xff);
      }
    } else if (sourceParameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter) sourceParameter).getExpressionValue(compilationContext) - 2;
      result = selectMask(masks.get(1));
      result[1] = (byte) (value & 0xff);
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
