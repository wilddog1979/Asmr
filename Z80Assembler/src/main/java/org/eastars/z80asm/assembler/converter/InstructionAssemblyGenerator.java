package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.z80asm.ast.Z80Instruction;
import org.eastars.z80asm.ast.parameter.Parameter;

import java.util.List;

public interface InstructionAssemblyGenerator<T extends Z80Instruction> {

  byte[] generate(
      CompilationContext compilationContext,
      List<Parameter> parameters,
      List<MaskedOpcode<T>> masks);

}
