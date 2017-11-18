package org.eaSTars.asm.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eaSTars.asm.ast.Directive;
import org.eaSTars.z80asm.ast.directives.ORG;
import org.junit.jupiter.api.Test;

public class ORGTest extends DirectiveTest {

	@Test
	public void testORGNormal() {
		Directive result = invokeDirectiveParser("org 7a00h\n");
		
		assertNotNull(result, "ORG directive must be recognized");
		assertTrue(result instanceof ORG, "Result must be an instance of ORG");
		
		ORG orgresult = (ORG) result;
		
		assertEquals(0x7a00, orgresult.getValue(null), "ORG vaue must be correct");
	}
	
}
