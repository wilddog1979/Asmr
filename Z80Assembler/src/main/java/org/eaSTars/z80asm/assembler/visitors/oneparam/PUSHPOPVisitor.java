package org.eaSTars.z80asm.assembler.visitors.oneparam;

import org.eaSTars.z80asm.assembler.visitors.OneParameterVisitor;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionPUSHPOPparametersContext;

public abstract class PUSHPOPVisitor<T extends OneParameterInstruction, C extends InstructionContext> extends OneParameterVisitor<T, C, InstructionPUSHPOPparametersContext> {

	@Override
	protected Parameter getParameter(InstructionPUSHPOPparametersContext paramCtx) {
		return getRegisterQQParameter(paramCtx.registerQQ())
				.orElseGet(() -> getRegisterPair(paramCtx.IX, RegisterPair.IX)
						.orElseGet(() -> getRegisterPair(paramCtx.IY, RegisterPair.IY)
								.orElseGet(() -> null)));
	}

}
