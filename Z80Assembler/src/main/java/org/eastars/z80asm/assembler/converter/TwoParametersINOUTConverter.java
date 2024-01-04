package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.IN;
import org.eastars.z80asm.ast.instructions.twoparam.OUT;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.Register;
import org.eastars.z80asm.ast.parameter.RegisterParameter;

import java.util.List;

import static org.eastars.z80asm.assembler.converter.Check.checkRegisterParameter;

public class TwoParametersINOUTConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(IN.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xdb, 0x00})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.A))
                        .setSource(reverseImmediate8(v[1]))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xc7})
                    .value(new byte[] {(byte) 0xed, 0x40})
                    .extractor((r, v) -> r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07))
                        .setSource(new RegisterParameter(Register.C))).build()
            ))
            .generator((c, p, m) -> generate(c, new TwoParameters(p.target(), p.source()), m)).build(),
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(OUT.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, 0x00})
                    .value(new byte[] {(byte) 0xd3, 0x00})
                    .extractor((r, v) -> r.setTarget(reverseImmediate8(v[1]))
                        .setSource(new RegisterParameter(Register.A))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff, (byte) 0xc7})
                    .value(new byte[] {(byte) 0xed, 0x41})
                    .extractor((r, v) -> r.setTarget(new RegisterParameter(Register.C))
                        .setSource(reverseRegisterR((v[1] >> 3) & 0x07))).build()
            ))
            .generator((c, p, m) -> generate(c, new TwoParameters(p.source(), p.target()), m)).build()
    );
  }

  private static byte[] generate(
      CompilationContext compilationContext,
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    int targetIndex = -1;
    if (sourceparameter instanceof ExpressionParameter
        && checkRegisterParameter(targetparameter, Register.A)) {
      result = selectMask(masks.get(0));
      result[1] = (byte) ((ExpressionParameter) sourceparameter).getExpressionValue(compilationContext);
    } else if (checkRegisterParameter(sourceparameter, Register.C)
        && (targetIndex = getRegisterRIndex(targetparameter)) != -1) {
      result = selectMask(masks.get(1));
      result[1] |= (byte) ((targetIndex << 3) & 0x38);
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
