package org.eaSTars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.z80asm.assembler.visitors.directive.EQUDirectiveVisitor;
import org.eaSTars.z80asm.assembler.visitors.directive.ORGDirectiveVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.EQUContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ORGContext;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Z80DirectivesVisitor extends Z80AssemblerBaseVisitor<Directive> {

	@FunctionalInterface
	private interface VisitorInvocation {
		public Directive invokeVisitor(ParseTree tree);
	}

	private record VisitorMapEntry(Class<? extends ParserRuleContext> context, VisitorInvocation invocation) {
	}
	
	private final Map<Class<? extends ParserRuleContext>, VisitorInvocation> visitorMap =
			Stream.of(
					new VisitorMapEntry(ORGContext.class, t -> new ORGDirectiveVisitor().visitORG((ORGContext) t)),
					new VisitorMapEntry(EQUContext.class, t -> new EQUDirectiveVisitor().visitEQU((EQUContext) t))
					).collect(Collectors.toMap(e -> e.context, e -> e.invocation));
	
	@Override
	public Directive visit(ParseTree tree) {
		Directive directive = null;
		
		if (tree != null) {
			VisitorInvocation invocation = visitorMap.get(tree.getClass());
			if (invocation != null) {
				directive = invocation.invokeVisitor(tree);
			}
		}
		
		return directive;
	}
}
