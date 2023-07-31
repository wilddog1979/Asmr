package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.LD;
import org.eaSTars.z80asm.ast.parameter.*;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.*;

public class LDVisitor extends TwoParameterInstructionVisitor<LD, LDContext, InstructionLDparametersContext> {

	@Override
	protected LD getInstruction() {
		return new LD();
	}
	
	@Override
	protected InstructionLDparametersContext getInstructionParameters(LDContext ctx) {
		return ctx.instructionLDparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionLDparametersContext paramctx) {
		Parameter parameter = null;

		if (paramctx instanceof LDfromAToAddressContext ||
				paramctx instanceof LDiaContext ||
				paramctx instanceof LDraContext ||
				paramctx instanceof LDrefnum16aContext) {
			parameter = new RegisterParameter(Register.A);
		} else if (paramctx instanceof LDaiContext) {
			parameter = new RegisterParameter(Register.I);
		} else if (paramctx instanceof LDarContext) {
			parameter = new RegisterParameter(Register.R);
		} else if (paramctx instanceof LDfromAddressToAContext) {
			parameter = new RegisterIndirectAddressing(((LDfromAddressToAContext) paramctx).refbc != null ? RegisterPair.BC : RegisterPair.DE);
		} else if (paramctx instanceof LDrefhltoregContext) {
			parameter = new RegisterIndirectAddressing(RegisterPair.HL);
		} else if (paramctx instanceof LDsphlContext ||
				paramctx instanceof LDrefnum16hlContext) {
			parameter = new RegisterPairParameter(RegisterPair.HL);
		} else if (paramctx instanceof LDrhnum8Context) {
			parameter = getExpression(((LDrhnum8Context) paramctx).hex8bits()).orElseGet(() -> null);
		} else if (paramctx instanceof LDssnum16Context) {
			parameter = getExpression(((LDssnum16Context) paramctx).hex16bits()).orElseGet(() -> null);
		} else if (paramctx instanceof LDidxnum8Context) {
			parameter = getExpression(((LDidxnum8Context) paramctx).hex8bits()).orElseGet(() -> null);
		} else if (paramctx instanceof LDixnum16Context) {
			parameter = getExpression(((LDixnum16Context) paramctx).hex16bits()).orElseGet(() -> null);
		} else if (paramctx instanceof LDiynum16Context) {
			parameter = getExpression(((LDiynum16Context) paramctx).hex16bits()).orElseGet(() -> null);
		} else if (paramctx instanceof LDspixContext ||
				paramctx instanceof LDrefnum16ixContext) {
			parameter = new RegisterPairParameter(RegisterPair.IX);
		} else if (paramctx instanceof LDspiyContext ||
				paramctx instanceof LDrefnum16iyContext) {
			parameter = new RegisterPairParameter(RegisterPair.IY);
		} else if (paramctx instanceof LDhlrefnum16Context) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDhlrefnum16Context) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDarefnum16Context) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDarefnum16Context) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDssrefnum16Context) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDssrefnum16Context) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDixrefnum16Context) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDixrefnum16Context) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDiyrefnum16Context) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDiyrefnum16Context) paramctx).hex16bits()).orElseGet(() -> null));
		} else {
			parameter = getRegisters(paramctx, LDregtorefhlContext.class)
					.orElseGet(() -> getRegistersMarked(paramctx, LDregregmarkedContext.class)
							.orElseGet(() -> getRegisters(paramctx, LDidxregsContext.class)
									.orElseGet(() -> getIndexedReference(paramctx, LDregsidxContext.class)
											.orElseGet(() -> getRegisterSSParameter(paramctx, LDrefnum16ssContext.class)
													.orElseGet(() -> null)))));
		}

		return parameter;
	}

	@Override
	protected Parameter getTargetParameter(InstructionLDparametersContext paramctx) {
		Parameter parameter = null;

		if (paramctx instanceof LDfromAToAddressContext) {
			parameter = new RegisterIndirectAddressing(((LDfromAToAddressContext) paramctx).refbc != null ? RegisterPair.BC : RegisterPair.DE);
		} else if (paramctx instanceof LDfromAddressToAContext ||
				paramctx instanceof LDaiContext ||
				paramctx instanceof LDarContext ||
				paramctx instanceof LDarefnum16Context) {
			parameter = new RegisterParameter(Register.A);
		} else if (paramctx instanceof LDiaContext) {
			parameter = new RegisterParameter(Register.I);
		} else if (paramctx instanceof LDraContext) {
			parameter = new RegisterParameter(Register.R);
		} else if (paramctx instanceof LDhlrefnum16Context) {
			parameter = new RegisterPairParameter(RegisterPair.HL);
		} else if (paramctx instanceof LDregtorefhlContext) {
			parameter = new RegisterIndirectAddressing(RegisterPair.HL);
		} else if (paramctx instanceof LDsphlContext ||
				paramctx instanceof LDspixContext ||
				paramctx instanceof LDspiyContext) {
			parameter = new RegisterPairParameter(RegisterPair.SP);
		} else if (paramctx instanceof LDixnum16Context ||
				paramctx instanceof LDixrefnum16Context) {
			parameter = new RegisterPairParameter(RegisterPair.IX);
		} else if (paramctx instanceof LDiynum16Context ||
				paramctx instanceof LDiyrefnum16Context) {
			parameter = new RegisterPairParameter(RegisterPair.IY);
		} else if (paramctx instanceof LDrefnum16hlContext) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDrefnum16hlContext) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDrefnum16aContext) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDrefnum16aContext) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDrefnum16ssContext) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDrefnum16ssContext) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDrefnum16ixContext) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDrefnum16ixContext) paramctx).hex16bits()).orElseGet(() -> null));
		} else if (paramctx instanceof LDrefnum16iyContext) {
			parameter = new ImmediateAddressingParameter(getExpression(((LDrefnum16iyContext) paramctx).hex16bits()).orElseGet(() -> null));
		} else {
			parameter = getRegisters(paramctx, LDrefhltoregContext.class)
					.orElseGet(() -> getRegisters(paramctx, LDregregmarkedContext.class)
							.orElseGet(() -> getRegistersWithReference(paramctx, LDrhnum8Context.class)
									.orElseGet(() -> getRegisterSSParameter(paramctx, LDssnum16Context.class)
											.orElseGet(() -> getIndexedReference(paramctx, LDidxregsContext.class)
													.orElseGet(() -> getRegisters(paramctx, LDregsidxContext.class)
															.orElseGet(() -> getIndexedReference(paramctx, LDidxnum8Context.class)
																	.orElseGet(() -> getRegisterSSParameter(paramctx, LDssrefnum16Context.class)
																			.orElseGet(() -> null))))))));
		}

		return parameter;
	}
	
}
