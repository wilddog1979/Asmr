package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBITRESSETparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class BITRESSETVisitor<T extends TwoParameterInstruction, C extends InstructionContext> extends TwoParameterInstructionVisitor<T, C, InstructionBITRESSETparametersContext> {

	@Override
	protected Parameter getSourceParameter(InstructionBITRESSETparametersContext paramctx) {
		return getRegistersWithReference(paramctx.registersWithReference())
				.orElseGet(() -> getIndexedReference(paramctx.indexedReference())
						.orElseGet(() -> null));
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionBITRESSETparametersContext paramctx) {
		return getExpression(paramctx.hex3bits())
				.orElseGet(() -> null);
	}

}
