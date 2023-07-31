package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.RL;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RLContext;

public class RLVisitor extends BitRotatingVisitor<RL, RLContext> {

	@Override
	protected RL getInstruction() {
		return new RL();
	}
	
	@Override
	protected InstructionBitRotatingparametersContext getInstructionParameters(RLContext ctx) {
		return ctx.instructionBitRotatingparameters();
	}

}
