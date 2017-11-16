package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.INC;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.INCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionINCDECparametersContext;

public class INCVisitor extends INCDECVisitor<INC, INCContext> {

	@Override
	protected INC getInstruction() {
		return new INC();
	}
	
	@Override
	protected InstructionINCDECparametersContext getInstructionParameters(INCContext ctx) {
		return ctx.instructionINCDECparameters();
	}
	
}
