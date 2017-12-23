package org.eaSTars.z80asm.assembler.visitors.directive;

import org.eaSTars.asm.ast.directives.ORG;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ORGContext;

public class ORGDirectiveVisitor extends Z80AssemblerBaseVisitor<ORG> {

	protected int parseHexValue(String value) {
		return Integer.parseInt(value.substring(0, value.length() - 1), 16);
	}
	
	public ORG visitORG(ORGContext ctx) {
		return new ORG(parseHexValue(ctx.Hex16Bits().getText()));
	};
}
