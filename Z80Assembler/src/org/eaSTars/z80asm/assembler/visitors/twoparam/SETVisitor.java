package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.ast.instructions.twoparam.SET;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBITRESSETparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SETContext;

public class SETVisitor extends BITRESSETVisitor<SET, SETContext> {

	@Override
	protected SET getInstruction() {
		return new SET();
	}
	
	@Override
	protected InstructionBITRESSETparametersContext getInstructionParameters(SETContext ctx) {
		return ctx.instructionBITRESSETparameters();
	}

}
