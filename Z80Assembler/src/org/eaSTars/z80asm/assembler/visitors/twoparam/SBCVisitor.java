package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.ast.instructions.twoparam.SBC;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionADCSBCparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SBCContext;

public class SBCVisitor extends ADCSBCVisitor<SBC, SBCContext> {

	@Override
	protected SBC getInstruction() {
		return new SBC();
	}
	
	@Override
	protected InstructionADCSBCparametersContext getInstructionParameters(SBCContext ctx) {
		return ctx.instructionADCSBCparameters();
	}
	
}
