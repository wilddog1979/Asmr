package org.eaSTars.asm.ast.test;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.assembler.visitors.Z80CompilationUnitVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerLexer;
import org.eaSTars.z80asm.parser.Z80AssemblerParser;

public abstract class AssemblerLineTester {

	protected AssemblerLine invokeParser(String content) {
		CharStream charStream = CharStreams.fromString(content);
		Lexer lexer = new Z80AssemblerLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		Z80AssemblerParser parser = new Z80AssemblerParser(tokenStream);
		
		CompilationUnit compilationUnit = new Z80CompilationUnitVisitor().visitZ80compilationUnit(parser.z80compilationUnit());
		
		assertEquals("One line expected", 1, compilationUnit.getLineCount());

		return compilationUnit.getLine(0);
	}
	
}
