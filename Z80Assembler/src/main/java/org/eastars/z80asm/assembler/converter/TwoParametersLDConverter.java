package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.LD;
import org.eastars.z80asm.ast.parameter.*;

import java.util.List;

import static org.eastars.z80asm.assembler.converter.Check.*;

public class TwoParametersLDConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(LD.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {0x02})
                    .extractor((r, v) -> r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.BC))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {0x12})
                    .extractor((r, v) -> r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.DE))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {0x0a})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(new RegisterIndirectAddressingParameter(RegisterPair.BC))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {0x1a})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(new RegisterIndirectAddressingParameter(RegisterPair.DE))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc0})
                    .value(new byte[] {0x40})
                    .extractor((r, v) -> {
                      if ((v[0] & 0x07) == 0x06) {
                        return r.setTarget(reverseRegisterR((v[0] >> 3) & 0x07))
                            .setSource(new RegisterIndirectAddressingParameter(RegisterPair.HL));
                      } else if ((v[0] & 0x38) == 0x30) {
                        return r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.HL))
                            .setSource(reverseRegisterR(v[0] & 0x07));
                      } else {
                        return r.setTarget(reverseRegisterR((v[0] >> 3) & 0x07))
                            .setSource(reverseRegisterRMarked(v[0] & 0x07));
                      }
                    }).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {(byte) 0xf9})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.SP))
                        .setSource(new RegisterPairParameter(RegisterPair.HL))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7, 0x00})
                    .value(new byte[] {0x06, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseRegisterRH((v[0] >> 3) & 0x07))
                        .setSource(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xf9})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.SP))
                        .setSource(reverseIXIY((v[0] & 0x20) == 0x00))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xff})
                    .value(new byte[] {(byte) 0xed, (byte) 0x47})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.I))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xff})
                    .value(new byte[] {(byte) 0xed, (byte) 0x57})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(new RegisterParameter(Register.I))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xff})
                    .value(new byte[] {(byte) 0xed, (byte) 0x4f})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.R))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xff})
                    .value(new byte[] {(byte) 0xed, (byte) 0x5f})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(new RegisterParameter(Register.R))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf, 0x00, 0x00})
                    .value(new byte[] {0x01, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseRegisterSS((v[0] >> 4) & 0x03))
                        .setSource(reverseImmediate16(v[2], v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {0x22, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(
                            new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))
                        .setSource(new RegisterPairParameter(RegisterPair.HL))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {0x2a, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                        .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2],
                            v[1])))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {0x32, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(
                            new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {0x3a, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2],
                            v[1])))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xf8, 0x00})
                    .value(new byte[] {(byte) 0xdd, 0x70, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))
                        .setSource(reverseRegisterR(v[1] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xc7, 0x00})
                    .value(new byte[] {(byte) 0xdd, 0x46, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x34, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))
                        .setSource(reverseImmediate8(v[3]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xed, (byte) 0x43, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(
                            new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))
                        .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xed, (byte) 0x4b, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseRegisterSS((v[1] >> 4) & 0x03))
                        .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3],
                            v[2])))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x21, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00))
                        .setSource(reverseImmediate16(v[3], v[2]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x22, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(
                            new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))
                        .setSource(reverseIXIY((v[0] & 0x20) == 0x00))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x2a, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00))
                        .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3],
                            v[2])))).build()
            ))
            .generator(TwoParametersLDConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    int registerIndex = -1;
    int markedRegisterIndex = -1;
    if (checkRegisterIndirectAddressing(targetparameter, RegisterPair.BC)
        && checkRegisterParameter(sourceparameter, Register.A)) {
      result = selectMask(masks.get(0));
    } else if (checkRegisterIndirectAddressing(targetparameter, RegisterPair.DE)
        && checkRegisterParameter(sourceparameter, Register.A)) {
      result = selectMask(masks.get(1));
    } else if (checkRegisterParameter(targetparameter, Register.A)
        && checkRegisterIndirectAddressing(sourceparameter, RegisterPair.BC)) {
      result = selectMask(masks.get(2));
    } else if (checkRegisterParameter(targetparameter, Register.A)
        && checkRegisterIndirectAddressing(sourceparameter, RegisterPair.DE)) {
      result = selectMask(masks.get(3));
    } else if (checkRegisterIndirectAddressing(targetparameter, RegisterPair.HL)
        && (registerIndex = getRegisterRIndex(sourceparameter)) != -1) {
      result = selectMask(masks.get(4));
      result[0] |= (byte) (0x30 | (registerIndex & 0x07));
    } else if ((registerIndex = getRegisterRIndex(targetparameter)) != -1
        && checkRegisterIndirectAddressing(sourceparameter, RegisterPair.HL)) {
      result = selectMask(masks.get(4));
      result[0] |= (byte) (((registerIndex << 3) & 0x38) | 0x06);
    } else if ((registerIndex = getRegisterRIndex(targetparameter)) != -1
        && (markedRegisterIndex = getMarkedRegisterRIndex(sourceparameter)) != -1) {
      result = selectMask(masks.get(4));
      result [0] |= (byte) (((registerIndex << 3) & 0x38) | (markedRegisterIndex & 0x07));
    } else if (checkRegisterPairParameter(targetparameter, RegisterPair.SP)
        && checkRegisterPairParameter(sourceparameter, RegisterPair.HL)) {
      result = selectMask(masks.get(5));
    } else if ((registerIndex = getRegisterRHIndex(targetparameter)) != -1
        && sourceparameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter)  sourceparameter).getExpressionValue(compilationContext);
      result = selectMask(masks.get(6));
      result[0] |= (byte) ((registerIndex << 3) & 0x38);
      result[1] = (byte) (value & 0xff);
    } else if (checkRegisterPairParameter(targetparameter, RegisterPair.SP)
        && checkRegisterPairParameterIXorIY(sourceparameter)) {
      result = generateIndexRegisters(((RegisterPairParameter) sourceparameter).getRegisterPair(),
          masks.get(7).getValue());
    } else if (checkRegisterParameter(targetparameter, Register.I)
        && checkRegisterParameter(sourceparameter, Register.A)) {
      result = selectMask(masks.get(8));
    } else if (checkRegisterParameter(targetparameter, Register.A)
        && checkRegisterParameter(sourceparameter, Register.I)) {
      result = selectMask(masks.get(9));
    } else if (checkRegisterParameter(targetparameter, Register.R)
        && checkRegisterParameter(sourceparameter, Register.A)) {
      result = selectMask(masks.get(10));
    } else if (checkRegisterParameter(targetparameter, Register.A)
        && checkRegisterParameter(sourceparameter, Register.R)) {
      result = selectMask(masks.get(11));
    } else if ((registerIndex = getRegisterSSIndex(targetparameter)) != -1
        && sourceparameter instanceof ExpressionParameter) {
      result = generateWithExpressionValue(
          compilationContext, selectMask(masks.get(12)), (ExpressionParameter) sourceparameter, 1);
      result[0] |= (byte) ((registerIndex << 4) & 0x30);
    } else if (targetparameter instanceof ImmediateAddressingParameter
        && checkRegisterPairParameter(sourceparameter, RegisterPair.HL)) {
      result = generateWithImmediateValue(compilationContext, selectMask(masks.get(13)), targetparameter, 1);
    } else if (checkRegisterPairParameter(targetparameter, RegisterPair.HL)
        && sourceparameter instanceof ImmediateAddressingParameter) {
      result = generateWithImmediateValue(compilationContext, selectMask(masks.get(14)), sourceparameter, 1);
    } else if (targetparameter instanceof ImmediateAddressingParameter
        && checkRegisterParameter(sourceparameter, Register.A)) {
      result = generateWithImmediateValue(compilationContext, selectMask(masks.get(15)), targetparameter, 1);
    } else if (checkRegisterParameter(targetparameter, Register.A)
        && sourceparameter instanceof ImmediateAddressingParameter) {
      result = generateWithImmediateValue(compilationContext, selectMask(masks.get(16)), sourceparameter, 1);
    } else if (targetparameter instanceof IndexedAddressingParameter
        && (registerIndex = getRegisterRIndex(sourceparameter)) != -1) {
      result = generateIndexedAddressing(
          compilationContext, (IndexedAddressingParameter) targetparameter, masks.get(17).getValue());
      if (result != null) {
        result[1] |= (byte) (registerIndex & 0x07);
      }
    }

    if (result == null) {
      if (sourceparameter instanceof IndexedAddressingParameter
          && (registerIndex = getRegisterRIndex(targetparameter)) != -1) {
        result = generateIndexedAddressing(
            compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(18).getValue());
        if (result != null) {
          result[1] |= (byte) ((registerIndex << 3) & 0x38);
        }
      }
    }

    if (result == null) {
      if (targetparameter instanceof IndexedAddressingParameter
          && sourceparameter instanceof ExpressionParameter) {
        result = generateIndexedAddressing(
            compilationContext, (IndexedAddressingParameter) targetparameter, masks.get(19).getValue());
        if (result != null) {
          int value = ((ExpressionParameter) sourceparameter).getExpressionValue(compilationContext);
          result[3] = (byte) (value & 0xff);
        }
      }
    }

    if (result == null) {
      if (targetparameter instanceof ImmediateAddressingParameter
          && (registerIndex = getRegisterSSIndex(sourceparameter)) != -1) {
        result = generateWithImmediateValue(compilationContext, selectMask(masks.get(20)), targetparameter, 2);
        result[1] |= (byte) ((registerIndex << 4) & 0x38);
      } else if (sourceparameter instanceof ImmediateAddressingParameter
          && (registerIndex = getRegisterSSIndex(targetparameter)) != -1) {
        result = generateWithImmediateValue(compilationContext, selectMask(masks.get(21)), sourceparameter, 2);
        result[1] |= (byte) ((registerIndex << 4) & 0x38);
      } else if (checkRegisterPairParameterIXorIY(targetparameter)
          && sourceparameter instanceof ExpressionParameter) {
        result = generateWithExpressionValue(
            compilationContext,
            generateIndexRegisters(((RegisterPairParameter) targetparameter).getRegisterPair(),
                masks.get(22).getValue()),
            (ExpressionParameter) sourceparameter,
            2);
      } else if (targetparameter instanceof ImmediateAddressingParameter
          && checkRegisterPairParameterIXorIY(sourceparameter)) {
        result = generateWithImmediateValue(
            compilationContext,
            generateIndexRegisters(((RegisterPairParameter) sourceparameter).getRegisterPair(),
                masks.get(23).getValue()),
            targetparameter,
            2);
      } else if (sourceparameter instanceof ImmediateAddressingParameter
          && checkRegisterPairParameterIXorIY(targetparameter)) {
        result = generateWithImmediateValue(
            compilationContext,
            generateIndexRegisters(((RegisterPairParameter) targetparameter).getRegisterPair(),
                masks.get(24).getValue()),
            sourceparameter,
            2);
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
