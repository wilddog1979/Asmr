package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.IN;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.INContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionINparametersContext;

public class INVisitor extends TwoParameterInstructionVisitor<IN, INContext, InstructionINparametersContext> {

	@Override
	protected IN getInstruction() {
		return new IN();
	}
	
	@Override
	protected InstructionINparametersContext getInstructionParameters(INContext ctx) {
		return ctx.instructionINparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionINparametersContext paramctx) {
		return getRegister(paramctx.C, Register.C)
				.orElseGet(() -> getExpression(paramctx.hex8bits())
						.orElseGet(() -> null));
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionINparametersContext paramctx) {
		return getRegister(paramctx.A, Register.A)
				.orElseGet(() -> getRegisters(paramctx.registers())
						.orElseGet(() -> null));
	}

}
