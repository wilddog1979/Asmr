package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.oneparam.AND;
import org.eaSTars.z80asm.ast.instructions.oneparam.CP;
import org.eaSTars.z80asm.ast.instructions.oneparam.DEC;
import org.eaSTars.z80asm.ast.instructions.oneparam.DJNZ;
import org.eaSTars.z80asm.ast.instructions.oneparam.INC;
import org.eaSTars.z80asm.ast.instructions.oneparam.OR;
import org.eaSTars.z80asm.ast.instructions.oneparam.POP;
import org.eaSTars.z80asm.ast.instructions.oneparam.PUSH;
import org.eaSTars.z80asm.ast.instructions.oneparam.RET;
import org.eaSTars.z80asm.ast.instructions.oneparam.RL;
import org.eaSTars.z80asm.ast.instructions.oneparam.RLC;
import org.eaSTars.z80asm.ast.instructions.oneparam.RR;
import org.eaSTars.z80asm.ast.instructions.oneparam.RRC;
import org.eaSTars.z80asm.ast.instructions.oneparam.RST;
import org.eaSTars.z80asm.ast.instructions.oneparam.SLA;
import org.eaSTars.z80asm.ast.instructions.oneparam.SRA;
import org.eaSTars.z80asm.ast.instructions.oneparam.SRL;
import org.eaSTars.z80asm.ast.instructions.oneparam.SUB;
import org.eaSTars.z80asm.ast.instructions.oneparam.XOR;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class OneParamTest extends InstructionTester {

	private static class InstructionArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"INC BC", INC.class, RegisterPairParameter.class, "BC", new byte[] {0x03}},
				{"INC DE", INC.class, RegisterPairParameter.class, "DE", new byte[] {0x13}},
				{"INC HL", INC.class, RegisterPairParameter.class, "HL", new byte[] {0x23}},
				{"INC SP", INC.class, RegisterPairParameter.class, "SP", new byte[] {0x33}},
				{"INC B", INC.class, RegisterParameter.class, "B", new byte[] {0x04}},
				{"INC C", INC.class, RegisterParameter.class, "C", new byte[] {0x0c}},
				{"INC D", INC.class, RegisterParameter.class, "D", new byte[] {0x14}},
				{"INC E", INC.class, RegisterParameter.class, "E", new byte[] {0x1c}},
				{"INC H", INC.class, RegisterParameter.class, "H", new byte[] {0x24}},
				{"INC L", INC.class, RegisterParameter.class, "L", new byte[] {0x2c}},
				{"INC [HL]", INC.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {0x34}},
				{"INC A", INC.class, RegisterParameter.class, "A", new byte[] {0x3c}},
				{"INC IX", INC.class, RegisterPairParameter.class, "IX", new byte[] {(byte) 0xdd, 0x23}},
				{"INC IY", INC.class, RegisterPairParameter.class, "IY", new byte[] {(byte) 0xfd, 0x23}},
				{"INC [IX+0030h]", INC.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xdd, 0x34, 0x30}},
				{"INC [IY+0030h]", INC.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xdd, 0x34, 0x30}},
				{"DEC BC", DEC.class, RegisterPairParameter.class, "BC", new byte[] {0x0b}},
				{"DEC DE", DEC.class, RegisterPairParameter.class, "DE", new byte[] {0x1b}},
				{"DEC HL", DEC.class, RegisterPairParameter.class, "HL", new byte[] {0x2b}},
				{"DEC SP", DEC.class, RegisterPairParameter.class, "SP", new byte[] {0x3b}},
				{"DEC B", DEC.class, RegisterParameter.class, "B", new byte[] {0x05}},
				{"DEC C", DEC.class, RegisterParameter.class, "C", new byte[] {0x0d}},
				{"DEC D", DEC.class, RegisterParameter.class, "D", new byte[] {0x15}},
				{"DEC E", DEC.class, RegisterParameter.class, "E", new byte[] {0x1d}},
				{"DEC H", DEC.class, RegisterParameter.class, "H", new byte[] {0x25}},
				{"DEC L", DEC.class, RegisterParameter.class, "L", new byte[] {0x2d}},
				{"DEC [HL]", DEC.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {0x35}},
				{"DEC A", DEC.class, RegisterParameter.class, "A", new byte[] {0x3d}},
				{"DEC IX", DEC.class, RegisterPairParameter.class, "IX", new byte[] {(byte) 0xdd, 0x2b}},
				{"DEC IY", DEC.class, RegisterPairParameter.class, "IY", new byte[] {(byte) 0xfd, 0x2b}},
				{"DEC [IX+0030h]", DEC.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xdd, 0x35, 0x30}},
				{"DEC [IY+0030h]", DEC.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xdd, 0x35, 0x30}},
				{"SUB B", SUB.class, RegisterParameter.class, "B", new byte[] {(byte) 0x90}},
				{"SUB C", SUB.class, RegisterParameter.class, "C", new byte[] {(byte) 0x91}},
				{"SUB D", SUB.class, RegisterParameter.class, "D", new byte[] {(byte) 0x92}},
				{"SUB E", SUB.class, RegisterParameter.class, "E", new byte[] {(byte) 0x93}},
				{"SUB H", SUB.class, RegisterParameter.class, "H", new byte[] {(byte) 0x94}},
				{"SUB L", SUB.class, RegisterParameter.class, "L", new byte[] {(byte) 0x95}},
				{"SUB [HL]", SUB.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0x96}},
				{"SUB A", SUB.class, RegisterParameter.class, "A", new byte[] {(byte) 0x97}},
				{"SUB 005fh", SUB.class, ExpressionParameter.class, "005fh", new byte[] {(byte) 0xd6, 0x5f}},
				{"SUB [IX+0030h]", SUB.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0x96, 0x30}},
				{"SUB [IY+0030h]", SUB.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0x96, 0x30}},
				{"AND B", AND.class, RegisterParameter.class, "B", new byte[] {(byte) 0xa0}},
				{"AND C", AND.class, RegisterParameter.class, "C", new byte[] {(byte) 0xa1}},
				{"AND D", AND.class, RegisterParameter.class, "D", new byte[] {(byte) 0xa2}},
				{"AND E", AND.class, RegisterParameter.class, "E", new byte[] {(byte) 0xa3}},
				{"AND H", AND.class, RegisterParameter.class, "H", new byte[] {(byte) 0xa4}},
				{"AND L", AND.class, RegisterParameter.class, "L", new byte[] {(byte) 0xa5}},
				{"AND [HL]", AND.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xa6}},
				{"AND A", AND.class, RegisterParameter.class, "A", new byte[] {(byte) 0xa7}},
				{"AND 005fh", AND.class, ExpressionParameter.class, "005fh", new byte[] {(byte) 0xe6, 0x5f}},
				{"AND [IX+0030h]", AND.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xa6, 0x30}},
				{"AND [IY+0030h]", AND.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xa6, 0x30}},
				{"XOR B", XOR.class, RegisterParameter.class, "B", new byte[] {(byte) 0xa8}},
				{"XOR C", XOR.class, RegisterParameter.class, "C", new byte[] {(byte) 0xa9}},
				{"XOR D", XOR.class, RegisterParameter.class, "D", new byte[] {(byte) 0xaa}},
				{"XOR E", XOR.class, RegisterParameter.class, "E", new byte[] {(byte) 0xab}},
				{"XOR H", XOR.class, RegisterParameter.class, "H", new byte[] {(byte) 0xac}},
				{"XOR L", XOR.class, RegisterParameter.class, "L", new byte[] {(byte) 0xad}},
				{"XOR [HL]", XOR.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xae}},
				{"XOR A", XOR.class, RegisterParameter.class, "A", new byte[] {(byte) 0xaf}},
				{"XOR 005fh", XOR.class, ExpressionParameter.class, "005fh", new byte[] {(byte) 0xee, 0x5f}},
				{"XOR [IX+0030h]", XOR.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xae, 0x30}},
				{"XOR [IY+0030h]", XOR.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xae, 0x30}},
				{"OR B", OR.class, RegisterParameter.class, "B", new byte[] {(byte) 0xb0}},
				{"OR C", OR.class, RegisterParameter.class, "C", new byte[] {(byte) 0xb1}},
				{"OR D", OR.class, RegisterParameter.class, "D", new byte[] {(byte) 0xb2}},
				{"OR E", OR.class, RegisterParameter.class, "E", new byte[] {(byte) 0xb3}},
				{"OR H", OR.class, RegisterParameter.class, "H", new byte[] {(byte) 0xb4}},
				{"OR L", OR.class, RegisterParameter.class, "L", new byte[] {(byte) 0xb5}},
				{"OR [HL]", OR.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xb6}},
				{"OR A", OR.class, RegisterParameter.class, "A", new byte[] {(byte) 0xb7}},
				{"OR 005fh", OR.class, ExpressionParameter.class, "005fh", new byte[] {(byte) 0xf6, 0x5f}},
				{"OR [IX+0030h]", OR.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xb6, 0x30}},
				{"OR [IY+0030h]", OR.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xb6, 0x30}},
				{"CP B", CP.class, RegisterParameter.class, "B", new byte[] {(byte) 0xb8}},
				{"CP C", CP.class, RegisterParameter.class, "C", new byte[] {(byte) 0xb9}},
				{"CP D", CP.class, RegisterParameter.class, "D", new byte[] {(byte) 0xba}},
				{"CP E", CP.class, RegisterParameter.class, "E", new byte[] {(byte) 0xbb}},
				{"CP H", CP.class, RegisterParameter.class, "H", new byte[] {(byte) 0xbc}},
				{"CP L", CP.class, RegisterParameter.class, "L", new byte[] {(byte) 0xbd}},
				{"CP [HL]", CP.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xbe}},
				{"CP A", CP.class, RegisterParameter.class, "A", new byte[] {(byte) 0xbf}},
				{"CP 005fh", CP.class, ExpressionParameter.class, "005fh", new byte[] {(byte) 0xfe, 0x5f}},
				{"CP [IX+0030h]", CP.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xbe, 0x30}},
				{"CP [IY+0030h]", CP.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xbe, 0x30}},
				{"POP BC", POP.class, RegisterPairParameter.class, "BC", new byte[] {(byte) 0xc1}},
				{"POP DE", POP.class, RegisterPairParameter.class, "DE", new byte[] {(byte) 0xd1}},
				{"POP HL", POP.class, RegisterPairParameter.class, "HL", new byte[] {(byte) 0xe1}},
				{"POP AF", POP.class, RegisterPairParameter.class, "AF", new byte[] {(byte) 0xf1}},
				{"POP IX", POP.class, RegisterPairParameter.class, "IX", new byte[] {(byte) 0xdd, (byte) 0xe1}},
				{"POP IY", POP.class, RegisterPairParameter.class, "IY", new byte[] {(byte) 0xfd, (byte) 0xe1}},
				{"PUSH BC", PUSH.class, RegisterPairParameter.class, "BC", new byte[] {(byte) 0xc5}},
				{"PUSH DE", PUSH.class, RegisterPairParameter.class, "DE", new byte[] {(byte) 0xd5}},
				{"PUSH HL", PUSH.class, RegisterPairParameter.class, "HL", new byte[] {(byte) 0xe5}},
				{"PUSH AF", PUSH.class, RegisterPairParameter.class, "AF", new byte[] {(byte) 0xf5}},
				{"PUSH IX", PUSH.class, RegisterPairParameter.class, "IX", new byte[] {(byte) 0xdd, (byte) 0xe5}},
				{"PUSH IY", PUSH.class, RegisterPairParameter.class, "IY", new byte[] {(byte) 0xfd, (byte) 0xe5}},
				{"RET", RET.class, null, null, new byte[] {(byte) 0xc9}},
				{"RETNZ", RET.class, ConditionParameter.class, "NZ", new byte[] {(byte) 0xc0}},
				{"RETZ", RET.class, ConditionParameter.class, "Z", new byte[] {(byte) 0xc8}},
				{"RETNC", RET.class, ConditionParameter.class, "NC", new byte[] {(byte) 0xd0}},
				{"RETC", RET.class, ConditionParameter.class, "C", new byte[] {(byte) 0xd8}},
				{"RETPO", RET.class, ConditionParameter.class, "PO", new byte[] {(byte) 0xe0}},
				{"RETPE", RET.class, ConditionParameter.class, "PE", new byte[] {(byte) 0xe8}},
				{"RETP", RET.class, ConditionParameter.class, "P", new byte[] {(byte) 0xf0}},
				{"RETM", RET.class, ConditionParameter.class, "M", new byte[] {(byte) 0xf8}},
				{"RST 00", RST.class, ConstantValueParameter.class, "00", new byte[] {(byte) 0xc7}},
				{"RST 08", RST.class, ConstantValueParameter.class, "08", new byte[] {(byte) 0xcf}},
				{"RST 10", RST.class, ConstantValueParameter.class, "10", new byte[] {(byte) 0xd7}},
				{"RST 18", RST.class, ConstantValueParameter.class, "18", new byte[] {(byte) 0xdf}},
				{"RST 20", RST.class, ConstantValueParameter.class, "20", new byte[] {(byte) 0xe7}},
				{"RST 28", RST.class, ConstantValueParameter.class, "28", new byte[] {(byte) 0xef}},
				{"RST 30", RST.class, ConstantValueParameter.class, "30", new byte[] {(byte) 0xf7}},
				{"RST 38", RST.class, ConstantValueParameter.class, "38", new byte[] {(byte) 0xff}},
				{"DJNZ 0000h", DJNZ.class, ExpressionParameter.class, "0000h", new byte[] {0x10, (byte) 0xfe}},
				{"RLC B", RLC.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x00}},
				{"RLC C", RLC.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x01}},
				{"RLC D", RLC.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x02}},
				{"RLC E", RLC.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x03}},
				{"RLC H", RLC.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x04}},
				{"RLC L", RLC.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x05}},
				{"RLC [HL]", RLC.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x06}},
				{"RLC A", RLC.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x07}},
				{"RLC [IX+0030h]", RLC.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x06}},
				{"RLC [IY+0030h]", RLC.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x06}},
				{"RRC B", RRC.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x08}},
				{"RRC C", RRC.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x09}},
				{"RRC D", RRC.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x0a}},
				{"RRC E", RRC.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x0b}},
				{"RRC H", RRC.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x0c}},
				{"RRC L", RRC.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x0d}},
				{"RRC [HL]", RRC.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x0e}},
				{"RRC A", RRC.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x0f}},
				{"RRC [IX+0030h]", RRC.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x0e}},
				{"RRC [IY+0030h]", RRC.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x0e}},
				{"RL B", RL.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x10}},
				{"RL C", RL.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x11}},
				{"RL D", RL.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x12}},
				{"RL E", RL.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x13}},
				{"RL H", RL.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x14}},
				{"RL L", RL.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x15}},
				{"RL [HL]", RL.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x16}},
				{"RL A", RL.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x17}},
				{"RL [IX+0030h]", RL.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x16}},
				{"RL [IY+0030h]", RL.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x16}},
				{"RR B", RR.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x18}},
				{"RR C", RR.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x19}},
				{"RR D", RR.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x1a}},
				{"RR E", RR.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x1b}},
				{"RR H", RR.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x1c}},
				{"RR L", RR.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x1d}},
				{"RR [HL]", RR.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x1e}},
				{"RR A", RR.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x1f}},
				{"RR [IX+0030h]", RR.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x1e}},
				{"RR [IY+0030h]", RR.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x1e}},
				{"SLA B", SLA.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x20}},
				{"SLA C", SLA.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x21}},
				{"SLA D", SLA.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x22}},
				{"SLA E", SLA.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x23}},
				{"SLA H", SLA.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x24}},
				{"SLA L", SLA.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x25}},
				{"SLA [HL]", SLA.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x26}},
				{"SLA A", SLA.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x27}},
				{"SLA [IX+0030h]", SLA.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x26}},
				{"SLA [IY+0030h]", SLA.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x26}},
				{"SRA B", SRA.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x28}},
				{"SRA C", SRA.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x29}},
				{"SRA D", SRA.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x2a}},
				{"SRA E", SRA.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x2b}},
				{"SRA H", SRA.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x2c}},
				{"SRA L", SRA.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x2d}},
				{"SRA [HL]", SRA.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x2e}},
				{"SRA A", SRA.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x2f}},
				{"SRA [IX+0030h]", SRA.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x2e}},
				{"SRA [IY+0030h]", SRA.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x2e}},
				{"SRL B", SRL.class, RegisterParameter.class, "B", new byte[] {(byte) 0xca, 0x38}},
				{"SRL C", SRL.class, RegisterParameter.class, "C", new byte[] {(byte) 0xca, 0x39}},
				{"SRL D", SRL.class, RegisterParameter.class, "D", new byte[] {(byte) 0xca, 0x3a}},
				{"SRL E", SRL.class, RegisterParameter.class, "E", new byte[] {(byte) 0xca, 0x3b}},
				{"SRL H", SRL.class, RegisterParameter.class, "H", new byte[] {(byte) 0xca, 0x3c}},
				{"SRL L", SRL.class, RegisterParameter.class, "L", new byte[] {(byte) 0xca, 0x3d}},
				{"SRL [HL]", SRL.class, RegisterIndirectAddressing.class, "[HL]", new byte[] {(byte) 0xca, 0x3e}},
				{"SRL A", SRL.class, RegisterParameter.class, "A", new byte[] {(byte) 0xca, 0x3f}},
				{"SRL [IX+0030h]", SRL.class, IndexedAddressingParameter.class, "[IX+0030h]", new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x3e}},
				{"SRL [IY+0030h]", SRL.class, IndexedAddressingParameter.class, "[IY+0030h]", new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x3e}}
			}).map(i -> Arguments.of(i));
		}

	}

	@ParameterizedTest
	@ArgumentsSource(InstructionArgumentProvider.class)
	public void testOneParameterInstructions(String testinstruction, Class<? extends OneParameterInstruction> instructionclass,
			Class<? extends Parameter> parameterclass, String parameterstring, byte[] opcode) {
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
		
		CompilationUnit compilationUnit = new CompilationUnit();
		
		assertOpcode(opcode, result.getOpcode(compilationUnit));
	}

}
