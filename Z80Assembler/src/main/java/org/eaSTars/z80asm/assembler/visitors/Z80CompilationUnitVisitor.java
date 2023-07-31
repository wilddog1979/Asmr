package org.eaSTars.z80asm.assembler.visitors;

import java.util.Optional;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.DirectiveLine;
import org.eaSTars.asm.visitor.AssemblerLineVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ORGContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Z80assemblerlineContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Z80compilationUnitContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.Z80directivesContext;

public class Z80CompilationUnitVisitor extends Z80AssemblerBaseVisitor<CompilationUnit> {

	private AssemblerLineVisitor assemblerLineVisitor = new AssemblerLineVisitor(new Z80InstructionVisitor());
	
	private Z80DirectivesVisitor directivesVisitor = new Z80DirectivesVisitor();
	
	@Override
	public CompilationUnit visitZ80compilationUnit(Z80compilationUnitContext ctx) {
		CompilationUnit compilationUnit = new CompilationUnit();
		
		boolean orgfound = false;
		
		int instructioncount = 0;
		
		for (ParseTree entry : ctx.children) {
			if (entry instanceof Z80assemblerlineContext) {
				Z80assemblerlineContext assemblerLine = (Z80assemblerlineContext) entry;
				AssemblerLine line = assemblerLineVisitor.visitAssemblerLine(assemblerLine.LABEL(), assemblerLine.instruction(), assemblerLine.COMMENT());
				if (line != null) {
					compilationUnit.addLine(line);
					if (assemblerLine.instruction() != null) {
						instructioncount++;
					}
				}
			} else if (entry instanceof Z80directivesContext) {
				Z80directivesContext directivectx = (Z80directivesContext) entry;
				if (directivectx.directive() instanceof ORGContext) {
					if (orgfound || instructioncount > 0) {
						Z80directivesContext context = (Z80directivesContext) entry;
						Token token = ((Z80directivesContext) entry).getStart();
						throw new RecognitionException(String.format("Unexpected .org directive in line %d at %d", token.getLine(), token.getCharPositionInLine()), null, null, context);
					}
					orgfound = true;
				}
				DirectiveLine directiveline = new DirectiveLine();
				Directive directive = directivesVisitor.visit(directivectx.directive());
				directiveline.setDirective(directive);
				Optional.ofNullable(directivectx.COMMENT()).ifPresent(c -> directiveline.setComment(c.getText()));
				compilationUnit.addLine(directiveline);
			}
		}
		
		return compilationUnit;
	}
}
