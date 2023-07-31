package org.eaSTars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class TwoParameterInstructionVisitor<T extends TwoParameterInstruction, C extends InstructionContext, P extends ParserRuleContext> extends ParameterizedVisitor<T, C, P> {

	protected abstract Parameter getSourceParameter(P paramCtx);
	
	protected abstract Parameter getTargetParameter(P paramCtx);
	
	public T visitInstruction(ParseTree t,  Class<C> type) {
		C ctx = type.cast(t);
		T instruction = null;
		
		P paramCtx = getInstructionParameters(ctx);
		if (paramCtx.exception == null) {
			instruction = getInstruction();
			instruction.setSource(getSourceParameter(paramCtx));
			instruction.setTarget(getTargetParameter(paramCtx));
		}
		
		return instruction;
	}
}
