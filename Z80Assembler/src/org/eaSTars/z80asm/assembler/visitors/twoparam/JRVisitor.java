package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.JR;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionJRparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.JRContext;

public class JRVisitor extends TwoParameterInstructionVisitor<JR, JRContext, InstructionJRparametersContext> {

	@Override
	protected JR getInstruction() {
		return new JR();
	}
	
	@Override
	protected InstructionJRparametersContext getInstructionParameters(JRContext ctx) {
		return ctx.instructionJRparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionJRparametersContext paramctx) {
		return getExpression(paramctx.hex8bits()).orElseGet(() -> null);
	}

	@Override
	protected Parameter getTargetParameter(InstructionJRparametersContext paramctx) {
		Parameter parameter = null;
		
		if (paramctx.NZ != null) {
			parameter = new ConditionParameter(Condition.NZ);
		} else if (paramctx.Z != null) {
			parameter = new ConditionParameter(Condition.Z);
		} else if (paramctx.NC != null) {
			parameter = new ConditionParameter(Condition.NC);
		} else if (paramctx.C != null) {
			parameter = new ConditionParameter(Condition.C);
		}
		
		return parameter;
	}
}
