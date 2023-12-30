package org.eastars.z80asm.assembler.visitors.oneparam;

import org.eastars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;

public abstract class SUBANDXORORCPVisitor<T extends OneParameterInstruction, C extends InstructionContext> extends OneParameterVisitor<T, C, InstructionSUBANDXORORCPparametersContext> {

  @Override
  protected Parameter getParameter(InstructionSUBANDXORORCPparametersContext paramCtx) {
    return getRegistersWithReference(paramCtx.registersWithReference())
        .orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
            .orElseGet(() -> getExpression(paramCtx.hex8bits())
                .orElseGet(() -> null)));
  }

}
