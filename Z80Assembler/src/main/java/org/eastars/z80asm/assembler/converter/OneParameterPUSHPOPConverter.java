package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.POP;
import org.eastars.z80asm.ast.instructions.oneparam.PUSH;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.RegisterPairParameter;

import java.util.List;

public class OneParameterPUSHPOPConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<OneParameterInstructionConverter.InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(POP.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf})
                    .value(new byte[] {(byte) 0xc1})
                    .extractor((r, v) -> r.setParameter(reverseRegisterQQ((v[0] >> 4) & 0x03))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xe1})
                    .extractor((r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))).build()
            ))
            .generator(OneParameterPUSHPOPConverter::generatePUSHPOP).build(),
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(PUSH.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf})
                    .value(new byte[] {(byte) 0xc5})
                    .extractor((r, v) -> r.setParameter(reverseRegisterQQ((v[0] >> 4) & 0x03))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xe5})
                    .extractor((r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))).build()
            ))
            .generator(OneParameterPUSHPOPConverter::generatePUSHPOP).build()
    );
  }

  private static byte[] generatePUSHPOP(
      CompilationContext compilationContext,
      Parameter parameter,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    int registerIndex = getRegisterQQIndex(parameter);
    if (registerIndex != -1) {
      result = new byte[] {(byte) (masks.get(0).getValue()[0] | (registerIndex << 4))};
    } else if (parameter instanceof RegisterPairParameter) {
      result = generateIndexRegisters(((RegisterPairParameter) parameter).getRegisterPair(), masks.get(1).getValue());
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
