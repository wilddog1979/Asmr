package org.eastars.asm.test;

import org.eastars.asm.ast.AssemblerLine;
import org.eastars.asm.ast.Directive;
import org.eastars.asm.ast.DirectiveLine;
import org.eastars.asm.ast.test.AssemblerLineTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DirectiveTest extends AssemblerLineTester {
  
  protected Directive invokeDirectiveParser(String content) {
    AssemblerLine line = invokeParser(content);
    
    assertTrue(line instanceof DirectiveLine, "Assembler line must be an instance of DirectiveLine");
    
    return ((DirectiveLine)line).getDirective();
  }
}
