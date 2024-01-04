package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.BIT;
import org.eastars.z80asm.ast.instructions.twoparam.RES;
import org.eastars.z80asm.ast.instructions.twoparam.SET;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public class TwoParametersBITRESSETConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(BIT.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xc0})
                    .value(new byte[] {(byte) 0xcb, 0x40})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07))
                        .setSource(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, 0x46})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
            ))
            .generator(TwoParametersBITRESSETConverter::generate).build(),
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(RES.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xc0})
                    .value(new byte[] {(byte) 0xcb, (byte) 0x80})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07))
                        .setSource(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0x86})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
            ))
            .generator(TwoParametersBITRESSETConverter::generate).build(),
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(SET.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xc0})
                    .value(new byte[] {(byte) 0xcb, (byte) 0xc0})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07))
                        .setSource(reverseRegisterRH(v[1] & 0x07))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0xc6})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                        .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))).build()
            ))
            .generator(TwoParametersBITRESSETConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    if (targetparameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter) targetparameter).getExpressionValue(compilationContext);
      if (value >= 0 && value <= 7) {
        int sourceIndex = getRegisterRHIndex(twoParameters.source());
        if (sourceIndex != -1) {
          result = selectMask(masks.get(0));
          result[1] |= (byte) ((value << 3) | sourceIndex);
        } else if (twoParameters.source() instanceof IndexedAddressingParameter) {
          result = generateIndexedAddressing(
              compilationContext, (IndexedAddressingParameter) twoParameters.source(), masks.get(1).getValue());
          if (result != null) {
            result[3] |= (byte) (value << 3);
          }
        }
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
