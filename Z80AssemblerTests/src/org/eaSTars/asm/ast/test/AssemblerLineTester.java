package org.eaSTars.asm.ast.test;

import static org.junit.Assert.assertEquals;

import org.eaSTars.asm.AbstractTester;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;

public abstract class AssemblerLineTester extends AbstractTester {

	protected AssemblerLine invokeParser(String content) {
		CompilationUnit compilationUnit = getCompilationUnit(content);
		
		assertEquals("One line expected", 1, compilationUnit.getLineCount());

		return compilationUnit.getLine(0);
	}
	
}
