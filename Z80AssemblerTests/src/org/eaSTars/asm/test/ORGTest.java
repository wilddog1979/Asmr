package org.eaSTars.asm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eaSTars.asm.ast.Directive;
import org.eaSTars.z80asm.ast.directives.ORG;
import org.junit.jupiter.api.Test;

public class ORGTest extends DirectiveTest {

	@Test
	public void testORGNormal() {
		Directive result = invokeDirectiveParser(".org 7a00h\n");
		
		assertNotNull("ORG directive must be recognized", result);
		assertTrue("Result must be an instance of ORG", result instanceof ORG);
		
		ORG orgresult = (ORG) result;
		
		assertEquals("ORG vaue must be correct", 0x7a00, orgresult.getValue().intValue());
	}
	
}
