package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.OUT;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionOUTparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.OUTContext;

public class OUTVisitor extends TwoParameterInstructionVisitor<OUT, OUTContext, InstructionOUTparametersContext> {

	@Override
	protected OUT getInstruction() {
		return new OUT();
	}
	
	@Override
	protected InstructionOUTparametersContext getInstructionParameters(OUTContext ctx) {
		return ctx.instructionOUTparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionOUTparametersContext paramctx) {
		return getRegister(paramctx.A, Register.A)
				.orElseGet(() -> getRegisters(paramctx.registers())
						.orElseGet(() -> null));
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionOUTparametersContext paramctx) {
		return getRegister(paramctx.C, Register.C)
				.orElseGet(() -> getExpression(paramctx.hex8bits())
						.orElseGet(() -> null));
	}

}
