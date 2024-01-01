package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.ast.Instruction;
import org.eastars.z80asm.ast.instructions.NoParameterInstruction;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;

public class Z80InstructionConverter {

  private static final NoParameterInstructionConverter noParameterInstructionConverter =
      new NoParameterInstructionConverter();

  private static final OneParameterInstructionConverter oneParameterInstructionConverter =
      new OneParameterInstructionConverter();

  private static final TwoParameterInstructionConverter twoParameterInstructionconverter =
      new TwoParameterInstructionConverter();

  public static byte[] convertInstruction(CompilationContext compilationContext, Instruction instruction) {
    if (instruction instanceof NoParameterInstruction) {
      return noParameterInstructionConverter.convert(compilationContext, (NoParameterInstruction) instruction);
    } else if (instruction instanceof OneParameterInstruction) {
      return oneParameterInstructionConverter.convert(compilationContext, (OneParameterInstruction) instruction);
    } else if (instruction instanceof TwoParameterInstruction) {
      return twoParameterInstructionconverter.convert(compilationContext, (TwoParameterInstruction) instruction);
    }

    return null;
  }

}
