package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.eaSTars.z80asm.assembler.visitors.Z80InstructionVisitor;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.parser.Z80AssemblerLexer;
import org.eaSTars.z80asm.parser.Z80AssemblerParser;

public abstract class InstructionTester {

	protected Z80Instruction invokeParser(String content) {
		CharStream charStream = CharStreams.fromString(content);
		Lexer lexer = new Z80AssemblerLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		Z80AssemblerParser parser = new Z80AssemblerParser(tokenStream);
		
		return new Z80InstructionVisitor().visit(parser.instruction());
	}
	
	protected void assertOpcode(byte[] expected, byte[] actual) {
		if (expected != null) {
			assertNotNull(actual, "Opcode must not be null");
			assertEquals(expected.length, actual.length, "Length of the opcode must be equal");
			for (int i = 0; i < expected.length; ++i) {
				assertEquals(expected[i], actual[i], "Opcode at " + i + " must be equal");
			}
		}
	}
}
