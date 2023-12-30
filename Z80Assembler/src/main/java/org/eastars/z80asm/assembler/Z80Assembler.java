package org.eastars.z80asm.assembler;

import org.antlr.v4.runtime.*;
import org.eastars.asm.assember.Assembler;
import org.eastars.asm.assember.AssemblerException;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.ast.CompilationUnit;
import org.eastars.asm.ast.Instruction;
import org.eastars.z80asm.assembler.converter.AbstractZ80InstructionConverter;
import org.eastars.z80asm.assembler.visitors.Z80CompilationUnitVisitor;
import org.eastars.z80asm.parser.Z80AssemblerLexer;
import org.eastars.z80asm.parser.Z80AssemblerParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Z80Assembler extends Assembler {
  
  @Override
  public CompilationUnit parseInstructions(String sourceFileName) {
    CompilationUnit result = null;
    
    try (InputStream infile = new FileInputStream(sourceFileName)) {
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
  
  @Override
  protected byte[] getInstruction(CompilationContext compilationContext, Instruction instruction) {
    return AbstractZ80InstructionConverter.convertInstruction(compilationContext, instruction);
  }
  
}
