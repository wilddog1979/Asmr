package org.eastars.asm.ast.test;

import org.antlr.v4.runtime.RecognitionException;
import org.eastars.asm.ast.AssemblerLine;
import org.eastars.asm.ast.Directive;
import org.eastars.asm.ast.DirectiveLine;
import org.eastars.asm.ast.directives.Org;
import org.eastars.z80asm.ast.directives.Equ;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DirectiveLineTest extends AssemblerLineTester {

  private static class DirectiveArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new Object[][] {
        {"org 7a00h\n", Org.class, null, "org 7a00h"},
        {"org 7a00h #comment\n", Org.class, "#comment", "org 7a00h"},
        {"@label1: equ 0340h\n", Equ.class, null, "@label1: equ 0340h"}
      }).map(Arguments::of);
    }
    
  }
  
  @ParameterizedTest
  @ArgumentsSource(DirectiveArgumentProvider.class)
  public void testInstructionLine(String testInstruction, Class<? extends Directive> instruction, String comment, String tostring) {
    AssemblerLine result = invokeParser(testInstruction);
    
    assertTrue(result instanceof DirectiveLine, "result must be an instance of DirectiveLine");
    DirectiveLine directiveLineResult = (DirectiveLine) result;
    assertNotNull(directiveLineResult.getDirective());
    assertEquals(directiveLineResult.getDirective().getClass(), instruction);

    if (comment != null) {
      assertEquals(comment, directiveLineResult.getComment(), "Comment must match");
    } else {
      assertNull(directiveLineResult.getComment(), "Unexpected comment");
    }
    
    assertEquals(tostring, directiveLineResult.toString(), "toString method result doesn't match");
  }
  
  @Test
  public void testOrgRepeated() {
    assertThrows(RecognitionException.class, () -> invokeParser("org 5000h\n\tNOP\n\norg 7b00h\n\tNOP\n"), "RecognitionException expected");
  }
  
}
