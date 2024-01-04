package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;

import java.util.List;

@FunctionalInterface
public interface TwoParametersInstructionAssemblyGenerator<T extends Z80Instruction>
    extends InstructionAssemblyGenerator {

  byte[] generate(
      CompilationContext compilationContext,
      TwoParameters parameters,
      List<MaskedOpcode<T>> masks);

}
