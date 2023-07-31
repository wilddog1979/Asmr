package org.eaSTars.asm.ast.test;

import org.eaSTars.asm.assember.LabelAlreadyDefinedException;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.asm.ast.InstructionLine;
import org.eaSTars.z80asm.ast.instructions.noparam.NOP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionLineTest extends AssemblerLineTester {

	private static class InstructionLineArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"################\n", null, null, "################", "################"},
				{"# Test program #\n", null, null, "# Test program #", "# Test program #"},
				{"NOP\n", null, NOP.class, null, "NOP "},
				{"NOP #comment\n", null, NOP.class, "#comment", "NOP #comment"},
				{"	#comment\n", null, null, "#comment", "#comment"},
				{"#comment\n", null, null, "#comment", "#comment"},
				{"@testlabel:\n", "@testlabel", null, null, "@testlabel: "},
				{"	@testlabel:					#comment\n", "@testlabel", null, "#comment", "@testlabel: #comment"},
				{"	@testlabel2:		NOP			#comment\n", "@testlabel2", NOP.class, "#comment", "@testlabel2: NOP #comment"}
			}).map(Arguments::of);
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(InstructionLineArgumentProvider.class)
	public void testInstructionLine(String testInstruction, String label, Class<? extends Instruction> instruction, String comment, String tostring) {
		AssemblerLine result = invokeParser(testInstruction);
		
		assertTrue(result instanceof InstructionLine, "result must be an instance of InstructionLine");
		InstructionLine instructionLineResult = (InstructionLine) result;
		
		if (label != null) {
			assertEquals(label, result.getLabel(), "Label must match");
		} else {
			assertNull(instructionLineResult.getLabel(), "Unexpected label");
		}
		
		if (instruction != null) {
			assertTrue(
					instructionLineResult.getInstruction().getClass().isAssignableFrom(instruction),
					String.format("Instruction must be subclass of %s", instruction.getName()));
		} else {
			assertNull(instructionLineResult.getInstruction(), "Unexpected instruction");
		}
		
		if (comment != null) {
			assertEquals(comment, instructionLineResult.getComment(), "Comment must match");
		} else {
			assertNull(instructionLineResult.getComment(), "Unexpected comment");
		}
		
		assertEquals(tostring, instructionLineResult.toString(), "toString method result doesn't match");
	}

	@Test
	public void testRepeatedLabel() {
		assertThrows(LabelAlreadyDefinedException.class, () -> invokeParser("# some comment\n\tNOP\n@mylabel:\tNOP\n\tNOP\n\t@mylabel:\tNOP\n\tNOP\n"), "LabelAlreadyDefinedException expected");
	}
	
}
