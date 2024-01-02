package org.eastars.z80asm.assembler.converter;

import lombok.Builder;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.*;
import org.eastars.z80asm.ast.parameter.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eastars.z80asm.assembler.converter.Check.*;

public class TwoParameterInstructionConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

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
        Parameter targetparameter,
        Parameter sourceparameter,
        List<MaskedOpcode<T>> masks);
    
  }

  private static final List<InstructionEntry<TwoParameterInstruction>> instructionlist = Arrays.asList(
    new InstructionEntry<TwoParameterInstruction>(ADC.class, Arrays.asList(
      new MaskedOpcode<>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x88}, (r, v) ->
          r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
      new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xce, 0x00}, (r, v) ->
          r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
      new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x4a}, (r, v) ->
          r.setTarget(new RegisterPairParameter(RegisterPair.HL))
              .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
      new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x8e, 0x00},
          (r, v) -> r.setTarget(new RegisterParameter(Register.A))
              .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
      ), TwoParameterInstructionConverter::generateADCSBC),
    new InstructionEntry<TwoParameterInstruction>(ADD.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xcf}, new byte[] {0x09}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                .setSource(reverseRegisterSS((v[0] >> 4) & 0x03))),
        new MaskedOpcode<>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x80}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xc6, 0x00}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x86, 0x00},
            (r, v) -> r.setTarget(new RegisterParameter(Register.A))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xcf}, new byte[] {(byte) 0xdd, 0x09}, (r, v) ->
            r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00))
                .setSource(reverseRegisterPPRR((v[0] & 0x20) == 0x00, (v[1] >> 4) & 0x03)))
        ), TwoParameterInstructionConverter::generateADD),
    new InstructionEntry<TwoParameterInstruction>(BIT.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, 0x40}, (r, v) ->
            r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd,
            (byte) 0xcb, 0x00, 0x46}, (r, v) ->
            r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
        ), TwoParameterInstructionConverter::generateBITRESSET),
    new InstructionEntry<TwoParameterInstruction>(CALL.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xc7, 0x00, 0x00}, new byte[] {(byte) 0xc4, 0x00, 0x00}, (r, v) ->
            r.setTarget(reverseCondition((v[0] >> 3) & 0x07)).setSource(reverseImmediate16(v[2], v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xcd, 0x00, 0x00}, (r, v) ->
            r.setSource(reverseImmediate16(v[2], v[1])))
        ), TwoParameterInstructionConverter::generateCALL),
    new InstructionEntry<TwoParameterInstruction>(EX.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {0x08}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.AF))
                .setSource(new RegisterPairParameter(RegisterPair.AFMarked))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xeb}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.DE))
                .setSource(new RegisterPairParameter(RegisterPair.HL))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xe3}, (r, v) ->
            r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.SP))
                .setSource(new RegisterPairParameter(RegisterPair.HL))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe3}, (r, v) ->
            r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.SP))
                .setSource(reverseIXIY((v[0] & 0x20) == 0x00)))), (c, t, s, m) -> generateEX(t, s, m)),
    new InstructionEntry<TwoParameterInstruction>(IN.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xdb, 0x00}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xed, 0x40}, (r, v) ->
            r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07)).setSource(new RegisterParameter(Register.C)))
        ), TwoParameterInstructionConverter::generateIN),
    new InstructionEntry<TwoParameterInstruction>(JP.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xe9}, (r, v) ->
            r.setSource(new RegisterIndirectAddressingParameter(RegisterPair.HL))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe9}, (r, v) ->
            r.setSource(new RegisterIndirectAddressingParameter((v[0] & 0x20) == 0x00
                ? RegisterPair.IX : RegisterPair.IY))),
        new MaskedOpcode<>(new byte[] {(byte) 0xc7, 0x00, 0x00}, new byte[] {(byte) 0xc2, 0x00, 0x00}, (r, v) ->
            r.setTarget(reverseCondition((v[0] >> 3) & 0x07)).setSource(reverseImmediate16(v[2], v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xc3, 0x00, 0x00}, (r, v) ->
            r.setSource(reverseImmediate16(v[2], v[1])))
        ), TwoParameterInstructionConverter::generateJP),
    new InstructionEntry<TwoParameterInstruction>(JR.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xe7, 0x00}, new byte[] {0x20, 0x00}, (r, v) ->
            r.setTarget(reverseCondition((v[0] >> 3) & 0x03)).setSource(reverseImmediate8(v[1] + 2))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {0x18, 0x00}, (r, v) ->
            r.setSource(reverseImmediate8(v[1] + 2)))
        ), TwoParameterInstructionConverter::generateJR),
    new InstructionEntry<TwoParameterInstruction>(LD.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {0x02}, (r, v) ->
            r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.BC))
                .setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {0x12}, (r, v) ->
            r.setTarget(new RegisterIndirectAddressingParameter(RegisterPair.DE))
                .setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {0x0a}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A))
                .setSource(new RegisterIndirectAddressingParameter(RegisterPair.BC))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {0x1a}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A))
                .setSource(new RegisterIndirectAddressingParameter(RegisterPair.DE))),
        new MaskedOpcode<>(new byte[] {(byte) 0xc0}, new byte[] {0x40}, (r, v) -> {
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
        }),
        new MaskedOpcode<>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xf9}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.SP))
                .setSource(new RegisterPairParameter(RegisterPair.HL))),
        new MaskedOpcode<>(new byte[] {(byte) 0xc7, 0x00}, new byte[] {0x06, 0x00}, (r, v) ->
            r.setTarget(reverseRegisterRH((v[0] >> 3) & 0x07)).setSource(reverseImmediate8(v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xf9}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.SP)).setSource(reverseIXIY((v[0] & 0x20) == 0x00))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x47}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.I)).setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x57}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterParameter(Register.I))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x4f}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.R)).setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x5f}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterParameter(Register.R))),
        new MaskedOpcode<>(new byte[] {(byte) 0xcf, 0x00, 0x00}, new byte[] {0x01, 0x00, 0x00}, (r, v) ->
            r.setTarget(reverseRegisterSS((v[0] >> 4) & 0x03)).setSource(reverseImmediate16(v[2], v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x22, 0x00, 0x00}, (r, v) ->
            r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))
                .setSource(new RegisterPairParameter(RegisterPair.HL))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x2a, 0x00, 0x00}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x32, 0x00, 0x00}, (r, v) ->
            r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))
                .setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x3a, 0x00, 0x00}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A))
                .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xf8, 0x00}, new byte[] {(byte) 0xdd, 0x70, 0x00}, (r, v) ->
            r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))
                .setSource(reverseRegisterR(v[1] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xc7, 0x00}, new byte[] {(byte) 0xdd, 0x46, 0x00}, (r, v) ->
            r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x34,
            0x00, 0x00}, (r, v) ->
            r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])).setSource(reverseImmediate8(v[3]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00}, new byte[] {(byte) 0xed, (byte) 0x43,
            0x00, 0x00}, (r, v) ->
            r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))
                .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00}, new byte[] {(byte) 0xed, (byte) 0x4b,
            0x00, 0x00}, (r, v) ->
            r.setTarget(reverseRegisterSS((v[1] >> 4) & 0x03))
                .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x21,
            0x00, 0x00}, (r, v) ->
            r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00)).setSource(reverseImmediate16(v[3], v[2]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x22,
            0x00, 0x00}, (r, v) ->
            r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))
                .setSource(reverseIXIY((v[0] & 0x20) == 0x00))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x2a,
            0x00, 0x00}, (r, v) ->
            r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00))
                .setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2]))))
        ), TwoParameterInstructionConverter::generateLD),
    new InstructionEntry<TwoParameterInstruction>(OUT.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xd3, 0x00}, (r, v) ->
            r.setTarget(reverseImmediate8(v[1])).setSource(new RegisterParameter(Register.A))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xed, 0x41}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.C)).setSource(reverseRegisterR((v[1] >> 3) & 0x07)))
        ), TwoParameterInstructionConverter::generateOUT),
    new InstructionEntry<TwoParameterInstruction>(RES.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, (byte) 0x80}, (r, v) ->
            r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd,
            (byte) 0xcb, 0x00, (byte) 0x86}, (r, v) ->
            r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
        ), TwoParameterInstructionConverter::generateBITRESSET),
    new InstructionEntry<TwoParameterInstruction>(SBC.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x98}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xde, 0x00}, (r, v) ->
            r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x42}, (r, v) ->
            r.setTarget(new RegisterPairParameter(RegisterPair.HL))
                .setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x9e, 0x00},
            (r, v) -> r.setTarget(new RegisterParameter(Register.A))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
        ), TwoParameterInstructionConverter::generateADCSBC),
    new InstructionEntry<>(SET.class, Arrays.asList(
        new MaskedOpcode<>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, (byte) 0xc0}, (r, v) ->
            r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
        new MaskedOpcode<>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd,
            (byte) 0xcb, 0x00, (byte) 0xc6}, (r, v) ->
            r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07))
                .setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
        ), TwoParameterInstructionConverter::generateBITRESSET));
  
  private static final Map<
      Class<? extends TwoParameterInstruction>, InstructionEntry<TwoParameterInstruction>> instructions =
      instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e));
  
  private static final Map<Integer, MaskedOpcodeMap<TwoParameterInstruction>> reverse =
      instructionlist.stream().flatMap(e -> e.masks.stream()).collect(Collectors.groupingBy(
          m -> m.getMask().length,
          Collectors.toMap(
              m -> new OpcodeMask<>(m.getMask(), m.getValue(), m.getExtractor()),
              MaskedOpcode::getInstruction,
              (u, v) -> {
                throw new IllegalStateException(String.format("Duplicate key %s", u));
              },
              MaskedOpcodeMap<TwoParameterInstruction>::new)));
  
  @Override
  protected MaskedOpcodeMap<TwoParameterInstruction> getReverse(int index) {
    return reverse.get(index);
  }

  @Override
  public byte[] convert(CompilationContext compilationContext, TwoParameterInstruction instruction) {
    byte[] result = null;
    InstructionEntry<TwoParameterInstruction> entry = instructions.get(instruction.getClass());
    if (entry != null && entry.generator != null) {
      result = entry.generator
          .generate(compilationContext, instruction.getTarget(), instruction.getSource(), entry.masks);
    }
    return result;
  }

  private static byte[] generateADCSBC(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
    if (checkRegisterParameter(targetparameter, Register.A)) {
      int sourceIndex = getRegisterRHIndex(sourceparameter);
      if (sourceIndex != -1) {
        result = selectMask(masks.get(0));
        result[0] |= (byte) (sourceIndex & 0x07);
      } else if (sourceparameter instanceof ExpressionParameter) {
        result = selectMask(masks.get(1));
        result[1] = (byte) ((ExpressionParameter) sourceparameter).getExpressionValue(compilationContext);
      } else if (sourceparameter instanceof IndexedAddressingParameter) {
        result = generateIndexedAddressing(
            compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(3).getValue());
      }
    } else if (checkRegisterPairParameter(targetparameter, RegisterPair.HL)) {
      int sourceIndex = getRegisterSSIndex(sourceparameter);
      if (sourceIndex != -1) {
        result = selectMask(masks.get(2));
        result[1] |= (byte) (sourceIndex << 4);
      }
    }
    
    return result;
  }
  
  private static byte[] generateBITRESSET(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
    if (targetparameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter) targetparameter).getExpressionValue(compilationContext);
      if (value >= 0 && value <= 7) {
        int sourceIndex = getRegisterRHIndex(sourceparameter);
        if (sourceIndex != -1) {
          result = selectMask(masks.get(0));
          result[1] |= (byte) ((value << 3) | sourceIndex);
        } else if (sourceparameter instanceof IndexedAddressingParameter) {
          result = generateIndexedAddressing(
              compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(1).getValue());
          if (result != null) {
            result[3] |= (byte) (value << 3);
          }
        }
      }
    }
    
    return result;
  }
  
  private static byte[] generateCALL(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
    if (sourceparameter instanceof ExpressionParameter) {
      if (targetparameter instanceof ConditionParameter) {
        result = generateWithExpressionValue(
            compilationContext, selectMask(masks.get(0)), (ExpressionParameter) sourceparameter, 1);
        Condition condition = ((ConditionParameter) targetparameter).getCondition();
        result[0] |= (byte) (condition.getOpcode() << 3);
      } else if (targetparameter == null) {
        result = generateWithExpressionValue(
            compilationContext, selectMask(masks.get(1)), (ExpressionParameter) sourceparameter, 1);
      }
    }
    
    return result;
  }
  
  private static byte[] generateJP(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
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
  
  private static byte[] generateJR(
      CompilationContext compilationContext,
      Parameter targetParameter,
      Parameter sourceParameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
    if (targetParameter instanceof ConditionParameter
        && sourceParameter instanceof ExpressionParameter) {
      Condition condition = ((ConditionParameter) targetParameter).getCondition();
      int value = ((ExpressionParameter) sourceParameter).getExpressionValue(compilationContext) - 2;
      if (condition == Condition.NZ || condition == Condition.Z
          || condition == Condition.NC || condition == Condition.C) {
        result = selectMask(masks.get(0));
        result[0] |= (byte) (condition.getOpcode() << 3);
        result[1] = (byte) (value & 0xff);
      }
    } else if (sourceParameter instanceof ExpressionParameter) {
      int value = ((ExpressionParameter) sourceParameter).getExpressionValue(compilationContext) - 2;
      result = selectMask(masks.get(1));
      result[1] = (byte) (value & 0xff);
    }
    
    return result;
  }
  
  private static byte[] generateIN(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    return generateINOUT(compilationContext, targetparameter, sourceparameter, masks);
  }
  
  private static byte[] generateOUT(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    return generateINOUT(compilationContext, sourceparameter, targetparameter, masks);
  }

  private static byte[] generateINOUT(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

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
  
  private static byte[] generateEX(
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;

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
  
  private static byte[] generateADD(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
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
  
  private static byte[] generateLD(
      CompilationContext compilationContext,
      Parameter targetparameter,
      Parameter sourceparameter,
      List<MaskedOpcode<TwoParameterInstruction>> masks) {
    byte[] result = null;
    
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
  
  private static byte[] generateWithImmediateValue(
      CompilationContext compilationContext,
      byte[] result,
      Parameter parameter,
      int idx) {
    return generateWithExpressionValue(
        compilationContext, result, ((ImmediateAddressingParameter) parameter).getValue(), idx);
  }

  private static byte[] generateWithExpressionValue(
      CompilationContext compilationContext,
      byte[] result,
      ExpressionParameter parameter,
      int idx) {
    int value = parameter.getExpressionValue(compilationContext);
    result[idx] = (byte) (value & 0xff);
    result[idx + 1] = (byte) ((value >> 8) & 0xff);
    return result;
  }
  
}
