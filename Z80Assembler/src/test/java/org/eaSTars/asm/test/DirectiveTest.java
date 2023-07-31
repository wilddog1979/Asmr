package org.eaSTars.asm.test;

import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.DirectiveLine;
import org.eaSTars.asm.ast.test.AssemblerLineTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DirectiveTest extends AssemblerLineTester {
	
	protected Directive invokeDirectiveParser(String content) {
		AssemblerLine line = invokeParser(content);
		
		assertTrue(line instanceof DirectiveLine, "Assembler line must be an instance of DirectiveLine");
		
		return ((DirectiveLine)line).getDirective();
	}
}
