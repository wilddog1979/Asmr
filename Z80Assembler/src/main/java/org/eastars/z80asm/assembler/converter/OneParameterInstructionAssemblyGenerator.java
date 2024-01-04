package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;

import java.util.List;

@FunctionalInterface
public interface OneParameterInstructionAssemblyGenerator<T extends Z80Instruction>
    extends InstructionAssemblyGenerator {

  byte[] generate(
      CompilationContext compilationContext,
      OneParameter parameters,
      List<MaskedOpcode<T>> masks);

}
