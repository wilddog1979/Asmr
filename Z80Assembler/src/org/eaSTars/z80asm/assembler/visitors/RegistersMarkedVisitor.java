package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RegistersmarkedContext;

public class RegistersMarkedVisitor extends Z80AssemblerBaseVisitor<Parameter> {

	@Override
	public Parameter visitRegistersmarked(RegistersmarkedContext ctx) {
		Parameter parameter = null;
		
		if (ctx.B != null) {
			parameter = new RegisterParameter(Register.BMarked);
		} else if (ctx.C != null) {
			parameter = new RegisterParameter(Register.CMarked);
		} else if (ctx.D != null) {
			parameter = new RegisterParameter(Register.DMarked);
		} else if (ctx.E != null) {
			parameter = new RegisterParameter(Register.EMarked);
		} else if (ctx.H != null) {
			parameter = new RegisterParameter(Register.HMarked);
		} else if (ctx.L != null) {
			parameter = new RegisterParameter(Register.LMarked);
		} else if (ctx.A != null) {
			parameter = new RegisterParameter(Register.AMarked);
		}
		
		return parameter;
	}
	
}
