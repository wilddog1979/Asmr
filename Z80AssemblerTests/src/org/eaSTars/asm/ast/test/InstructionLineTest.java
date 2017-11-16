package org.eaSTars.asm.ast.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.asm.ast.InstructionLine;
import org.eaSTars.z80asm.ast.instructions.noparam.NOP;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class InstructionLineTest extends AssemblerLineTester {

	private static class InstructionLineArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"################\n", null, null, "################", "0000\t################"},
				{"# Test program #\n", null, null, "# Test program #", "0000\t# Test program #"},
				{"NOP\n", null, NOP.class, null, "0000\tNOP "},
				{"NOP #comment\n", null, NOP.class, "#comment", "0000\tNOP #comment"},
				{"	#comment\n", null, null, "#comment", "0000\t#comment"},
				{"#comment\n", null, null, "#comment", "0000\t#comment"},
				{"@testlabel:\n", "@testlabel", null, null, "0000\t@testlabel: "},
				{"	@testlabel:					#comment\n", "@testlabel", null, "#comment", "0000\t@testlabel: #comment"},
				{"	@testlabel2:		NOP			#comment\n", "@testlabel2", NOP.class, "#comment", "0000\t@testlabel2: NOP #comment"}
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(InstructionLineArgumentProvider.class)
	public void testInstructionLine(String testinstruction, String label, Class<? extends Instruction> instruction, String comment, String tostring) {
		AssemblerLine result = invokeParser(testinstruction);
		
		assertTrue("result must be an instance of InstructionLine", result instanceof InstructionLine);
		InstructionLine instructionLineResult = (InstructionLine) result;
		
		assertEquals("Address must match", 0, instructionLineResult.getAddress());
		
		if (label != null) {
			assertEquals("Label must match", label, result.getLabel());
		} else {
			assertNull("Unexpected label", instructionLineResult.getLabel());
		}
		
		if (instruction != null) {
			assertTrue(String.format("Instruction must be subclass of %s",
					instruction.getName()),instructionLineResult.getInstruction().getClass().isAssignableFrom(instruction));
		} else {
			assertNull("Unexpected instruction", instructionLineResult.getInstruction());
		}
		
		if (comment != null) {
			assertEquals("Comment must match", comment, instructionLineResult.getComment());
		} else {
			assertNull("Unexpected comment", instructionLineResult.getComment());
		}
		
		assertEquals("toString method result doesn't match", tostring, instructionLineResult.toString());
	}

}
