package org.eastars.asm.test;

import org.eastars.asm.ast.Directive;
import org.eastars.asm.ast.directives.ORG;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrgTest extends DirectiveTest {

  @Test
  public void testORGNormal() {
    Directive result = invokeDirectiveParser("org 7a00h\n");

    assertNotNull(result, "ORG directive must be recognized");
    assertTrue(result instanceof ORG, "Result must be an instance of ORG");

    ORG orgResult = (ORG) result;

    assertEquals(0x7a00, orgResult.getValue().intValue(), "ORG value must be correct");
  }

}
