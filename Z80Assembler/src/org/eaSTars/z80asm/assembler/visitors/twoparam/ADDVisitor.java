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
	protected Parameter getSourceParameter(InstructionADDparametersContext paramctx) {
		return getRegisterSSParameter(paramctx.registerSS())
				.orElseGet(() -> getRegistersWithReference(paramctx.registersWithReference())
						.orElseGet(() -> getRegisterPPParameter(paramctx.registerPP())
								.orElseGet(() -> getRegisterRRParameter(paramctx.registerRR())
										.orElseGet(() -> getIndexedReference(paramctx.indexedReference())
												.orElseGet(() -> getExpression(paramctx.hex8bits())
														.orElseGet(() -> null))))));
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionADDparametersContext paramctx) {
		return getRegisterPair(paramctx.HL, RegisterPair.HL)
				.orElseGet(() -> getRegister(paramctx.A, Register.A)
						.orElseGet(() -> getRegisterPair(paramctx.IX, RegisterPair.IX)
								.orElseGet(() -> getRegisterPair(paramctx.IY, RegisterPair.IY)
										.orElseGet(() -> null))));
	}
	
}
