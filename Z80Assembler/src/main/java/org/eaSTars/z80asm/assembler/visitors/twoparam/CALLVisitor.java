package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.CALL;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.CALLContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionCALLparametersContext;

public class CALLVisitor extends TwoParameterInstructionVisitor<CALL, CALLContext, InstructionCALLparametersContext> {

	@Override
	protected CALL getInstruction() {
		return new CALL();
	}
	
	@Override
	protected InstructionCALLparametersContext getInstructionParameters(CALLContext ctx) {
		return ctx.instructionCALLparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionCALLparametersContext paramctx) {
		return getExpression(paramctx.hex16bits()).orElseGet(() -> null);
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionCALLparametersContext paramctx) {
		Parameter parameter = null;

		if (paramctx.NZ != null) {
			parameter = new ConditionParameter(Condition.NZ);
		} else if (paramctx.Z != null) {
			parameter = new ConditionParameter(Condition.Z);
		} else if (paramctx.NC != null) {
			parameter = new ConditionParameter(Condition.NC);
		} else if (paramctx.C != null) {
			parameter = new ConditionParameter(Condition.C);
		} else if (paramctx.PO != null) {
			parameter = new ConditionParameter(Condition.PO);
		} else if (paramctx.PE != null) {
			parameter = new ConditionParameter(Condition.PE);
		} else if (paramctx.P != null) {
			parameter = new ConditionParameter(Condition.P);
		} else if (paramctx.M != null) {
			parameter = new ConditionParameter(Condition.M);
		}

		return parameter;
	}

}
