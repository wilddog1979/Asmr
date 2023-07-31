package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.ADD;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ADDContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionADDparametersContext;

public class ADDVisitor extends TwoParameterInstructionVisitor<ADD, ADDContext, InstructionADDparametersContext> {

	@Override
	protected ADD getInstruction() {
		return new ADD();
	}
	
	@Override
	protected InstructionADDparametersContext getInstructionParameters(ADDContext ctx) {
		return ctx.instructionADDparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionADDparametersContext paramCtx) {
		return getRegisterSSParameter(paramCtx.registerSS())
				.orElseGet(() -> getRegistersWithReference(paramCtx.registersWithReference())
						.orElseGet(() -> getRegisterPPParameter(paramCtx.registerPP())
								.orElseGet(() -> getRegisterRRParameter(paramCtx.registerRR())
										.orElseGet(() -> getIndexedReference(paramCtx.indexedReference())
												.orElseGet(() -> getExpression(paramCtx.hex8bits())
														.orElseGet(() -> null))))));
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionADDparametersContext paramCtx) {
		return getRegisterPair(paramCtx.HL, RegisterPair.HL)
				.orElseGet(() -> getRegister(paramCtx.A, Register.A)
						.orElseGet(() -> getRegisterPair(paramCtx.IX, RegisterPair.IX)
								.orElseGet(() -> getRegisterPair(paramCtx.IY, RegisterPair.IY)
										.orElseGet(() -> null))));
	}
	
}
