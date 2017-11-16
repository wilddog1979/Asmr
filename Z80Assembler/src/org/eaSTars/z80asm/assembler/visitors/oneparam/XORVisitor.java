package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.ast.instructions.oneparam.XOR;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.XORContext;

public class XORVisitor extends SUBANDXORORCPVisitor<XOR, XORContext> {

	@Override
	protected XOR getInstruction() {
		return new XOR();
	}

	@Override
	protected InstructionSUBANDXORORCPparametersContext getInstructionParameters(XORContext ctx) {
		return ctx.instructionSUBANDXORORCPparameters();
	}
	
}
