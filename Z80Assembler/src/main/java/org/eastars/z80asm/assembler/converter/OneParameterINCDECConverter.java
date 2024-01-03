package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.DEC;
import org.eastars.z80asm.ast.instructions.oneparam.INC;
import org.eastars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.RegisterPairParameter;

import java.util.List;

public class OneParameterINCDECConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<OneParameterInstructionConverter.InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(DEC.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf})
                    .value(new byte[] {0x0b})
                    .extractor((r, v) -> r.setParameter(reverseRegisterSS((v[0] >> 4) & 0x03))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7})
                    .value(new byte[] {0x05})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH((v[0] >> 3) & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, 0x2b})
                    .extractor((r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, 0x35, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterINCDECConverter::generate).build(),
        OneParameterInstructionConverter.InstructionEntry.<OneParameterInstruction>builder()
            .instruction(INC.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf})
                    .value(new byte[] {0x03})
                    .extractor((r, v) -> r.setParameter(reverseRegisterSS((v[0] >> 4) & 0x03))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7})
                    .value(new byte[] {0x04})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH((v[0] >> 3) & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, 0x23})
                    .extractor((r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, 0x34, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterINCDECConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      Parameter parameter,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    int registerIndex = getRegisterSSIndex(parameter);
    if (registerIndex != -1) {
      result = new byte[] {(byte) (masks.get(0).getValue()[0] | (registerIndex << 4))};
    } else if ((registerIndex = getRegisterRHIndex(parameter)) != -1) {
      result = new byte[] {(byte) (masks.get(1).getValue()[0] | (registerIndex << 3))};
    } else if (parameter instanceof RegisterPairParameter) {
      result = generateIndexRegisters(((RegisterPairParameter) parameter).getRegisterPair(), masks.get(2).getValue());
    }
    if (result == null && parameter instanceof IndexedAddressingParameter) {
      result = generateIndexedAddressing(
          compilationContext, (IndexedAddressingParameter) parameter, masks.get(3).getValue());
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
