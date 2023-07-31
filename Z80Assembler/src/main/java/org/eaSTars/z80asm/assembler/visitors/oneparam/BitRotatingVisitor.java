package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionBitRotatingparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class BitRotatingVisitor<T extends OneParameterInstruction, C extends InstructionContext> extends OneParameterVisitor<T, C, InstructionBitRotatingparametersContext> {

	@Override
	protected Parameter getParameter(InstructionBitRotatingparametersContext paramCtx) {
		return getRegistersWithReference(paramCtx.registersWithReference())
				.orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
						.orElseGet(() -> null));
	}

}
