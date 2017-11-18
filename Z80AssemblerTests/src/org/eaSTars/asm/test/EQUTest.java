package org.eaSTars.asm.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eaSTars.asm.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

public class EQUTest extends DirectiveTest {

	@Test
	public void testEQUValue() {
		CompilationUnit compilationUnit = getCompilationUnit("@testlabel: equ cafeh\n");
		
		int result = compilationUnit.getLabelValue("@testlabel");
		
		assertEquals(0xcafe, result, "Result must match");
	}
}
