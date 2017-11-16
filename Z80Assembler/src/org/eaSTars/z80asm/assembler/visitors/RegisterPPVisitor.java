package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RegisterPPContext;

public class RegisterPPVisitor extends Z80AssemblerBaseVisitor<RegisterPairParameter> {

	@Override
	public RegisterPairParameter visitRegisterPP(RegisterPPContext ctx) {
		RegisterPair register = null;
		
		if (ctx.BC != null) {
			register = RegisterPair.BC;
		} else if (ctx.DE != null) {
			register = RegisterPair.DE;
		} else if (ctx.IX != null) {
			register = RegisterPair.IX;
		} else if (ctx.SP != null) {
			register = RegisterPair.SP;
		}
		
		return new RegisterPairParameter(register);
	}
	
}
