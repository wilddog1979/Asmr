package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.ADC;
import org.eastars.z80asm.ast.instructions.twoparam.SBC;
import org.eastars.z80asm.ast.parameter.*;

import java.util.List;

import static org.eastars.z80asm.assembler.converter.Check.checkRegisterPairParameter;
import static org.eastars.z80asm.assembler.converter.Check.checkRegisterParameter;

public class TwoParameterADCSBCConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(ADC.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0x88})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xce, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xcf})
                    .value(new byte[] {(byte) 0xed, 0x4a})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                        .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x8e, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
            ))
            .generator(TwoParameterADCSBCConverter::generate).build(),
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(SBC.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xf8})
                    .value(new byte[] {(byte) 0x98})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseRegisterRH(v[0] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xde, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xcf})
                    .value(new byte[] {(byte) 0xed, 0x42})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                        .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdd, (byte) 0x9e, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
            ))
            .generator(TwoParameterADCSBCConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetParameter = twoParameters.target();
    if (checkRegisterParameter(targetParameter, Register.A)) {
      int sourceIndex = getRegisterRHIndex(twoParameters.source());
      if (sourceIndex != -1) {
        result = selectMask(masks.get(0));
        result[0] |= (byte) (sourceIndex & 0x07);
      } else if (twoParameters.source() instanceof ExpressionParameter) {
        result = selectMask(masks.get(1));
        result[1] = (byte) ((ExpressionParameter) twoParameters.source()).getExpressionValue(compilationContext);
      } else if (twoParameters.source() instanceof IndexedAddressingParameter) {
        result = generateIndexedAddressing(
            compilationContext, (IndexedAddressingParameter) twoParameters.source(), masks.get(3).getValue());
      }
    } else if (checkRegisterPairParameter(targetParameter, RegisterPair.HL)) {
      int sourceIndex = getRegisterSSIndex(twoParameters.source());
      if (sourceIndex != -1) {
        result = selectMask(masks.get(2));
        result[1] |= (byte) (sourceIndex << 4);
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
