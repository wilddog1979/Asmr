package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.RET;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class OneParameterRETConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<OneParameterInstructionConverter.InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RET.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {(byte) 0xc9})
                    .extractor((r, v) -> r.setParameter(null)).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7})
                    .value(new byte[] {(byte) 0xc0})
                    .extractor((r, v) -> r.setParameter(reverseCondition((v[0] >> 3) & 0x07))).build()
            ))
            .generator((c, p, m) -> generateRET(p, m)).build()
    );
  }

  private static byte[] generateRET(Parameter parameter, List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    if (parameter == null || parameter instanceof ConditionParameter) {
      result = parameter == null
          ? new byte[] {
          masks.get(0).getValue()[0]
      } :
          new byte[] {
              (byte) (masks.get(1).getValue()[0] | (((ConditionParameter) parameter).getCondition().getOpcode() << 3))
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
