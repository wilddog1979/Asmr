package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.twoparam.ADC;
import org.eaSTars.z80asm.ast.instructions.twoparam.ADD;
import org.eaSTars.z80asm.ast.instructions.twoparam.BIT;
import org.eaSTars.z80asm.ast.instructions.twoparam.CALL;
import org.eaSTars.z80asm.ast.instructions.twoparam.EX;
import org.eaSTars.z80asm.ast.instructions.twoparam.IN;
import org.eaSTars.z80asm.ast.instructions.twoparam.JP;
import org.eaSTars.z80asm.ast.instructions.twoparam.JR;
import org.eaSTars.z80asm.ast.instructions.twoparam.LD;
import org.eaSTars.z80asm.ast.instructions.twoparam.OUT;
import org.eaSTars.z80asm.ast.instructions.twoparam.RES;
import org.eaSTars.z80asm.ast.instructions.twoparam.SBC;
import org.eaSTars.z80asm.ast.instructions.twoparam.SET;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.ImmediateAddressingParameter;
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

public class TwoParamTest extends InstructionTester {

	private static class InstructionArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"EX AF, AF'", EX.class, RegisterPairParameter.class, "AF", RegisterPairParameter.class, "AF'", new byte[] {0x08}},
				{"EX [SP], HL", EX.class, RegisterIndirectAddressing.class, "[SP]", RegisterPairParameter.class, "HL", new byte[] {(byte) 0xe3}},
				{"EX DE, HL", EX.class, RegisterPairParameter.class, "DE", RegisterPairParameter.class, "HL", new byte[] {(byte) 0xeb}},
				{"EX [SP], IX", EX.class, RegisterIndirectAddressing.class, "[SP]", RegisterPairParameter.class, "IX", new byte[] {(byte) 0xdd, (byte) 0xe3}},
				{"EX [SP], IY", EX.class, RegisterIndirectAddressing.class, "[SP]", RegisterPairParameter.class, "IY", new byte[] {(byte) 0xfd, (byte) 0xe3}},
				{"ADD HL, BC", ADD.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "BC", new byte[]{0x09}},
				{"ADD HL, DE", ADD.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "DE", new byte[]{0x19}},
				{"ADD HL, HL", ADD.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "HL", new byte[]{0x29}},
				{"ADD HL, SP", ADD.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "SP", new byte[]{0x39}},
				{"ADD A, B", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "B", new byte[]{(byte) 0x80}},
				{"ADD A, C", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "C", new byte[]{(byte) 0x81}},
				{"ADD A, D", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "D", new byte[]{(byte) 0x82}},
				{"ADD A, E", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "E", new byte[]{(byte) 0x83}},
				{"ADD A, H", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "H", new byte[]{(byte) 0x84}},
				{"ADD A, L", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "L", new byte[]{(byte) 0x85}},
				{"ADD A, [HL]", ADD.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0x86}},
				{"ADD A, A", ADD.class, RegisterParameter.class, "A", RegisterParameter.class, "A", new byte[]{(byte) 0x87}},
				{"ADD A, 007fh", ADD.class, RegisterParameter.class, "A", ExpressionParameter.class, "007fh", new byte[]{(byte) 0xc6, 0x7f}},
				{"ADD IX, BC", ADD.class, RegisterPairParameter.class, "IX", RegisterPairParameter.class, "BC", new byte[]{(byte) 0xdd, 0x09}},
				{"ADD IX, DE", ADD.class, RegisterPairParameter.class, "IX", RegisterPairParameter.class, "DE", new byte[]{(byte) 0xdd, 0x19}},
				{"ADD IX, IX", ADD.class, RegisterPairParameter.class, "IX", RegisterPairParameter.class, "IX", new byte[]{(byte) 0xdd, 0x29}},
				{"ADD IX, SP", ADD.class, RegisterPairParameter.class, "IX", RegisterPairParameter.class, "SP", new byte[]{(byte) 0xdd, 0x39}},
				{"ADD IY, BC", ADD.class, RegisterPairParameter.class, "IY", RegisterPairParameter.class, "BC", new byte[]{(byte) 0xfd, 0x09}},
				{"ADD IY, DE", ADD.class, RegisterPairParameter.class, "IY", RegisterPairParameter.class, "DE", new byte[]{(byte) 0xfd, 0x19}},
				{"ADD IY, IY", ADD.class, RegisterPairParameter.class, "IY", RegisterPairParameter.class, "IY", new byte[]{(byte) 0xfd, 0x29}},
				{"ADD IY, SP", ADD.class, RegisterPairParameter.class, "IY", RegisterPairParameter.class, "SP", new byte[]{(byte) 0xfd, 0x39}},
				{"ADD A, [IX+000ch]", ADD.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IX+000ch]", new byte[]{(byte) 0xdd, (byte) 0x86, 0x0c}},
				{"ADD A, [IY+00d0h]", ADD.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IY+00d0h]", new byte[]{(byte) 0xfd, (byte) 0x86, (byte) 0xd0}},
				{"ADC A, B", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "B", new byte[]{(byte) 0x88}},
				{"ADC A, C", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "C", new byte[]{(byte) 0x89}},
				{"ADC A, D", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "D", new byte[]{(byte) 0x8a}},
				{"ADC A, E", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "E", new byte[]{(byte) 0x8b}},
				{"ADC A, H", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "H", new byte[]{(byte) 0x8c}},
				{"ADC A, L", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "L", new byte[]{(byte) 0x8d}},
				{"ADC A, [HL]", ADC.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0x8e}},
				{"ADC A, A", ADC.class, RegisterParameter.class, "A", RegisterParameter.class, "A", new byte[]{(byte) 0x8f}},
				{"ADC A, 007fh", ADC.class, RegisterParameter.class, "A", ExpressionParameter.class, "007fh", new byte[]{(byte) 0xce, 0x7f}},
				{"ADC HL, BC", ADC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "BC", new byte[]{(byte) 0xed, 0x4a}},
				{"ADC HL, DE", ADC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "DE", new byte[]{(byte) 0xed, 0x5a}},
				{"ADC HL, HL", ADC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "HL", new byte[]{(byte) 0xed, 0x6a}},
				{"ADC HL, SP", ADC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "SP", new byte[]{(byte) 0xed, 0x7a}},
				{"ADC A, [IX+000ch]", ADC.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IX+000ch]", new byte[]{(byte) 0xdd, (byte) 0x8e, 0xc}},
				{"ADC A, [IY+00d0h]", ADC.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IY+00d0h]", new byte[]{(byte) 0xfd, (byte) 0x8e, (byte) 0xd0}},
				{"SBC A, B", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "B", new byte[]{(byte) 0x98}},
				{"SBC A, C", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "C", new byte[]{(byte) 0x99}},
				{"SBC A, D", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "D", new byte[]{(byte) 0x9a}},
				{"SBC A, E", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "E", new byte[]{(byte) 0x9b}},
				{"SBC A, H", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "H", new byte[]{(byte) 0x9c}},
				{"SBC A, L", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "L", new byte[]{(byte) 0x9d}},
				{"SBC A, [HL]", SBC.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0x9e}},
				{"SBC A, A", SBC.class, RegisterParameter.class, "A", RegisterParameter.class, "A", new byte[]{(byte) 0x9f}},
				{"SBC A, 007fh", SBC.class, RegisterParameter.class, "A", ExpressionParameter.class, "007fh", new byte[]{(byte) 0xde, 0x7f}},
				{"SBC HL, BC", SBC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "BC", new byte[]{(byte) 0xed, 0x42}},
				{"SBC HL, DE", SBC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "DE", new byte[]{(byte) 0xed, 0x52}},
				{"SBC HL, HL", SBC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "HL", new byte[]{(byte) 0xed, 0x62}},
				{"SBC HL, SP", SBC.class, RegisterPairParameter.class, "HL", RegisterPairParameter.class, "SP", new byte[]{(byte) 0xed, 0x72}},
				{"SBC A, [IX+000ch]", SBC.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IX+000ch]", new byte[]{(byte) 0xdd, (byte) 0x9e, 0xc}},
				{"SBC A, [IY+00d0h]", SBC.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IY+00d0h]", new byte[]{(byte) 0xfd, (byte) 0x9e, (byte) 0xd0}},
				{"JP [HL]", JP.class, null, null, RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0xe9}},
				{"JP [IX]", JP.class, null, null, RegisterIndirectAddressing.class, "[IX]", new byte[]{(byte) 0xdd, (byte) 0xe9}},
				{"JP [IY]", JP.class, null, null, RegisterIndirectAddressing.class, "[IY]", new byte[]{(byte) 0xfd, (byte) 0xe9}},
				{"JP 3500h", JP.class, null, null, ExpressionParameter.class, "3500h", new byte[]{(byte) 0xc3, 0x00, 0x35}},
				{"JPNZ 3500h", JP.class, ConditionParameter.class, "NZ", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xc2, 0x00, 0x35}},
				{"JPZ 3500h", JP.class, ConditionParameter.class, "Z", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xca, 0x00, 0x35}},
				{"JPNC 3500h", JP.class, ConditionParameter.class, "NC", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xd2, 0x00, 0x35}},
				{"JPC 3500h", JP.class, ConditionParameter.class, "C", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xda, 0x00, 0x35}},
				{"JPPO 3500h", JP.class, ConditionParameter.class, "PO", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xe2, 0x00, 0x35}},
				{"JPPE 3500h", JP.class, ConditionParameter.class, "PE", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xea, 0x00, 0x35}},
				{"JPP 3500h", JP.class, ConditionParameter.class, "P", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xf2, 0x00, 0x35}},
				{"JPM 3500h", JP.class, ConditionParameter.class, "M", ExpressionParameter.class, "3500h", new byte[]{(byte) 0xfa, 0x00, 0x35}},
				{"JR 0035h", JR.class, null, null, ExpressionParameter.class, "0035h", new byte[]{0x18, 0x33}},
				{"JRNZ 0035h", JR.class, ConditionParameter.class, "NZ", ExpressionParameter.class, "0035h", new byte[]{0x20, 0x33}},
				{"JRZ 0035h", JR.class, ConditionParameter.class, "Z", ExpressionParameter.class, "0035h", new byte[]{0x28, 0x33}},
				{"JRNC 0035h", JR.class, ConditionParameter.class, "NC", ExpressionParameter.class, "0035h", new byte[]{0x30, 0x33}},
				{"JRC 0035h", JR.class, ConditionParameter.class, "C", ExpressionParameter.class, "0035h", new byte[]{0x38, 0x33}},
				{"BIT 0002h, B", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "B", new byte[]{(byte) 0xcb, 0x50}},
				{"BIT 0002h, C", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "C", new byte[]{(byte) 0xcb, 0x51}},
				{"BIT 0002h, D", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "D", new byte[]{(byte) 0xcb, 0x52}},
				{"BIT 0002h, E", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "E", new byte[]{(byte) 0xcb, 0x53}},
				{"BIT 0002h, H", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "H", new byte[]{(byte) 0xcb, 0x54}},
				{"BIT 0002h, L", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "L", new byte[]{(byte) 0xcb, 0x55}},
				{"BIT 0002h, [HL]", BIT.class, ExpressionParameter.class, "0002h", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0xcb, 0x56}},
				{"BIT 0002h, A", BIT.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "A", new byte[]{(byte) 0xcb, 0x57}},
				{"BIT 0004h, [IX+009ah]", BIT.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IX+009ah]", new byte[]{(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, 0x66}},
				{"BIT 0004h, [IY+009ah]", BIT.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IY+009ah]", new byte[]{(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, 0x66}},
				{"RES 0002h, B", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "B", new byte[]{(byte) 0xcb, (byte) 0x90}},
				{"RES 0002h, C", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "C", new byte[]{(byte) 0xcb, (byte) 0x91}},
				{"RES 0002h, D", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "D", new byte[]{(byte) 0xcb, (byte) 0x92}},
				{"RES 0002h, E", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "E", new byte[]{(byte) 0xcb, (byte) 0x93}},
				{"RES 0002h, H", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "H", new byte[]{(byte) 0xcb, (byte) 0x94}},
				{"RES 0002h, L", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "L", new byte[]{(byte) 0xcb, (byte) 0x95}},
				{"RES 0002h, [HL]", RES.class, ExpressionParameter.class, "0002h", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0xcb, (byte) 0x96}},
				{"RES 0002h, A", RES.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "A", new byte[]{(byte) 0xcb, (byte) 0x97}},
				{"RES 0004h, [IX+009ah]", RES.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IX+009ah]", new byte[]{(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, (byte)0xa6}},
				{"RES 0004h, [IY+009ah]", RES.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IY+009ah]", new byte[]{(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, (byte)0xa6}},
				{"SET 0002h, B", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "B", new byte[]{(byte) 0xcb, (byte) 0xd0}},
				{"SET 0002h, C", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "C", new byte[]{(byte) 0xcb, (byte) 0xd1}},
				{"SET 0002h, D", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "D", new byte[]{(byte) 0xcb, (byte) 0xd2}},
				{"SET 0002h, E", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "E", new byte[]{(byte) 0xcb, (byte) 0xd3}},
				{"SET 0002h, H", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "H", new byte[]{(byte) 0xcb, (byte) 0xd4}},
				{"SET 0002h, L", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "L", new byte[]{(byte) 0xcb, (byte) 0xd5}},
				{"SET 0002h, [HL]", SET.class, ExpressionParameter.class, "0002h", RegisterIndirectAddressing.class, "[HL]", new byte[]{(byte) 0xcb, (byte) 0xd6}},
				{"SET 0002h, A", SET.class, ExpressionParameter.class, "0002h", RegisterParameter.class, "A", new byte[]{(byte) 0xcb, (byte) 0xd7}},
				{"SET 0004h, [IX+009ah]", SET.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IX+009ah]", new byte[]{(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, (byte) 0xe6}},
				{"SET 0004h, [IY+009ah]", SET.class, ExpressionParameter.class, "0004h", IndexedAddressingParameter.class, "[IY+009ah]", new byte[]{(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, (byte) 0xe6}},
				{"OUT 0020h, A", OUT.class, ExpressionParameter.class, "0020h", RegisterParameter.class, "A", new byte[]{(byte) 0xd3, 0x20}},
				{"OUT C, B", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "B", new byte[]{(byte) 0xed, 0x41}},
				{"OUT C, C", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x49}},
				{"OUT C, D", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "D", new byte[]{(byte) 0xed, 0x51}},
				{"OUT C, E", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "E", new byte[]{(byte) 0xed, 0x59}},
				{"OUT C, H", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "H", new byte[]{(byte) 0xed, 0x61}},
				{"OUT C, L", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "L", new byte[]{(byte) 0xed, 0x69}},
				{"OUT C, A", OUT.class, RegisterParameter.class, "C", RegisterParameter.class, "A", new byte[]{(byte) 0xed, 0x79}},
				{"IN A, 0020h", IN.class, RegisterParameter.class, "A", ExpressionParameter.class, "0020h", new byte[]{(byte) 0xdb, 0x20}},
				{"IN B, C", IN.class, RegisterParameter.class, "B", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x40}},
				{"IN C, C", IN.class, RegisterParameter.class, "C", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x48}},
				{"IN D, C", IN.class, RegisterParameter.class, "D", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x50}},
				{"IN E, C", IN.class, RegisterParameter.class, "E", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x58}},
				{"IN H, C", IN.class, RegisterParameter.class, "H", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x60}},
				{"IN L, C", IN.class, RegisterParameter.class, "L", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x68}},
				{"IN A, C", IN.class, RegisterParameter.class, "A", RegisterParameter.class, "C", new byte[]{(byte) 0xed, 0x78}},
				{"CALL 12ffh", CALL.class, null, null, ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xcd, (byte) 0xff, 0x12}},
				{"CALLNZ 12ffh", CALL.class, ConditionParameter.class, "NZ", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xc4, (byte) 0xff, 0x12}}, 
				{"CALLZ 12ffh", CALL.class, ConditionParameter.class, "Z", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xcc, (byte) 0xff, 0x12}}, 
				{"CALLNC 12ffh", CALL.class, ConditionParameter.class, "NC", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xd4, (byte) 0xff, 0x12}}, 
				{"CALLC 12ffh", CALL.class, ConditionParameter.class, "C", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xdc, (byte) 0xff, 0x12}}, 
				{"CALLPO 12ffh", CALL.class, ConditionParameter.class, "PO", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xe4, (byte) 0xff, 0x12}}, 
				{"CALLPE 12ffh", CALL.class, ConditionParameter.class, "PE", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xec, (byte) 0xff, 0x12}}, 
				{"CALLP 12ffh", CALL.class, ConditionParameter.class, "P", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xf4, (byte) 0xff, 0x12}}, 
				{"CALLM 12ffh", CALL.class, ConditionParameter.class, "M", ExpressionParameter.class, "12ffh", new byte[]{(byte) 0xfc, (byte) 0xff, 0x12}},
				{"LD [BC], A", LD.class, RegisterIndirectAddressing.class, "[BC]", RegisterParameter.class, "A", new byte[]{0x02}},
				{"LD [DE], A", LD.class, RegisterIndirectAddressing.class, "[DE]", RegisterParameter.class, "A", new byte[]{0x12}},
				{"LD A, [BC]", LD.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[BC]", new byte[]{0x0a}},
				{"LD A, [DE]", LD.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[DE]", new byte[]{0x1a}},
				{"LD [HL], B", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "B", new byte[]{0x70}},
				{"LD [HL], C", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "C", new byte[]{0x71}},
				{"LD [HL], D", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "D", new byte[]{0x72}},
				{"LD [HL], E", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "E", new byte[]{0x73}},
				{"LD [HL], H", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "H", new byte[]{0x74}},
				{"LD [HL], L", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "L", new byte[]{0x75}},
				{"LD [HL], A", LD.class, RegisterIndirectAddressing.class, "[HL]", RegisterParameter.class, "A", new byte[]{0x77}},
				{"LD B, [HL]", LD.class, RegisterParameter.class, "B", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x46}},
				{"LD C, [HL]", LD.class, RegisterParameter.class, "C", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x4e}},
				{"LD D, [HL]", LD.class, RegisterParameter.class, "D", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x56}},
				{"LD E, [HL]", LD.class, RegisterParameter.class, "E", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x5e}},
				{"LD H, [HL]", LD.class, RegisterParameter.class, "H", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x66}},
				{"LD L, [HL]", LD.class, RegisterParameter.class, "L", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x6e}},
				{"LD A, [HL]", LD.class, RegisterParameter.class, "A", RegisterIndirectAddressing.class, "[HL]", new byte[]{0x7e}},
				{"LD B, B'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "B'", new byte[]{0x40}},
				{"LD B, C'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "C'", new byte[]{0x41}},
				{"LD B, D'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "D'", new byte[]{0x42}},
				{"LD B, E'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "E'", new byte[]{0x43}},
				{"LD B, H'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "H'", new byte[]{0x44}},
				{"LD B, L'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "L'", new byte[]{0x45}},
				{"LD B, A'", LD.class, RegisterParameter.class, "B", RegisterParameter.class, "A'", new byte[]{0x47}},
				{"LD C, B'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "B'", new byte[]{0x48}},
				{"LD C, C'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "C'", new byte[]{0x49}},
				{"LD C, D'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "D'", new byte[]{0x4a}},
				{"LD C, E'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "E'", new byte[]{0x4b}},
				{"LD C, H'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "H'", new byte[]{0x4c}},
				{"LD C, L'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "L'", new byte[]{0x4d}},
				{"LD C, A'", LD.class, RegisterParameter.class, "C", RegisterParameter.class, "A'", new byte[]{0x4f}},
				{"LD D, B'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "B'", new byte[]{0x50}},
				{"LD D, C'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "C'", new byte[]{0x51}},
				{"LD D, D'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "D'", new byte[]{0x52}},
				{"LD D, E'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "E'", new byte[]{0x53}},
				{"LD D, H'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "H'", new byte[]{0x54}},
				{"LD D, L'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "L'", new byte[]{0x55}},
				{"LD D, A'", LD.class, RegisterParameter.class, "D", RegisterParameter.class, "A'", new byte[]{0x57}},
				{"LD E, B'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "B'", new byte[]{0x58}},
				{"LD E, C'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "C'", new byte[]{0x59}},
				{"LD E, D'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "D'", new byte[]{0x5a}},
				{"LD E, E'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "E'", new byte[]{0x5b}},
				{"LD E, H'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "H'", new byte[]{0x5c}},
				{"LD E, L'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "L'", new byte[]{0x5d}},
				{"LD E, A'", LD.class, RegisterParameter.class, "E", RegisterParameter.class, "A'", new byte[]{0x5f}},
				{"LD H, B'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "B'", new byte[]{0x60}},
				{"LD H, C'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "C'", new byte[]{0x61}},
				{"LD H, D'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "D'", new byte[]{0x62}},
				{"LD H, E'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "E'", new byte[]{0x63}},
				{"LD H, H'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "H'", new byte[]{0x64}},
				{"LD H, L'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "L'", new byte[]{0x65}},
				{"LD H, A'", LD.class, RegisterParameter.class, "H", RegisterParameter.class, "A'", new byte[]{0x67}},
				{"LD L, B'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "B'", new byte[]{0x68}},
				{"LD L, C'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "C'", new byte[]{0x69}},
				{"LD L, D'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "D'", new byte[]{0x6a}},
				{"LD L, E'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "E'", new byte[]{0x6b}},
				{"LD L, H'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "H'", new byte[]{0x6c}},
				{"LD L, L'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "L'", new byte[]{0x6d}},
				{"LD L, A'", LD.class, RegisterParameter.class, "L", RegisterParameter.class, "A'", new byte[]{0x6f}},
				{"LD A, B'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "B'", new byte[]{0x78}},
				{"LD A, C'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "C'", new byte[]{0x79}},
				{"LD A, D'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "D'", new byte[]{0x7a}},
				{"LD A, E'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "E'", new byte[]{0x7b}},
				{"LD A, H'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "H'", new byte[]{0x7c}},
				{"LD A, L'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "L'", new byte[]{0x7d}},
				{"LD A, A'", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "A'", new byte[]{0x7f}},
				{"LD SP, HL", LD.class, RegisterPairParameter.class, "SP", RegisterPairParameter.class, "HL", new byte[]{(byte) 0xf9}},
				{"LD B, 004dh", LD.class, RegisterParameter.class, "B", ExpressionParameter.class, "004dh", new byte[]{0x06, 0x4d}},
				{"LD C, 004dh", LD.class, RegisterParameter.class, "C", ExpressionParameter.class, "004dh", new byte[]{0x0e, 0x4d}},
				{"LD D, 004dh", LD.class, RegisterParameter.class, "D", ExpressionParameter.class, "004dh", new byte[]{0x16, 0x4d}},
				{"LD E, 004dh", LD.class, RegisterParameter.class, "E", ExpressionParameter.class, "004dh", new byte[]{0x1e, 0x4d}},
				{"LD H, 004dh", LD.class, RegisterParameter.class, "H", ExpressionParameter.class, "004dh", new byte[]{0x26, 0x4d}},
				{"LD L, 004dh", LD.class, RegisterParameter.class, "L", ExpressionParameter.class, "004dh", new byte[]{0x2e, 0x4d}},
				{"LD [HL], 004dh", LD.class, RegisterIndirectAddressing.class, "[HL]", ExpressionParameter.class, "004dh", new byte[]{0x36, 0x4d}},
				{"LD A, 004dh", LD.class, RegisterParameter.class, "A", ExpressionParameter.class, "004dh", new byte[]{0x3e, 0x4d}},
				{"LD SP, IX", LD.class, RegisterPairParameter.class, "SP", RegisterPairParameter.class, "IX", new byte[]{(byte) 0xdd, (byte) 0xf9}},
				{"LD SP, IY", LD.class, RegisterPairParameter.class, "SP", RegisterPairParameter.class, "IY", new byte[]{(byte) 0xfd, (byte) 0xf9}},
				{"LD I, A", LD.class, RegisterParameter.class, "I", RegisterParameter.class, "A", new byte[]{(byte) 0xed, 0x47}},
				{"LD A, I", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "I", new byte[]{(byte) 0xed, 0x57}},
				{"LD R, A", LD.class, RegisterParameter.class, "R", RegisterParameter.class, "A", new byte[]{(byte) 0xed, 0x4f}},
				{"LD A, R", LD.class, RegisterParameter.class, "A", RegisterParameter.class, "R", new byte[]{(byte) 0xed, 0x5f}},
				{"LD BC, 8f00h", LD.class, RegisterPairParameter.class, "BC", ExpressionParameter.class, "8f00h", new byte[]{0x01, 0x00, (byte) 0x8f}},
				{"LD DE, 8f00h", LD.class, RegisterPairParameter.class, "DE", ExpressionParameter.class, "8f00h", new byte[]{0x11, 0x00, (byte) 0x8f}},
				{"LD HL, 8f00h", LD.class, RegisterPairParameter.class, "HL", ExpressionParameter.class, "8f00h", new byte[]{0x21, 0x00, (byte) 0x8f}},
				{"LD SP, 8f00h", LD.class, RegisterPairParameter.class, "SP", ExpressionParameter.class, "8f00h", new byte[]{0x31, 0x00, (byte) 0x8f}},
				{"LD [2100h], HL", LD.class, ImmediateAddressingParameter.class, "[2100h]", RegisterPairParameter.class, "HL", new byte[]{0x22, 0x00, 0x21}},
				{"LD HL, [2100h]", LD.class, RegisterPairParameter.class, "HL", ImmediateAddressingParameter.class, "[2100h]", new byte[]{0x2a, 0x00, 0x21}},
				{"LD [2100h], A", LD.class, ImmediateAddressingParameter.class, "[2100h]", RegisterParameter.class, "A", new byte[]{0x32, 0x00, 0x21}},
				{"LD A, [2100h]", LD.class, RegisterParameter.class, "A", ImmediateAddressingParameter.class, "[2100h]", new byte[]{0x3a, 0x00, 0x21}},
				{"LD [IX+002dh], B", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "B", new byte[]{(byte) 0xdd, 0x70, 0x2d}},
				{"LD [IX+002dh], C", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "C", new byte[]{(byte) 0xdd, 0x71, 0x2d}},
				{"LD [IX+002dh], D", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "D", new byte[]{(byte) 0xdd, 0x72, 0x2d}},
				{"LD [IX+002dh], E", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "E", new byte[]{(byte) 0xdd, 0x73, 0x2d}},
				{"LD [IX+002dh], H", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "H", new byte[]{(byte) 0xdd, 0x74, 0x2d}},
				{"LD [IX+002dh], L", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "L", new byte[]{(byte) 0xdd, 0x75, 0x2d}},
				{"LD [IX+002dh], A", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", RegisterParameter.class, "A", new byte[]{(byte) 0xdd, 0x77, 0x2d}},
				{"LD [IY+002dh], B", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "B", new byte[]{(byte) 0xfd, 0x70, 0x2d}},
				{"LD [IY+002dh], C", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "C", new byte[]{(byte) 0xfd, 0x71, 0x2d}},
				{"LD [IY+002dh], D", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "D", new byte[]{(byte) 0xfd, 0x72, 0x2d}},
				{"LD [IY+002dh], E", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "E", new byte[]{(byte) 0xfd, 0x73, 0x2d}},
				{"LD [IY+002dh], H", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "H", new byte[]{(byte) 0xfd, 0x74, 0x2d}},
				{"LD [IY+002dh], L", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "L", new byte[]{(byte) 0xfd, 0x75, 0x2d}},
				{"LD [IY+002dh], A", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", RegisterParameter.class, "A", new byte[]{(byte) 0xfd, 0x77, 0x2d}},
				{"LD B, [IX+002dh]", LD.class, RegisterParameter.class, "B", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x46, 0x2d}},
				{"LD C, [IX+002dh]", LD.class, RegisterParameter.class, "C", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x4e, 0x2d}},
				{"LD D, [IX+002dh]", LD.class, RegisterParameter.class, "D", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x56, 0x2d}},
				{"LD E, [IX+002dh]", LD.class, RegisterParameter.class, "E", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x5e, 0x2d}},
				{"LD H, [IX+002dh]", LD.class, RegisterParameter.class, "H", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x66, 0x2d}},
				{"LD L, [IX+002dh]", LD.class, RegisterParameter.class, "L", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x6e, 0x2d}},
				{"LD A, [IX+002dh]", LD.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IX+002dh]", new byte[]{(byte) 0xdd, 0x7e, 0x2d}},
				{"LD B, [IY+002dh]", LD.class, RegisterParameter.class, "B", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x46, 0x2d}},
				{"LD C, [IY+002dh]", LD.class, RegisterParameter.class, "C", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x4e, 0x2d}},
				{"LD D, [IY+002dh]", LD.class, RegisterParameter.class, "D", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x56, 0x2d}},
				{"LD E, [IY+002dh]", LD.class, RegisterParameter.class, "E", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x5e, 0x2d}},
				{"LD H, [IY+002dh]", LD.class, RegisterParameter.class, "H", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x66, 0x2d}},
				{"LD L, [IY+002dh]", LD.class, RegisterParameter.class, "L", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x6e, 0x2d}},
				{"LD A, [IY+002dh]", LD.class, RegisterParameter.class, "A", IndexedAddressingParameter.class, "[IY+002dh]", new byte[]{(byte) 0xfd, 0x7e, 0x2d}},
				{"LD [IX+002dh], 00bah", LD.class, IndexedAddressingParameter.class, "[IX+002dh]", ExpressionParameter.class, "00bah", new byte[]{(byte) 0xdd, 0x34, 0x2d, (byte) 0xba}},
				{"LD [IY+002dh], 00beh", LD.class, IndexedAddressingParameter.class, "[IY+002dh]", ExpressionParameter.class, "00beh", new byte[]{(byte) 0xfd, 0x34, 0x2d, (byte) 0xbe}},
				{"LD [5a00h], BC", LD.class, ImmediateAddressingParameter.class, "[5a00h]", RegisterPairParameter.class, "BC", new byte[]{(byte) 0xed, 0x43, 0x00, 0x5a}},
				{"LD [5a00h], DE", LD.class, ImmediateAddressingParameter.class, "[5a00h]", RegisterPairParameter.class, "DE", new byte[]{(byte) 0xed, 0x53, 0x00, 0x5a}},
				{"LD [5a00h], HL", LD.class, ImmediateAddressingParameter.class, "[5a00h]", RegisterPairParameter.class, "HL", new byte[]{0x22, 0x00, 0x5a}}, // this is an exception, shorter code exists
				{"LD [5a00h], SP", LD.class, ImmediateAddressingParameter.class, "[5a00h]", RegisterPairParameter.class, "SP", new byte[]{(byte) 0xed, 0x73, 0x00, 0x5a}},
				{"LD BC, [5a00h]", LD.class, RegisterPairParameter.class, "BC", ImmediateAddressingParameter.class, "[5a00h]", new byte[]{(byte) 0xed, 0x4b, 0x00, 0x5a}},
				{"LD DE, [5a00h]", LD.class, RegisterPairParameter.class, "DE", ImmediateAddressingParameter.class, "[5a00h]", new byte[]{(byte) 0xed, 0x5b, 0x00, 0x5a}},
				{"LD HL, [5a00h]", LD.class, RegisterPairParameter.class, "HL", ImmediateAddressingParameter.class, "[5a00h]", new byte[]{0x2a, 0x00, 0x5a}}, // this is an exception, shorter code exists
				{"LD SP, [5a00h]", LD.class, RegisterPairParameter.class, "SP", ImmediateAddressingParameter.class, "[5a00h]", new byte[]{(byte) 0xed, 0x7b, 0x00, 0x5a}},
				{"LD IX, cafeh", LD.class, RegisterPairParameter.class, "IX", ExpressionParameter.class, "cafeh", new byte[]{(byte) 0xdd, 0x21, (byte) 0xfe, (byte) 0xca}},
				{"LD IY, cafeh", LD.class, RegisterPairParameter.class, "IY", ExpressionParameter.class, "cafeh", new byte[]{(byte) 0xfd, 0x21, (byte) 0xfe, (byte) 0xca}},
				{"LD [de00h], IX", LD.class, ImmediateAddressingParameter.class, "[de00h]", RegisterPairParameter.class, "IX", new byte[]{(byte) 0xdd, 0x22, 0x00, (byte) 0xde}},
				{"LD [de00h], IY", LD.class, ImmediateAddressingParameter.class, "[de00h]", RegisterPairParameter.class, "IY", new byte[]{(byte) 0xfd, 0x22, 0x00, (byte) 0xde}},
				{"LD IX, [de00h]", LD.class, RegisterPairParameter.class, "IX", ImmediateAddressingParameter.class, "[de00h]", new byte[]{(byte) 0xdd, 0x2a, 0x00, (byte) 0xde}},
				{"LD IY, [de00h]", LD.class, RegisterPairParameter.class, "IY", ImmediateAddressingParameter.class, "[de00h]", new byte[]{(byte) 0xfd, 0x2a, 0x00, (byte) 0xde}}
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(InstructionArgumentProvider.class)
	public void testOneParameterInstructions(String testinstruction, Class<? extends TwoParameterInstruction> instructionclass,
			Class<? extends Parameter> targetparameterclass, String targetparameterstring,
			Class<? extends Parameter> sourceparameterclass, String sourceparameterstring,
			byte[] opcode) {
		Z80Instruction result = invokeParser(testinstruction);

		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result.getClass().isAssignableFrom(instructionclass), () -> String.format("Test instruction must be an instance of %s", instructionclass.getName()));
		
		Parameter resulttargetparameter = ((TwoParameterInstruction)result).getTarget();
		if (targetparameterclass != null) {
			assertTrue(resulttargetparameter.getClass().isAssignableFrom(targetparameterclass), () -> String.format("Target parameter must be an instance of %s, it was %s", targetparameterclass.getName(), resulttargetparameter.getClass().getName()));
			
			assertNotNull(resulttargetparameter, "Instruction target parameter must be presented");
			assertEquals(targetparameterstring, resulttargetparameter.getAssembly(), "Target parameter string does not match");
		} else {
			assertNull(resulttargetparameter, "Target parameter most not be presented");
		}
		
		Parameter resultsourceparameter = ((TwoParameterInstruction)result).getSource();
		
		assertTrue(resultsourceparameter.getClass().isAssignableFrom(sourceparameterclass), () -> String.format("Source parameter must be an instance of %s, it was %s", sourceparameterclass.getName(), resultsourceparameter.getClass().getName()));
			
		assertNotNull(resultsourceparameter, "Instruction source parameter must be presented");
		assertEquals(sourceparameterstring, resultsourceparameter.getAssembly(), "Source parameter string does not match");
		
		assertEquals(testinstruction, result.getAssembly(), "Instruction assembly does not match");
		
		CompilationUnit compilationUnit = new CompilationUnit();
		
		assertOpcode(opcode, result.getOpcode(compilationUnit));
	}
	
}
