package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.JP;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstruictionJPparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.JPContext;

public class JPVisitor extends TwoParameterInstructionVisitor<JP, JPContext, InstruictionJPparametersContext> {

	@Override
	protected JP getInstruction() {
		return new JP();
	}
	
	@Override
	protected InstruictionJPparametersContext getInstructionParameters(JPContext ctx) {
		return ctx.instruictionJPparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstruictionJPparametersContext paramctx) {
		return getRegisterIndirectAddressing(paramctx.HL, RegisterPair.HL)
				.orElseGet(() -> getRegisterIndirectAddressing(paramctx.IX, RegisterPair.IX)
						.orElseGet(() -> getRegisterIndirectAddressing(paramctx.IY, RegisterPair.IY)
								.orElseGet(() -> getExpression(paramctx.hex16bits())
										.orElseGet(() -> null))));
	}
	
	@Override
	protected Parameter getTargetParameter(InstruictionJPparametersContext paramctx) {
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
