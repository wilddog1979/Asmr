package org.eaSTars.asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.antlr.v4.runtime.RecognitionException;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.DirectiveLine;
import org.eaSTars.z80asm.ast.directives.ORG;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class DirectiveLineTest extends AssemblerLineTester {

	private static class DirectiveArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{".org 7a00h\n", ORG.class, null, "7a00"},
				{".org 7a00h #comment\n", ORG.class, "#comment", "7a00"}
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(DirectiveArgumentProvider.class)
	public void testInstructionLine(String testinstruction, Class<? extends Directive> instruction, String comment, String tostring) {
		AssemblerLine result = invokeParser(testinstruction);
		
		assertTrue(result instanceof DirectiveLine, "result must be an instance of DirectiveLine");
		DirectiveLine directiveLineResult = (DirectiveLine) result;
		
		if (comment != null) {
			assertEquals(comment, directiveLineResult.getComment(), "Comment must match");
		} else {
			assertNull(directiveLineResult.getComment(), "Unexpected comment");
		}
		
		assertEquals(tostring, directiveLineResult.toString(), "toString method result doesn't match");
	}
	
	@Test
	public void testOrgRepeated() {
		assertThrows(RecognitionException.class, () -> invokeParser(".org 5000h\n\tNOP\n\n.org 7b00h\n\tNOP\n"), "RecognitionException expected");
	}
	
}
