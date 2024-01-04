package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.DJNZ;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class OneParameterDJNZConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(DJNZ.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xff, 0x00})
                    .value(new byte[]{0x10, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1] + 2))).build()
            ))
            .generator(OneParameterDJNZConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      List<Parameter> parameters,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    if (parameters.get(0) instanceof ExpressionParameter expressionParameter) {
      result = new byte[] {
          masks.get(0).getValue()[0],
          (byte) (expressionParameter.getExpressionValue(compilationContext) - 2)
      };
    }

    return result;
  }

  @Override
  public byte[] convert(CompilationContext compilationContext, OneParameterInstruction instruction) {
    return new byte[0];
  }

  @Override
  protected MaskedOpcodeMap<OneParameterInstruction> getReverse(int index) {
    return null;
  }

}
