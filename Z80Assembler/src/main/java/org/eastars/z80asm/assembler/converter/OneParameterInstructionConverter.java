package org.eastars.z80asm.assembler.converter;

import lombok.Builder;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.oneparam.*;
import org.eastars.z80asm.ast.parameter.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OneParameterInstructionConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  @Builder
  private record InstructionEntry<T extends Z80Instruction>(Class<? extends T> instruction,
                                  List<MaskedOpcode<T>> masks,
                                  InstructionAssemblyGenerator<T> generator) {
    private InstructionEntry(
        Class<? extends T> instruction,
        List<MaskedOpcode<T>> masks,
        InstructionAssemblyGenerator<T> generator) {
      this.instruction = instruction;
      this.masks = masks;
      masks.forEach(m -> m.setInstruction(instruction));
      this.generator = generator;
    }

  }

  @FunctionalInterface
  protected interface InstructionAssemblyGenerator<T extends Z80Instruction> {

    byte[] generate(
        CompilationContext compilationContext,
        Parameter parameter,
        List<MaskedOpcode<T>> masks);

  }

  private static final List<InstructionEntry<OneParameterInstruction>> instructionlist = Arrays.asList(
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
                .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
        ))
        .generator(OneParameterInstructionConverter::generateSUBANDXORORCP).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateSUBANDXORORCP).build(),
        InstructionEntry.<OneParameterInstruction>builder()
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateINCDEC).build(),
      InstructionEntry.<OneParameterInstruction>builder()
          .instruction(DJNZ.class)
          .masks(List.of(
              MaskedOpcode.<OneParameterInstruction>builder()
                  .mask(new byte[]{(byte) 0xff, 0x00})
                  .value(new byte[]{0x10, 0x00})
                  .extractor((r, v) -> r.setParameter(reverseImmediate8(v[1] + 2))).build()
          ))
          .generator(OneParameterInstructionConverter::generateDJNZ).build(),
      InstructionEntry.<OneParameterInstruction>builder()
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateINCDEC).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateSUBANDXORORCP).build(),
      InstructionEntry.<OneParameterInstruction>builder()
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
          .generator(OneParameterInstructionConverter::generatePUSHPOP).build(),
      InstructionEntry.<OneParameterInstruction>builder()
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
          .generator(OneParameterInstructionConverter::generatePUSHPOP).build(),
      InstructionEntry.<OneParameterInstruction>builder()
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
          .generator((c, p, m) -> generateRET(p, m)).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
      InstructionEntry.<OneParameterInstruction>builder()
          .instruction(RST.class)
          .masks(List.of(
              MaskedOpcode.<OneParameterInstruction>builder()
                  .mask(new byte[]{(byte) 0xc7})
                  .value(new byte[]{(byte) 0xc7})
                  .extractor((r, v) -> r.setParameter(reverseRSTValue(v[0] & 0x38))).build()
          ))
          .generator((c, p, m) -> generateRST(p, m)).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateBitRotating).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateSUBANDXORORCP).build(),
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
                  .extractor((r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
          ))
          .generator(OneParameterInstructionConverter::generateSUBANDXORORCP).build());

  private static final Map<
      Class<? extends OneParameterInstruction>, InstructionEntry<OneParameterInstruction>> instructions =
      instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e));

  private static final Map<Integer, MaskedOpcodeMap<OneParameterInstruction>> reverse =
      instructionlist.stream().flatMap(e -> e.masks.stream()).collect(Collectors.groupingBy(
          m -> m.getMask().length,
          Collectors.toMap(
              m -> new OpcodeMask<>(m.getMask(), m.getValue(), m.getExtractor()),
              MaskedOpcode::getInstruction,
              (u, v) -> {
                throw new IllegalStateException(String.format("Duplicate key %s", u));
              },
              MaskedOpcodeMap::new)));

  @Override
  protected MaskedOpcodeMap<OneParameterInstruction> getReverse(int index) {
    return reverse.get(index);
  }

  @Override
  public byte[] convert(CompilationContext compilationContext, OneParameterInstruction instruction) {
    byte[] result = null;
    InstructionEntry<OneParameterInstruction> entry = instructions.get(instruction.getClass());
    if (entry != null && entry.generator != null) {
      result = entry.generator.generate(compilationContext, instruction.getParameter(), entry.masks);
    }
    return result;
  }

  private static byte[] generateSUBANDXORORCP(
      CompilationContext compilationContext,
      Parameter parameter,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

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

  private static byte[] generateINCDEC(
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

  private static byte[] generateDJNZ(
      CompilationContext compilationContext,
      Parameter parameter,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    if (parameter instanceof ExpressionParameter expressionParameter) {
      result = new byte[] {
          masks.get(0).getValue()[0],
          (byte) (expressionParameter.getExpressionValue(compilationContext) - 2)
      };
    }

    return result;
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

  private static byte[] generateBitRotating(
      CompilationContext compilationContext,
      Parameter parameter,
      List<MaskedOpcode<OneParameterInstruction>> masks) {
    byte[] result = null;

    int registerIndex = getRegisterRHIndex(parameter);
    if (registerIndex != -1) {
      result = Arrays.copyOf(masks.get(0).getValue(), 2);
      result[1] |= (byte) registerIndex;
    } else if (parameter instanceof IndexedAddressingParameter) {
      result = generateIndexedAddressing(
          compilationContext, (IndexedAddressingParameter) parameter, masks.get(1).getValue());
    }

    return result;
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

}
