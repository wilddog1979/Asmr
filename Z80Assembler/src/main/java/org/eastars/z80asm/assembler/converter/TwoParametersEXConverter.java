package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.EX;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.RegisterIndirectAddressingParameter;
import org.eastars.z80asm.ast.parameter.RegisterPair;
import org.eastars.z80asm.ast.parameter.RegisterPairParameter;

import java.util.List;

import static org.eastars.z80asm.assembler.converter.Check.checkRegisterIndirectAddressing;
import static org.eastars.z80asm.assembler.converter.Check.checkRegisterPairParameter;

public class TwoParametersEXConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  public static List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> getInstructionList() {
    return List.of(
        InstructionEntry
            .<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>builder()
            .instruction(EX.class)
            .masks(List.of(
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {0x08})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.AF))
                        .setSource(new RegisterPairParameter(RegisterPair.AFMarked))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {(byte) 0xeb})
                    .extractor((r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.DE))
                        .setSource(new RegisterPairParameter(RegisterPair.HL))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xff})
                    .value(new byte[] {(byte) 0xe3})
                    .extractor((r, v) -> r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.SP))
                        .setSource(new RegisterPairParameter(RegisterPair.HL))).build(),
                MaskedOpcode.<TwoParameterInstruction>builder()
                    .mask(new byte[] {(byte) 0xdf, (byte) 0xff})
                    .value(new byte[] {(byte) 0xdd, (byte) 0xe3})
                    .extractor((r, v) -> r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.SP))
                        .setSource(reverseIXIY((v[0] & 0x20) == 0x00))).build()
            ))
            .generator((c, p, m) -> generate(p, m)).build()
    );
  }

  private static byte[] generate(
      TwoParameters twoParameters,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

    Parameter targetparameter = twoParameters.target();
    Parameter sourceparameter = twoParameters.source();
    if (checkRegisterPairParameter(targetparameter, RegisterPair.AF)
        && checkRegisterPairParameter(sourceparameter, RegisterPair.AFMarked)) {
      result = selectMask(masks.get(0));
    } else if (checkRegisterPairParameter(targetparameter, RegisterPair.DE)
        && checkRegisterPairParameter(sourceparameter, RegisterPair.HL)) {
      result = selectMask(masks.get(1));
    } else if (checkRegisterIndirectAddressing(targetparameter, RegisterPair.SP)) {
      if (sourceparameter instanceof RegisterPairParameter) {
        RegisterPair registerPair = ((RegisterPairParameter) sourceparameter).getRegisterPair();
        if (registerPair == RegisterPair.HL) {
          result = selectMask(masks.get(2));
        } else {
          result = generateIndexRegisters(registerPair, masks.get(3).getValue());
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
