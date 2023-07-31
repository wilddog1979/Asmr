package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.SLA;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SLAContext;

public class SLAVisitor extends BitRotatingVisitor<SLA, SLAContext> {

	@Override
	protected SLA getInstruction() {
		return new SLA();
	}
	
	@Override
	protected InstructionBitRotatingparametersContext getInstructionParameters(SLAContext ctx) {
		return ctx.instructionBitRotatingparameters();
	}

}
