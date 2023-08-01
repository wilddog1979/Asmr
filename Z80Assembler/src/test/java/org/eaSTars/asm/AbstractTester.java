package org.eaSTars.asm;

import org.antlr.v4.runtime.*;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eaSTars.z80asm.assembler.visitors.Z80CompilationUnitVisitor;
import org.eaSTars.z80asm.assembler.visitors.Z80InstructionVisitor;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.expression.Expression;
import org.eaSTars.z80asm.parser.Z80AssemblerLexer;
import org.eaSTars.z80asm.parser.Z80AssemblerParser;

public abstract class AbstractTester {

	protected Z80AssemblerParser getZ80AssemblerParser(String content) {
		CharStream charStream = CharStreams.fromString(content);
		Lexer lexer = new Z80AssemblerLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		return new Z80AssemblerParser(tokenStream);
	}
	
	protected CompilationUnit getCompilationUnit(String content) {
		Z80AssemblerParser parser = getZ80AssemblerParser(content);
		return new Z80CompilationUnitVisitor().visitZ80compilationUnit(parser.z80compilationUnit());
	}
	
	protected Z80Instruction getZ80Instruction(String content) {
		Z80AssemblerParser parser = getZ80AssemblerParser(content);
		return new Z80InstructionVisitor().visit(parser.instruction());
	}
	
	protected Expression getExpression(String content) {
		Z80AssemblerParser parser = getZ80AssemblerParser(content);
		return new ExpressionVisitor().visitExpression(parser.expression());
	}

}
