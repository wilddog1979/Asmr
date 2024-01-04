package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.ADD;
import org.eastars.z80asm.ast.parameter.*;

import java.util.List;

import static org.eastars.z80asm.assembler.converter.Check.checkRegisterPairParameter;
import static org.eastars.z80asm.assembler.converter.Check.checkRegisterParameter;

public class TwoParametersADDConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(ADD.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xcf})
                    .value(new byte[] {0x09})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                        .setSource(reverseRegisterSS((v[0] >> 4) & 0x03))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0x80})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xc6, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x86, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xcf})
                    .value(new byte[] {(byte) 0xdd, 0x09})
                    .extractor((r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00))
                        .setSource(reverseRegisterPPRR((v[0] & 0x20) == 0x00, (v[1] >> 4) & 0x03))).build()
            ))
            .generator(TwoParametersADDConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    int sourceIndex = getRegisterSSIndex(sourceparameter);
    if (checkRegisterPairParameter(targetparameter, RegisterPair.HL)
        && sourceIndex != -1) {
      result = selectMask(masks.get(0));
      result[0] |= (byte) ((sourceIndex << 4) & 0x30);
    } else if (checkRegisterParameter(targetparameter, Register.A)) {
      sourceIndex = getRegisterRHIndex(sourceparameter);
      if (sourceIndex != -1) {
        result = selectMask(masks.get(1));
        result[0] |= (byte) (sourceIndex & 0x07);
      } else if (sourceparameter instanceof ExpressionParameter) {
        result = selectMask(masks.get(2));
        result[1] |= (byte) ((ExpressionParameter) sourceparameter).getExpressionValue(compilationContext);
      } else if (sourceparameter instanceof IndexedAddressingParameter) {
        result = generateIndexedAddressing(
            compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(3).getValue());
      }
    }
    if (result == null && targetparameter instanceof RegisterPairParameter) {
      result = generateIndexRegisters(((RegisterPairParameter) targetparameter).getRegisterPair(),
          masks.get(4).getValue());
      if (result != null
          && ((sourceIndex = getRegisterPPIndex(sourceparameter)) != -1
          || (sourceIndex = getRegisterRRIndex(sourceparameter)) != -1)) {
        result[1] |= (byte) ((sourceIndex << 4) & 0x30);
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
