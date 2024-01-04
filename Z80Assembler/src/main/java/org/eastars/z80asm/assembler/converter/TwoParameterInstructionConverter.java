package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eastars.z80asm.utilities.Utilities.concatenate;

public class TwoParameterInstructionConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

  private static final List<InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> instructionlist = concatenate(
          TwoParameterADCSBCConverter.getInstructionList(),
          TwoParametersADDConverter.getInstructionList(),
          TwoParametersBITRESSETConverter.getInstructionList(),
          TwoParametersCALLConverter.getInstructionList(),
          TwoParametersEXConverter.getInstructionList(),
          TwoParametersINOUTConverter.getInstructionList(),
          TwoParametersJPConverter.getInstructionList(),
          TwoParametersJRConverter.getInstructionList(),
          TwoParametersLDConverter.getInstructionList());
  
  private static final Map<
      Class<? extends TwoParameterInstruction>, InstructionEntry<TwoParameterInstruction,
      TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>> instructions =
      instructionlist.stream().collect(Collectors.toMap(InstructionEntry::instruction, e -> e));
  
  private static final Map<Integer, MaskedOpcodeMap<TwoParameterInstruction>> reverse =
      instructionlist.stream().flatMap(e -> e.masks().stream()).collect(Collectors.groupingBy(
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
    InstructionEntry<TwoParameterInstruction, TwoParametersInstructionAssemblyGenerator<TwoParameterInstruction>>
        entry = instructions.get(instruction.getClass());
    if (entry != null && entry.generator() != null) {
      result = entry.generator().generate(
          compilationContext, new TwoParameters(instruction.getTarget(), instruction.getSource()), entry.masks());
    }
    return result;
  }
  
}
