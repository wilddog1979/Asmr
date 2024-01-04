package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.JP;
import org.eastars.z80asm.ast.parameter.*;

import java.util.List;

public class TwoParametersJPConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(JP.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {(byte) 0xe9})
                    .extractor((r, v) -> r.setSource(new RegisterIndirectAddressingParameter(RegisterPair.HL))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xe9})
                    .extractor((r, v) ->
                        r.setSource(new RegisterIndirectAddressingParameter((v[0] & 0x20) == 0x00
                            ? RegisterPair.IX : RegisterPair.IY))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xc7, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xc2, 0x00, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x07))
                        .setSource(reverseImmediate16(v[2], v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00, 0x00})
                    .value(new byte[] {(byte) 0xc3, 0x00, 0x00})
                    .extractor((r, v) -> r.setSource(reverseImmediate16(v[2], v[1]))).build()
            ))
            .generator(TwoParametersJPConverter::generate).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    if (targetparameter == null) {
      if (sourceparameter instanceof RegisterIndirectAddressingParameter registerIndirectAddressingParameter) {
        RegisterPair registerPair = registerIndirectAddressingParameter.getRegisterPair();
        if (registerPair == RegisterPair.HL) {
          result = selectMask(masks.get(0));
        } else {
          result = generateIndexRegisters(registerPair, masks.get(1).getValue());
        }
      } else if (sourceparameter instanceof ExpressionParameter) {
        result = generateWithExpressionValue(
            compilationContext, selectMask(masks.get(3)), (ExpressionParameter) sourceparameter, 1);
      }
    } else if (targetparameter instanceof ConditionParameter
        && sourceparameter instanceof ExpressionParameter) {
      result = generateWithExpressionValue(
          compilationContext, selectMask(masks.get(2)), (ExpressionParameter) sourceparameter, 1);
      Condition condition = ((ConditionParameter) targetparameter).getCondition();
      result[0] |= (byte) (condition.getOpcode() << 3);
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
