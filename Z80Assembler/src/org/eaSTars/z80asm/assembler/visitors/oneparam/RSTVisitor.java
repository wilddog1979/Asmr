package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.oneparam.RST;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionRSTparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RSTContext;

public class RSTVisitor extends OneParameterVisitor<RST, RSTContext, InstructionRSTparametersContext> {

	@Override
	protected RST getInstruction() {
		return new RST();
	}
	
	@Override
	protected InstructionRSTparametersContext getInstructionParameters(RSTContext ctx) {
		return ctx.instructionRSTparameters();
	}
	
	@Override
	protected Parameter getParameter(InstructionRSTparametersContext paramctx) {
		return getParameterT(paramctx.parameterT())
				.orElseGet(() -> null);
	}

}
