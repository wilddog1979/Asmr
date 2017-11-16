package org.eaSTars.asm.test;

import static org.junit.Assert.assertTrue;

import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.DirectiveLine;
import org.eaSTars.asm.ast.test.AssemblerLineTester;

public abstract class DirectiveTest extends AssemblerLineTester {
	
	protected Directive invokeDirectiveParser(String content) {
		AssemblerLine line = invokeParser(content);
		
		assertTrue("Assembler line must be an instance of DirectiveLine", line instanceof DirectiveLine);
		
		return ((DirectiveLine)line).getDirective();
	}
}
