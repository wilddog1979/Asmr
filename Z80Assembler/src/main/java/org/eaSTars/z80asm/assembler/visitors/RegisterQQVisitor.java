package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RegisterQQContext;

public class RegisterQQVisitor extends Z80AssemblerBaseVisitor<RegisterPairParameter> {

	@Override
	public RegisterPairParameter visitRegisterQQ(RegisterQQContext ctx) {
		RegisterPair register = null;
		
		if (ctx.BC != null) {
			register = RegisterPair.BC;
		} else if (ctx.DE != null) {
			register = RegisterPair.DE;
		} else if (ctx.HL != null) {
			register = RegisterPair.HL;
		} else if (ctx.AF != null) {
			register = RegisterPair.AF;
		}
		
		return new RegisterPairParameter(register);
	}
}
