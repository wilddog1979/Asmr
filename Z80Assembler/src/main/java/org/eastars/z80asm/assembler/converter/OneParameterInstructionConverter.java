package org.eastars.z80asm.assembler.converter;

import lombok.Builder;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eastars.z80asm.utilities.Utilities.concatenate;

public class OneParameterInstructionConverter extends AbstractZ80InstructionConverter<OneParameterInstruction> {

  @Builder
  record InstructionEntry<T extends Z80Instruction>(Class<? extends T> instruction,
                                                    List<MaskedOpcode<T>> masks,
                                                    InstructionAssemblyGenerator<T> generator) {
    InstructionEntry(
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
  interface InstructionAssemblyGenerator<T extends Z80Instruction> {

    byte[] generate(
        CompilationContext compilationContext,
        Parameter parameter,
        List<MaskedOpcode<T>> masks);

  }

  private static final List<InstructionEntry<OneParameterInstruction>> instructionlist = concatenate(
      OneParameterSUBANDXORORCPConverter.getInstructionList(),
      OneParameterINCDECConverter.getInstructionList(),
      OneParameterDJNZConverter.getInstructionList(),
      OneParameterPUSHPOPConverter.getInstructionList(),
      OneParameterRETConverter.getInstructionList(),
      OneParameterBitRotatingConverter.getInstructionList(),
      OneParameterRSTConverter.getInstructionList());

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

}
