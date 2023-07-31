package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.AND;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ANDContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;

public class ANDVisitor extends SUBANDXORORCPVisitor<AND, ANDContext> {

	@Override
	protected AND getInstruction() {
		return new AND();
	}
	
	@Override
	protected InstructionSUBANDXORORCPparametersContext getInstructionParameters(ANDContext ctx) {
		return ctx.instructionSUBANDXORORCPparameters();
	}
}
