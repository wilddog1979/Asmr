package org.eaSTars.z80asm.ast.test;

import org.eaSTars.asm.AbstractTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class InstructionTester extends AbstractTester {

	protected void assertOpcode(byte[] expected, byte[] actual) {
		if (expected != null) {
			assertNotNull(actual, "Opcode must not be null");
			assertEquals(expected.length, actual.length, "Length of the opcode must be equal");
			for (int i = 0; i < expected.length; ++i) {
				assertEquals(expected[i], actual[i], "Opcode at " + i + " must be equal");
			}
		}
	}
}
