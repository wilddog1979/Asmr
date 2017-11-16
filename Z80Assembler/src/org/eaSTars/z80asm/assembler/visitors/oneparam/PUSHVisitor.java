package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.PUSH;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionPUSHPOPparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.PUSHContext;

public class PUSHVisitor extends PUSHPOPVisitor<PUSH, PUSHContext> {

	@Override
	protected PUSH getInstruction() {
		return new PUSH();
	}
	
	@Override
	protected InstructionPUSHPOPparametersContext getInstructionParameters(PUSHContext ctx) {
		return ctx.instructionPUSHPOPparameters();
	}

}
