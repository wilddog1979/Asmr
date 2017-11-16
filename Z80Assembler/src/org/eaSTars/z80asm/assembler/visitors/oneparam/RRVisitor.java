package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.RR;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RRContext;

public class RRVisitor extends BitRotatingVisitor<RR, RRContext> {

	@Override
	protected RR getInstruction() {
		return new RR();
	}
	
	@Override
	protected InstructionBitRotatingparametersContext getInstructionParameters(RRContext ctx) {
		return ctx.instructionBitRotatingparameters();
	}

}
