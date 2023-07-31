package org.eaSTars.z80asm.ast.test;

import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.oneparam.*;
import org.eaSTars.z80asm.ast.parameter.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OneParamTest extends InstructionTester {

	private static class InstructionArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"INC BC", INC.class, RegisterPairParameter.class, "BC"},
				{"INC DE", INC.class, RegisterPairParameter.class, "DE"},
				{"INC HL", INC.class, RegisterPairParameter.class, "HL"},
				{"INC SP", INC.class, RegisterPairParameter.class, "SP"},
				{"INC B", INC.class, RegisterParameter.class, "B"},
				{"INC C", INC.class, RegisterParameter.class, "C"},
				{"INC D", INC.class, RegisterParameter.class, "D"},
				{"INC E", INC.class, RegisterParameter.class, "E"},
				{"INC H", INC.class, RegisterParameter.class, "H"},
				{"INC L", INC.class, RegisterParameter.class, "L"},
				{"INC [HL]", INC.class, RegisterIndirectAddressing.class, "[HL]"},
				{"INC A", INC.class, RegisterParameter.class, "A"},
				{"INC IX", INC.class, RegisterPairParameter.class, "IX"},
				{"INC IY", INC.class, RegisterPairParameter.class, "IY"},
				{"INC [IX+0030h]", INC.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"INC [IY+0030h]", INC.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"DEC BC", DEC.class, RegisterPairParameter.class, "BC"},
				{"DEC DE", DEC.class, RegisterPairParameter.class, "DE"},
				{"DEC HL", DEC.class, RegisterPairParameter.class, "HL"},
				{"DEC SP", DEC.class, RegisterPairParameter.class, "SP"},
				{"DEC B", DEC.class, RegisterParameter.class, "B"},
				{"DEC C", DEC.class, RegisterParameter.class, "C"},
				{"DEC D", DEC.class, RegisterParameter.class, "D"},
				{"DEC E", DEC.class, RegisterParameter.class, "E"},
				{"DEC H", DEC.class, RegisterParameter.class, "H"},
				{"DEC L", DEC.class, RegisterParameter.class, "L"},
				{"DEC [HL]", DEC.class, RegisterIndirectAddressing.class, "[HL]"},
				{"DEC A", DEC.class, RegisterParameter.class, "A"},
				{"DEC IX", DEC.class, RegisterPairParameter.class, "IX"},
				{"DEC IY", DEC.class, RegisterPairParameter.class, "IY"},
				{"DEC [IX+0030h]", DEC.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"DEC [IY+0030h]", DEC.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"SUB B", SUB.class, RegisterParameter.class, "B"},
				{"SUB C", SUB.class, RegisterParameter.class, "C"},
				{"SUB D", SUB.class, RegisterParameter.class, "D"},
				{"SUB E", SUB.class, RegisterParameter.class, "E"},
				{"SUB H", SUB.class, RegisterParameter.class, "H"},
				{"SUB L", SUB.class, RegisterParameter.class, "L"},
				{"SUB [HL]", SUB.class, RegisterIndirectAddressing.class, "[HL]"},
				{"SUB A", SUB.class, RegisterParameter.class, "A"},
				{"SUB 005fh", SUB.class, ExpressionParameter.class, "005fh"},
				{"SUB [IX+0030h]", SUB.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"SUB [IY+0030h]", SUB.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"AND B", AND.class, RegisterParameter.class, "B"},
				{"AND C", AND.class, RegisterParameter.class, "C"},
				{"AND D", AND.class, RegisterParameter.class, "D"},
				{"AND E", AND.class, RegisterParameter.class, "E"},
				{"AND H", AND.class, RegisterParameter.class, "H"},
				{"AND L", AND.class, RegisterParameter.class, "L"},
				{"AND [HL]", AND.class, RegisterIndirectAddressing.class, "[HL]"},
				{"AND A", AND.class, RegisterParameter.class, "A"},
				{"AND 005fh", AND.class, ExpressionParameter.class, "005fh"},
				{"AND [IX+0030h]", AND.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"AND [IY+0030h]", AND.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"XOR B", XOR.class, RegisterParameter.class, "B"},
				{"XOR C", XOR.class, RegisterParameter.class, "C"},
				{"XOR D", XOR.class, RegisterParameter.class, "D"},
				{"XOR E", XOR.class, RegisterParameter.class, "E"},
				{"XOR H", XOR.class, RegisterParameter.class, "H"},
				{"XOR L", XOR.class, RegisterParameter.class, "L"},
				{"XOR [HL]", XOR.class, RegisterIndirectAddressing.class, "[HL]"},
				{"XOR A", XOR.class, RegisterParameter.class, "A"},
				{"XOR 005fh", XOR.class, ExpressionParameter.class, "005fh"},
				{"XOR [IX+0030h]", XOR.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"XOR [IY+0030h]", XOR.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"OR B", OR.class, RegisterParameter.class, "B"},
				{"OR C", OR.class, RegisterParameter.class, "C"},
				{"OR D", OR.class, RegisterParameter.class, "D"},
				{"OR E", OR.class, RegisterParameter.class, "E"},
				{"OR H", OR.class, RegisterParameter.class, "H"},
				{"OR L", OR.class, RegisterParameter.class, "L"},
				{"OR [HL]", OR.class, RegisterIndirectAddressing.class, "[HL]"},
				{"OR A", OR.class, RegisterParameter.class, "A"},
				{"OR 005fh", OR.class, ExpressionParameter.class, "005fh"},
				{"OR [IX+0030h]", OR.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"OR [IY+0030h]", OR.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"CP B", CP.class, RegisterParameter.class, "B"},
				{"CP C", CP.class, RegisterParameter.class, "C"},
				{"CP D", CP.class, RegisterParameter.class, "D"},
				{"CP E", CP.class, RegisterParameter.class, "E"},
				{"CP H", CP.class, RegisterParameter.class, "H"},
				{"CP L", CP.class, RegisterParameter.class, "L"},
				{"CP [HL]", CP.class, RegisterIndirectAddressing.class, "[HL]"},
				{"CP A", CP.class, RegisterParameter.class, "A"},
				{"CP 005fh", CP.class, ExpressionParameter.class, "005fh"},
				{"CP [IX+0030h]", CP.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"CP [IY+0030h]", CP.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"POP BC", POP.class, RegisterPairParameter.class, "BC"},
				{"POP DE", POP.class, RegisterPairParameter.class, "DE"},
				{"POP HL", POP.class, RegisterPairParameter.class, "HL"},
				{"POP AF", POP.class, RegisterPairParameter.class, "AF"},
				{"POP IX", POP.class, RegisterPairParameter.class, "IX"},
				{"POP IY", POP.class, RegisterPairParameter.class, "IY"},
				{"PUSH BC", PUSH.class, RegisterPairParameter.class, "BC"},
				{"PUSH DE", PUSH.class, RegisterPairParameter.class, "DE"},
				{"PUSH HL", PUSH.class, RegisterPairParameter.class, "HL"},
				{"PUSH AF", PUSH.class, RegisterPairParameter.class, "AF"},
				{"PUSH IX", PUSH.class, RegisterPairParameter.class, "IX"},
				{"PUSH IY", PUSH.class, RegisterPairParameter.class, "IY"},
				{"RET", RET.class, null, null, new byte[] {(byte) 0xc9}},
				{"RETNZ", RET.class, ConditionParameter.class, "NZ"},
				{"RETZ", RET.class, ConditionParameter.class, "Z"},
				{"RETNC", RET.class, ConditionParameter.class, "NC"},
				{"RETC", RET.class, ConditionParameter.class, "C"},
				{"RETPO", RET.class, ConditionParameter.class, "PO"},
				{"RETPE", RET.class, ConditionParameter.class, "PE"},
				{"RETP", RET.class, ConditionParameter.class, "P"},
				{"RETM", RET.class, ConditionParameter.class, "M"},
				{"RST 00", RST.class, ConstantValueParameter.class, "00"},
				{"RST 08", RST.class, ConstantValueParameter.class, "08"},
				{"RST 10", RST.class, ConstantValueParameter.class, "10"},
				{"RST 18", RST.class, ConstantValueParameter.class, "18"},
				{"RST 20", RST.class, ConstantValueParameter.class, "20"},
				{"RST 28", RST.class, ConstantValueParameter.class, "28"},
				{"RST 30", RST.class, ConstantValueParameter.class, "30"},
				{"RST 38", RST.class, ConstantValueParameter.class, "38"},
				{"DJNZ 0000h", DJNZ.class, ExpressionParameter.class, "0000h"},
				{"RLC B", RLC.class, RegisterParameter.class, "B"},
				{"RLC C", RLC.class, RegisterParameter.class, "C"},
				{"RLC D", RLC.class, RegisterParameter.class, "D"},
				{"RLC E", RLC.class, RegisterParameter.class, "E"},
				{"RLC H", RLC.class, RegisterParameter.class, "H"},
				{"RLC L", RLC.class, RegisterParameter.class, "L"},
				{"RLC [HL]", RLC.class, RegisterIndirectAddressing.class, "[HL]"},
				{"RLC A", RLC.class, RegisterParameter.class, "A"},
				{"RLC [IX+0030h]", RLC.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"RLC [IY+0030h]", RLC.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"RRC B", RRC.class, RegisterParameter.class, "B"},
				{"RRC C", RRC.class, RegisterParameter.class, "C"},
				{"RRC D", RRC.class, RegisterParameter.class, "D"},
				{"RRC E", RRC.class, RegisterParameter.class, "E"},
				{"RRC H", RRC.class, RegisterParameter.class, "H"},
				{"RRC L", RRC.class, RegisterParameter.class, "L"},
				{"RRC [HL]", RRC.class, RegisterIndirectAddressing.class, "[HL]"},
				{"RRC A", RRC.class, RegisterParameter.class, "A"},
				{"RRC [IX+0030h]", RRC.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"RRC [IY+0030h]", RRC.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"RL B", RL.class, RegisterParameter.class, "B"},
				{"RL C", RL.class, RegisterParameter.class, "C"},
				{"RL D", RL.class, RegisterParameter.class, "D"},
				{"RL E", RL.class, RegisterParameter.class, "E"},
				{"RL H", RL.class, RegisterParameter.class, "H"},
				{"RL L", RL.class, RegisterParameter.class, "L"},
				{"RL [HL]", RL.class, RegisterIndirectAddressing.class, "[HL]"},
				{"RL A", RL.class, RegisterParameter.class, "A"},
				{"RL [IX+0030h]", RL.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"RL [IY+0030h]", RL.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"RR B", RR.class, RegisterParameter.class, "B"},
				{"RR C", RR.class, RegisterParameter.class, "C"},
				{"RR D", RR.class, RegisterParameter.class, "D"},
				{"RR E", RR.class, RegisterParameter.class, "E"},
				{"RR H", RR.class, RegisterParameter.class, "H"},
				{"RR L", RR.class, RegisterParameter.class, "L"},
				{"RR [HL]", RR.class, RegisterIndirectAddressing.class, "[HL]"},
				{"RR A", RR.class, RegisterParameter.class, "A"},
				{"RR [IX+0030h]", RR.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"RR [IY+0030h]", RR.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"SLA B", SLA.class, RegisterParameter.class, "B"},
				{"SLA C", SLA.class, RegisterParameter.class, "C"},
				{"SLA D", SLA.class, RegisterParameter.class, "D"},
				{"SLA E", SLA.class, RegisterParameter.class, "E"},
				{"SLA H", SLA.class, RegisterParameter.class, "H"},
				{"SLA L", SLA.class, RegisterParameter.class, "L"},
				{"SLA [HL]", SLA.class, RegisterIndirectAddressing.class, "[HL]"},
				{"SLA A", SLA.class, RegisterParameter.class, "A"},
				{"SLA [IX+0030h]", SLA.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"SLA [IY+0030h]", SLA.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"SRA B", SRA.class, RegisterParameter.class, "B"},
				{"SRA C", SRA.class, RegisterParameter.class, "C"},
				{"SRA D", SRA.class, RegisterParameter.class, "D"},
				{"SRA E", SRA.class, RegisterParameter.class, "E"},
				{"SRA H", SRA.class, RegisterParameter.class, "H"},
				{"SRA L", SRA.class, RegisterParameter.class, "L"},
				{"SRA [HL]", SRA.class, RegisterIndirectAddressing.class, "[HL]"},
				{"SRA A", SRA.class, RegisterParameter.class, "A"},
				{"SRA [IX+0030h]", SRA.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"SRA [IY+0030h]", SRA.class, IndexedAddressingParameter.class, "[IY+0030h]"},
				{"SRL B", SRL.class, RegisterParameter.class, "B"},
				{"SRL C", SRL.class, RegisterParameter.class, "C"},
				{"SRL D", SRL.class, RegisterParameter.class, "D"},
				{"SRL E", SRL.class, RegisterParameter.class, "E"},
				{"SRL H", SRL.class, RegisterParameter.class, "H"},
				{"SRL L", SRL.class, RegisterParameter.class, "L"},
				{"SRL [HL]", SRL.class, RegisterIndirectAddressing.class, "[HL]"},
				{"SRL A", SRL.class, RegisterParameter.class, "A"},
				{"SRL [IX+0030h]", SRL.class, IndexedAddressingParameter.class, "[IX+0030h]"},
				{"SRL [IY+0030h]", SRL.class, IndexedAddressingParameter.class, "[IY+0030h]"}
			}).map(i -> Arguments.of(i));
		}

	}

	@ParameterizedTest
	@ArgumentsSource(InstructionArgumentProvider.class)
	public void testOneParameterInstructions(String testinstruction, Class<? extends OneParameterInstruction> instructionclass,
			Class<? extends Parameter> parameterclass, String parameterstring) {
		Z80Instruction result = getZ80Instruction(testinstruction);

		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result.getClass().isAssignableFrom(instructionclass), () -> String.format("Test instruction must be an instance of %s", instructionclass.getName()));
		
		Parameter resultparameter = ((OneParameterInstruction)result).getParameter();
		if (parameterclass != null) {
			assertTrue(resultparameter.getClass().isAssignableFrom(parameterclass), () -> String.format("Parameter must be an instance of %s", parameterclass.getName()));
			
			assertNotNull(resultparameter, "Instruction parameter must be presented");
			assertEquals(parameterstring, resultparameter.getAssembly(), "Parameter string does not match");
		} else {
			assertNull(resultparameter, "Parameter most not be presented");
		}
		
		assertEquals(testinstruction, result.getAssembly(), "Instruction assembly does not match");
	}

}
