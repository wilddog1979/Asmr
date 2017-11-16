package org.eaSTars.z80asm.assembler.visitors;

import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ParameterTContext;

public class ParameterTVisitor extends Z80AssemblerBaseVisitor<Parameter> {

	@Override
	public Parameter visitParameterT(ParameterTContext ctx) {
		Parameter parameter = null;
		
		if (ctx.x00h != null) {
			parameter = new ConstantValueParameter("00");
		} else if (ctx.x08h != null) {
			parameter = new ConstantValueParameter("08");
		} else if (ctx.x10h != null) {
			parameter = new ConstantValueParameter("10");
		} else if (ctx.x18h != null) {
			parameter = new ConstantValueParameter("18");
		} else if (ctx.x20h != null) {
			parameter = new ConstantValueParameter("20");
		} else if (ctx.x28h != null) {
			parameter = new ConstantValueParameter("28");
		} else if (ctx.x30h != null) {
			parameter = new ConstantValueParameter("30");
		} else if (ctx.x38h != null) {
			parameter = new ConstantValueParameter("38");
		}
		
		return parameter;
	}
}
