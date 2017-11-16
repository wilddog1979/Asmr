package org.eaSTars.z80asm.assembler.visitors;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.z80asm.assembler.visitors.directive.ORGDirectiveVisitor;
import org.eaSTars.z80asm.ast.Z80Directive;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ORGContext;

public class Z80DirectivesVisitor extends Z80AssemblerBaseVisitor<Directive> {

	@FunctionalInterface
	private interface VisitorInvokation{
		public Z80Directive invokeVisitor(ParseTree tree);
	}
	
	private class VisitorMapEntry {
		private Class<? extends ParserRuleContext> context;
		private VisitorInvokation invokation;
		
		private VisitorMapEntry(
				Class<? extends ParserRuleContext> context,
				VisitorInvokation invokation) {
			this.context = context;
			this.invokation = invokation;
		}
	}
	
	private Map<Class<? extends ParserRuleContext>, VisitorInvokation> visitorMap =
			Arrays.asList(
					new VisitorMapEntry(ORGContext.class, t -> new ORGDirectiveVisitor().visitORG((ORGContext) t))
					)
			.stream().collect(Collectors.toMap(e -> e.context, e -> e.invokation));
	
	@Override
	public Directive visit(ParseTree tree) {
		Directive directive = null;
		
		if (tree != null) {
			VisitorInvokation invokation = visitorMap.get(tree.getClass());
			if (invokation != null) {
				directive = invokation.invokeVisitor(tree);
			}
		}
		
		return directive;
	}
}
