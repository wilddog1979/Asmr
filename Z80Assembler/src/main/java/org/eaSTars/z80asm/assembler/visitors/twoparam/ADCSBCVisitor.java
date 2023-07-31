package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionADCSBCparametersContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionContext;

public abstract class ADCSBCVisitor<T extends TwoParameterInstruction, C extends InstructionContext> extends TwoParameterInstructionVisitor<T, C, InstructionADCSBCparametersContext> {

	@Override
	protected Parameter getSourceParameter(InstructionADCSBCparametersContext paramCtx) {
		return getRegistersWithReference(paramCtx.registersWithReference())
				.orElseGet(() -> getRegisterSSParameter(paramCtx.registerSS())
						.orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
								.orElseGet(() -> getExpression(paramCtx.hex8bits())
										.orElseGet(() -> null))));
	}

	@Override
	protected Parameter getTargetParameter(InstructionADCSBCparametersContext paramCtx) {
		return getRegister(paramCtx.A, Register.A)
				.orElseGet(() -> getRegisterPair(paramCtx.HL, RegisterPair.HL)
						.orElseGet(() -> null));
	}

}
