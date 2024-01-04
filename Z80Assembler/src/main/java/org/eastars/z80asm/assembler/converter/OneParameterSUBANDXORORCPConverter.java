package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.*;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class OneParameterSUBANDXORORCPConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  public static List<InstructionEntry<OneParameterInstruction>> getInstructionList() {
    return List.of(
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(AND.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0xa0})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xe6, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xa6, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterSUBANDXORORCPConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(CP.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0xb8})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xfe, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[]{(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[]{(byte) 0xdd, (byte) 0xbe, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterSUBANDXORORCPConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(OR.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0xb0})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xf6, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xb6, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterSUBANDXORORCPConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(SUB.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0x90})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xd6, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x96, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterSUBANDXORORCPConverter::generate).build(),
        InstructionEntry.<OneParameterInstruction>builder()
            .instruction(XOR.class)
            .masks(List.of(
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0xa8})
                    .extractor((r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xee, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<OneParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xae, 0x00})
                    .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
                    .build()
            ))
            .generator(OneParameterSUBANDXORORCPConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      List<Parameter> parameters,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    Parameter parameter = parameters.get(0);
    int registerIndex = getRegisterRHIndex(parameter);
    if (registerIndex != -1) {
      result = new byte[] {(byte) (masks.get(0).getValue()[0] | registerIndex)};
    } else if (parameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter) parameter).getExpressionValue(compilationContext);
      result = new byte[] {masks.get(1).getValue()[0], 0};
      result[1] = (byte) (value & 0xff);
    }
    if (result == null && parameter instanceof IndexedAddressingParameter) {
      result = generateIndexedAddressing(
          compilationContext, (IndexedAddressingParameter) parameter, masks.get(2).getValue());
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
