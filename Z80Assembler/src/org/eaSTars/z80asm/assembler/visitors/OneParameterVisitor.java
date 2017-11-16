package org.eaSTars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class OneParameterVisitor<T extends OneParameterInstruction, C extends InstructionContext, P extends ParserRuleContext> extends ParameterizedVisitor<T, C, P> {
	
	protected abstract Parameter getParameter(P paramctx);
	
	public T visitInstruction(ParseTree t,  Class<C> type) {
		C ctx = type.cast(t);
		T instruction = null;
		
		P paramctx = getInstructionParameters(ctx);
		if (paramctx.exception == null) {
			instruction = getInstruction();
			instruction.setParameter(getParameter(paramctx));
		}
		
		return instruction;
	}
	
}
