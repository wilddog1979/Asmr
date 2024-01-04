package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.*;
import org.eastars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.Arrays;
import java.util.List;

public class OneParameterBitRotatingConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RL.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xf8})
                    .value(new byte[] {(byte) 0xca, 0x10})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x16})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RLC.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xf8})
                    .value(new byte[] {(byte) 0xca, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x06})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RR.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xff, (byte) 0xf8})
                    .value(new byte[]{(byte) 0xca, 0x18})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[]{(byte) 0xdd, (byte) 0xca, 0x00, 0x1e})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(RRC.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xff, (byte) 0xf8})
                    .value(new byte[]{(byte) 0xca, 0x08})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[]{(byte) 0xdd, (byte) 0xca, 0x00, 0x0e})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(SLA.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xf8})
                    .value(new byte[] {(byte) 0xca, 0x20})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x26})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(SRA.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xf8})
                    .value(new byte[] {(byte) 0xca, 0x28})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x2e})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(SRL.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xf8})
                    .value(new byte[] {(byte) 0xca, 0x38})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x3e})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterBitRotatingConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      List<Parameter> parameters,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    int registerIndex = getRegisterRHIndex(parameters.get(0));
    if (registerIndex != -1) {
      result = Arrays.copyOf(masks.get(0).getValue(), 2);
      result[1] |= (byte) registerIndex;
    } else if (parameters.get(0) instanceof IndexedAddressingParameter) {
      result = generateIndexedAddressing(
          compilationContext, (IndexedAddressingParameter) parameters.get(0), masks.get(1).getValue());
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
