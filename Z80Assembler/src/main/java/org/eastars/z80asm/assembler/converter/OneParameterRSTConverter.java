package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.RST;
import org.eastars.z80asm.ast.parameter.ConstantValueParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class OneParameterRSTConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<OneParameterInstructionConverter.InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RST.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xc7})
                    .value(new byte[]{(byte) 0xc7})
                    .extractor((r, v) -> r.setParameter(reverseRSTValue(v[0] & 0x38))).build()
            ))
            .generator((c, p, m) -> generateRST(p, m)).build()
    );
  }

  private static byte[] generateRST(Parameter parameter, List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    if (parameter instanceof ConstantValueParameter constantValeParameter) {
      int value = Integer.parseInt(constantValeParameter.getValue(), 16);
      if (value == 0x00 || value == 0x08 || value == 0x10 || value == 0x18
          || value == 0x20 || value == 0x28 || value == 0x30 || value == 0x38) {
        result = new byte[] {(byte) (masks.get(0).getValue()[0] | value)};
      }
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
