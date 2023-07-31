package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.oneparam.RET;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionRETparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RETContext;

public class RETVisitor extends OneParameterVisitor<RET, RETContext, InstructionRETparametersContext> {

	@Override
	protected RET getInstruction() {
		return new RET();
	}
	
	@Override
	protected InstructionRETparametersContext getInstructionParameters(RETContext ctx) {
		return ctx.instructionRETparameters();
	}
	
	@Override
	protected Parameter getParameter(InstructionRETparametersContext paramCtx) {
		Parameter parameter = null;
		
		if (paramCtx.NZ != null) {
			parameter = new ConditionParameter(Condition.NZ);
		} else if (paramCtx.Z != null) {
			parameter = new ConditionParameter(Condition.Z);
		} else if (paramCtx.NC != null) {
			parameter = new ConditionParameter(Condition.NC);
		} else if (paramCtx.C != null) {
			parameter = new ConditionParameter(Condition.C);
		} else if (paramCtx.PO != null) {
			parameter = new ConditionParameter(Condition.PO);
		} else if (paramCtx.PE != null) {
			parameter = new ConditionParameter(Condition.PE);
		} else if (paramCtx.P != null) {
			parameter = new ConditionParameter(Condition.P);
		} else if (paramCtx.M != null) {
			parameter = new ConditionParameter(Condition.M);
		}
		
		return parameter;
	}

}
