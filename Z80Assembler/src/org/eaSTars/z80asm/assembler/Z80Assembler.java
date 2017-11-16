package org.eaSTars.z80asm.assembler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.eaSTars.asm.assember.Assembler;
import org.eaSTars.asm.assember.AssemblerException;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.assembler.visitors.Z80CompilationUnitVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerLexer;
import org.eaSTars.z80asm.parser.Z80AssemblerParser;

public class Z80Assembler extends Assembler {
	
	@Override
	public CompilationUnit parseInstructions(String sourcefilename) {
		CompilationUnit result = null;
		
		try (InputStream infile = new FileInputStream(sourcefilename)) {
			CharStream charStream = CharStreams.fromStream(infile);
			Lexer lexer = new Z80AssemblerLexer(charStream);
			
			lexer.removeErrorListeners();
			lexer.addErrorListener(getErrorListener());
			
			TokenStream tokenStream = new CommonTokenStream(lexer);
			Z80AssemblerParser parser = new Z80AssemblerParser(tokenStream);
			parser.removeErrorListeners();
			parser.addErrorListener(getErrorListener());
			
			Z80CompilationUnitVisitor compilationUnitVisitor = new Z80CompilationUnitVisitor();
			result = compilationUnitVisitor.visitZ80compilationUnit(parser.z80compilationUnit());
			
		} catch (IOException e) {
			throw new AssemblerException(e);
		}
		
		return result;
	}
	
}
