package org.eastars.z80asm.assembler.converter;

import lombok.Builder;
import org.eastars.z80asm.ast.Z80Instruction;

import java.util.List;

@Builder
public record InstructionEntry<T extends Z80Instruction, P extends InstructionAssemblyGenerator>(
    Class<? extends T> instruction, List<MaskedOpcode<T>> masks, P generator) {

  public InstructionEntry(Class<? extends T> instruction, List<MaskedOpcode<T>> masks, P generator) {
    this.instruction = instruction;
    this.masks = masks;
    masks.forEach(m -> m.setInstruction(instruction));
    this.generator = generator;
  }

}
