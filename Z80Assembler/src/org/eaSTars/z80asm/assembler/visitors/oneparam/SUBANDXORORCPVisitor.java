package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionSUBANDXORORCPparametersContext;

public abstract class SUBANDXORORCPVisitor<T extends OneParameterInstruction, C extends InstructionContext> extends OneParameterVisitor<T, C, InstructionSUBANDXORORCPparametersContext> {

	@Override
	protected Parameter getParameter(InstructionSUBANDXORORCPparametersContext paramctx) {
		return getRegistersWithReference(paramctx.registersWithReference())
				.orElseGet(() -> getIndexedReference(paramctx.indexedReference())
						.orElseGet(() -> getExpression(paramctx.hex8bits())
								.orElseGet(() -> null)));
	}

}
