package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RegisterRRContext;

public class RegisterRRVisitor extends Z80AssemblerBaseVisitor<RegisterPairParameter> {

	@Override
	public RegisterPairParameter visitRegisterRR(RegisterRRContext ctx) {
		RegisterPair register = null;
		
		if (ctx.BC != null) {
			register = RegisterPair.BC;
		} else if (ctx.DE != null) {
			register = RegisterPair.DE;
		} else if (ctx.IY != null) {
			register = RegisterPair.IY;
		} else if (ctx.SP != null) {
			register = RegisterPair.SP;
		}
		
		return new RegisterPairParameter(register);
	}
}
