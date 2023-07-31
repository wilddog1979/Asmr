package org.eaSTars.asm.test;

import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.directives.ORG;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ORGTest extends DirectiveTest {

	@Test
	public void testORGNormal() {
		Directive result = invokeDirectiveParser("org 7a00h\n");
		
		assertNotNull(result, "ORG directive must be recognized");
		assertTrue(result instanceof ORG, "Result must be an instance of ORG");
		
		ORG orgresult = (ORG) result;
		
		assertEquals(0x7a00, orgresult.getValue().intValue(), "ORG value must be correct");
	}
	
}
