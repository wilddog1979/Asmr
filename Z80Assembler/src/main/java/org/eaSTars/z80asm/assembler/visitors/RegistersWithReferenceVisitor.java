package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.*;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RegistersWithReferenceContext;

public class RegistersWithReferenceVisitor extends Z80AssemblerBaseVisitor<Parameter> {

	@Override
	public Parameter visitRegistersWithReference(RegistersWithReferenceContext ctx) {
		Parameter parameter = null;
		
		if (ctx.B != null) {
			parameter = new RegisterParameter(Register.B);
		} else if (ctx.C != null) {
			parameter = new RegisterParameter(Register.C);
		} else if (ctx.D != null) {
			parameter = new RegisterParameter(Register.D);
		} else if (ctx.E != null) {
			parameter = new RegisterParameter(Register.E);
		} else if (ctx.H != null) {
			parameter = new RegisterParameter(Register.H);
		} else if (ctx.L != null) {
			parameter = new RegisterParameter(Register.L);
		} else if (ctx.refHL != null) {
			parameter = new RegisterIndirectAddressing(RegisterPair.HL);
		} else if (ctx.A != null) {
			parameter = new RegisterParameter(Register.A);
		}
		
		return parameter;
	}
	
}
