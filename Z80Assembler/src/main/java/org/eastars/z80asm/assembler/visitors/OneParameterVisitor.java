package org.eastars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eastars.z80asm.ast.instructions.OneParameterInstruction;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class OneParameterVisitor<T extends OneParameterInstruction, C extends InstructionContext,
    P extends ParserRuleContext> extends ParameterizedVisitor<T, C, P> {
  
  protected abstract Parameter getParameter(P paramCtx);
  
  public T visitInstruction(ParseTree t,  Class<C> type) {
    C ctx = type.cast(t);
    T instruction = null;
    
    P paramCtx = getInstructionParameters(ctx);
    if (paramCtx.exception == null) {
      instruction = getInstruction();
      instruction.setParameter(getParameter(paramCtx));
    }
    
    return instruction;
  }
  
}
