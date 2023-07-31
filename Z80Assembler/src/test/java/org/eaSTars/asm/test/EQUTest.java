package org.eaSTars.asm.test;

import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.assember.CompilationContext.Phase;
import org.eaSTars.asm.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EQUTest extends DirectiveTest {

	@Test
	public void testEQUValue() {
		CompilationUnit compilationUnit = getCompilationUnit("@testlabel: equ cafeh\n");
		CompilationContext compilationContext = new CompilationContext();
		for (int i = 0; i < compilationUnit.getLineCount(); ++i) {
			compilationContext.addInstructionLine(compilationUnit.getLine(i), 0);
		}
		compilationContext.setPhase(Phase.COMPILATION);
		
		int result = compilationContext.getLabelValue("@testlabel");
		
		assertEquals(0xcafe, result, "Result must match");
	}
}
