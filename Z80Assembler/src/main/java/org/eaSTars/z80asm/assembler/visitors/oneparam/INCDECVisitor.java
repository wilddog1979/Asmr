package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionINCDECparametersContext;

public abstract class INCDECVisitor<T extends OneParameterInstruction, C extends InstructionContext> extends OneParameterVisitor<T, C, InstructionINCDECparametersContext> {

	@Override
	protected Parameter getParameter(InstructionINCDECparametersContext paramCtx) {
		return getRegisterPair(paramCtx.IX, RegisterPair.IX)
				.orElseGet(() -> getRegisterPair(paramCtx.IY, RegisterPair.IY)
						.orElseGet(() -> getRegisterSSParameter(paramCtx.registerSS())
								.orElseGet(() -> getRegistersWithReference(paramCtx.registersWithReference())
										.orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
												.orElseGet(() -> null)))));
	}

}