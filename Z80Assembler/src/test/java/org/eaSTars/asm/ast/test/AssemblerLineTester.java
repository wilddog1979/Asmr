package org.eaSTars.asm.ast.test;


import org.eaSTars.asm.AbstractTester;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AssemblerLineTester extends AbstractTester {

	protected AssemblerLine invokeParser(String content) {
		CompilationUnit compilationUnit = getCompilationUnit(content);

		assertEquals(1, compilationUnit.getLineCount(), "One line expected");

		return compilationUnit.getLine(0);
	}
	
}
