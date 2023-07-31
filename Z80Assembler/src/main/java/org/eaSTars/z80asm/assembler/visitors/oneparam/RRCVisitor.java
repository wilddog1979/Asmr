package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.RRC;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RRCContext;

public class RRCVisitor extends BitRotatingVisitor<RRC, RRCContext> {

	@Override
	protected RRC getInstruction() {
		return new RRC();
	}
	
	@Override
	protected InstructionBitRotatingparametersContext getInstructionParameters(RRCContext ctx) {
		return ctx.instructionBitRotatingparameters();
	}

}
