package org.eaSTars.z80asm.converter.test;

import org.eaSTars.asm.assember.PushbackInputStream;
import org.eaSTars.z80asm.assembler.converter.OneParameterInstructionConverter;
import org.eaSTars.z80asm.ast.expression.ConstantValueExpression;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.oneparam.*;
import org.eaSTars.z80asm.ast.parameter.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OneParameterConverterTest {

	private static class ConverterArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(new Object[][] {
				{new INC(new RegisterPairParameter(RegisterPair.BC)), new byte[] {0x03}},
				{new INC(new RegisterPairParameter(RegisterPair.DE)), new byte[] {0x13}},
				{new INC(new RegisterPairParameter(RegisterPair.HL)), new byte[] {0x23}},
				{new INC(new RegisterPairParameter(RegisterPair.SP)), new byte[] {0x33}},
				{new INC(new RegisterParameter(Register.B)), new byte[] {0x04}},
				{new INC(new RegisterParameter(Register.C)), new byte[] {0x0c}},
				{new INC(new RegisterParameter(Register.D)), new byte[] {0x14}},
				{new INC(new RegisterParameter(Register.E)), new byte[] {0x1c}},
				{new INC(new RegisterParameter(Register.H)), new byte[] {0x24}},
				{new INC(new RegisterParameter(Register.L)), new byte[] {0x2c}},
				{new INC(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x34}},
				{new INC(new RegisterParameter(Register.A)), new byte[] {0x3c}},
				{new INC(new RegisterPairParameter(RegisterPair.IX)), new byte[] {(byte) 0xdd, 0x23}},
				{new INC(new RegisterPairParameter(RegisterPair.IY)), new byte[] {(byte) 0xfd, 0x23}},
				{new INC(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, 0x34, 0x30}},
				{new INC(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, 0x34, 0x30}},
				{new DEC(new RegisterPairParameter(RegisterPair.BC)), new byte[] {0x0b}},
				{new DEC(new RegisterPairParameter(RegisterPair.DE)), new byte[] {0x1b}},
				{new DEC(new RegisterPairParameter(RegisterPair.HL)), new byte[] {0x2b}},
				{new DEC(new RegisterPairParameter(RegisterPair.SP)), new byte[] {0x3b}},
				{new DEC(new RegisterParameter(Register.B)), new byte[] {0x05}},
				{new DEC(new RegisterParameter(Register.C)), new byte[] {0x0d}},
				{new DEC(new RegisterParameter(Register.D)), new byte[] {0x15}},
				{new DEC(new RegisterParameter(Register.E)), new byte[] {0x1d}},
				{new DEC(new RegisterParameter(Register.H)), new byte[] {0x25}},
				{new DEC(new RegisterParameter(Register.L)), new byte[] {0x2d}},
				{new DEC(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x35}},
				{new DEC(new RegisterParameter(Register.A)), new byte[] {0x3d}},
				{new DEC(new RegisterPairParameter(RegisterPair.IX)), new byte[] {(byte) 0xdd, 0x2b}},
				{new DEC(new RegisterPairParameter(RegisterPair.IY)), new byte[] {(byte) 0xfd, 0x2b}},
				{new DEC(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, 0x35, 0x30}},
				{new DEC(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, 0x35, 0x30}},
				{new SUB(new RegisterParameter(Register.B)), new byte[] {(byte) 0x90}},
				{new SUB(new RegisterParameter(Register.C)), new byte[] {(byte) 0x91}},
				{new SUB(new RegisterParameter(Register.D)), new byte[] {(byte) 0x92}},
				{new SUB(new RegisterParameter(Register.E)), new byte[] {(byte) 0x93}},
				{new SUB(new RegisterParameter(Register.H)), new byte[] {(byte) 0x94}},
				{new SUB(new RegisterParameter(Register.L)), new byte[] {(byte) 0x95}},
				{new SUB(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0x96}},
				{new SUB(new RegisterParameter(Register.A)), new byte[] {(byte) 0x97}},
				{new SUB(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x5f)), 8)), new byte[] {(byte) 0xd6, 0x5f}},
				{new SUB(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0x96, 0x30}},
				{new SUB(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0x96, 0x30}},
				{new AND(new RegisterParameter(Register.B)), new byte[] {(byte) 0xa0}},
				{new AND(new RegisterParameter(Register.C)), new byte[] {(byte) 0xa1}},
				{new AND(new RegisterParameter(Register.D)), new byte[] {(byte) 0xa2}},
				{new AND(new RegisterParameter(Register.E)), new byte[] {(byte) 0xa3}},
				{new AND(new RegisterParameter(Register.H)), new byte[] {(byte) 0xa4}},
				{new AND(new RegisterParameter(Register.L)), new byte[] {(byte) 0xa5}},
				{new AND(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xa6}},
				{new AND(new RegisterParameter(Register.A)), new byte[] {(byte) 0xa7}},
				{new AND(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x5f)), 8)), new byte[] {(byte) 0xe6, 0x5f}},
				{new AND(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xa6, 0x30}},
				{new AND(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xa6, 0x30}},
				{new XOR(new RegisterParameter(Register.B)), new byte[] {(byte) 0xa8}},
				{new XOR(new RegisterParameter(Register.C)), new byte[] {(byte) 0xa9}},
				{new XOR(new RegisterParameter(Register.D)), new byte[] {(byte) 0xaa}},
				{new XOR(new RegisterParameter(Register.E)), new byte[] {(byte) 0xab}},
				{new XOR(new RegisterParameter(Register.H)), new byte[] {(byte) 0xac}},
				{new XOR(new RegisterParameter(Register.L)), new byte[] {(byte) 0xad}},
				{new XOR(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xae}},
				{new XOR(new RegisterParameter(Register.A)), new byte[] {(byte) 0xaf}},
				{new XOR(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x5f)), 8)), new byte[] {(byte) 0xee, 0x5f}},
				{new XOR(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xae, 0x30}},
				{new XOR(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xae, 0x30}},
				{new OR(new RegisterParameter(Register.B)), new byte[] {(byte) 0xb0}},
				{new OR(new RegisterParameter(Register.C)), new byte[] {(byte) 0xb1}},
				{new OR(new RegisterParameter(Register.D)), new byte[] {(byte) 0xb2}},
				{new OR(new RegisterParameter(Register.E)), new byte[] {(byte) 0xb3}},
				{new OR(new RegisterParameter(Register.H)), new byte[] {(byte) 0xb4}},
				{new OR(new RegisterParameter(Register.L)), new byte[] {(byte) 0xb5}},
				{new OR(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xb6}},
				{new OR(new RegisterParameter(Register.A)), new byte[] {(byte) 0xb7}},
				{new OR(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x5f)), 8)), new byte[] {(byte) 0xf6, 0x5f}},
				{new OR(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xb6, 0x30}},
				{new OR(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xb6, 0x30}},
				{new CP(new RegisterParameter(Register.B)), new byte[] {(byte) 0xb8}},
				{new CP(new RegisterParameter(Register.C)), new byte[] {(byte) 0xb9}},
				{new CP(new RegisterParameter(Register.D)), new byte[] {(byte) 0xba}},
				{new CP(new RegisterParameter(Register.E)), new byte[] {(byte) 0xbb}},
				{new CP(new RegisterParameter(Register.H)), new byte[] {(byte) 0xbc}},
				{new CP(new RegisterParameter(Register.L)), new byte[] {(byte) 0xbd}},
				{new CP(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xbe}},
				{new CP(new RegisterParameter(Register.A)), new byte[] {(byte) 0xbf}},
				{new CP(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x5f)), 8)), new byte[] {(byte) 0xfe, 0x5f}},
				{new CP(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xbe, 0x30}},
				{new CP(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xbe, 0x30}},
				{new POP(new RegisterPairParameter(RegisterPair.BC)), new byte[] {(byte) 0xc1}},
				{new POP(new RegisterPairParameter(RegisterPair.DE)), new byte[] {(byte) 0xd1}},
				{new POP(new RegisterPairParameter(RegisterPair.HL)), new byte[] {(byte) 0xe1}},
				{new POP(new RegisterPairParameter(RegisterPair.AF)), new byte[] {(byte) 0xf1}},
				{new POP(new RegisterPairParameter(RegisterPair.IX)), new byte[] {(byte) 0xdd, (byte) 0xe1}},
				{new POP(new RegisterPairParameter(RegisterPair.IY)), new byte[] {(byte) 0xfd, (byte) 0xe1}},
				{new PUSH(new RegisterPairParameter(RegisterPair.BC)), new byte[] {(byte) 0xc5}},
				{new PUSH(new RegisterPairParameter(RegisterPair.DE)), new byte[] {(byte) 0xd5}},
				{new PUSH(new RegisterPairParameter(RegisterPair.HL)), new byte[] {(byte) 0xe5}},
				{new PUSH(new RegisterPairParameter(RegisterPair.AF)), new byte[] {(byte) 0xf5}},
				{new PUSH(new RegisterPairParameter(RegisterPair.IX)), new byte[] {(byte) 0xdd, (byte) 0xe5}},
				{new PUSH(new RegisterPairParameter(RegisterPair.IY)), new byte[] {(byte) 0xfd, (byte) 0xe5}},
				{new RET(), new byte[] {(byte) 0xc9}},
				{new RET(new ConditionParameter(Condition.NZ)), new byte[] {(byte) 0xc0}},
				{new RET(new ConditionParameter(Condition.Z)), new byte[] {(byte) 0xc8}},
				{new RET(new ConditionParameter(Condition.NC)), new byte[] {(byte) 0xd0}},
				{new RET(new ConditionParameter(Condition.C)), new byte[] {(byte) 0xd8}},
				{new RET(new ConditionParameter(Condition.PO)), new byte[] {(byte) 0xe0}},
				{new RET(new ConditionParameter(Condition.PE)), new byte[] {(byte) 0xe8}},
				{new RET(new ConditionParameter(Condition.P)), new byte[] {(byte) 0xf0}},
				{new RET(new ConditionParameter(Condition.M)), new byte[] {(byte) 0xf8}},
				{new RST(new ConstantValueParameter("00")), new byte[] {(byte) 0xc7}},
				{new RST(new ConstantValueParameter("08")), new byte[] {(byte) 0xcf}},
				{new RST(new ConstantValueParameter("10")), new byte[] {(byte) 0xd7}},
				{new RST(new ConstantValueParameter("18")), new byte[] {(byte) 0xdf}},
				{new RST(new ConstantValueParameter("20")), new byte[] {(byte) 0xe7}},
				{new RST(new ConstantValueParameter("28")), new byte[] {(byte) 0xef}},
				{new RST(new ConstantValueParameter("30")), new byte[] {(byte) 0xf7}},
				{new RST(new ConstantValueParameter("38")), new byte[] {(byte) 0xff}},
				{new DJNZ(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0)), 8)), new byte[] {0x10, (byte) 0xfe}},
				{new RLC(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x00}},
				{new RLC(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x01}},
				{new RLC(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x02}},
				{new RLC(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x03}},
				{new RLC(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x04}},
				{new RLC(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x05}},
				{new RLC(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x06}},
				{new RLC(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x07}},
				{new RLC(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x06}},
				{new RLC(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x06}},
				{new RRC(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x08}},
				{new RRC(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x09}},
				{new RRC(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x0a}},
				{new RRC(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x0b}},
				{new RRC(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x0c}},
				{new RRC(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x0d}},
				{new RRC(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x0e}},
				{new RRC(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x0f}},
				{new RRC(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x0e}},
				{new RRC(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x0e}},
				{new RL(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x10}},
				{new RL(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x11}},
				{new RL(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x12}},
				{new RL(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x13}},
				{new RL(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x14}},
				{new RL(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x15}},
				{new RL(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x16}},
				{new RL(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x17}},
				{new RL(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x16}},
				{new RL(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x16}},
				{new RR(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x18}},
				{new RR(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x19}},
				{new RR(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x1a}},
				{new RR(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x1b}},
				{new RR(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x1c}},
				{new RR(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x1d}},
				{new RR(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x1e}},
				{new RR(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x1f}},
				{new RR(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x1e}},
				{new RR(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x1e}},
				{new SLA(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x20}},
				{new SLA(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x21}},
				{new SLA(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x22}},
				{new SLA(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x23}},
				{new SLA(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x24}},
				{new SLA(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x25}},
				{new SLA(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x26}},
				{new SLA(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x27}},
				{new SLA(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x26}},
				{new SLA(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x26}},
				{new SRA(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x28}},
				{new SRA(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x29}},
				{new SRA(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x2a}},
				{new SRA(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x2b}},
				{new SRA(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x2c}},
				{new SRA(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x2d}},
				{new SRA(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x2e}},
				{new SRA(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x2f}},
				{new SRA(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x2e}},
				{new SRA(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x2e}},
				{new SRL(new RegisterParameter(Register.B)), new byte[] {(byte) 0xca, 0x38}},
				{new SRL(new RegisterParameter(Register.C)), new byte[] {(byte) 0xca, 0x39}},
				{new SRL(new RegisterParameter(Register.D)), new byte[] {(byte) 0xca, 0x3a}},
				{new SRL(new RegisterParameter(Register.E)), new byte[] {(byte) 0xca, 0x3b}},
				{new SRL(new RegisterParameter(Register.H)), new byte[] {(byte) 0xca, 0x3c}},
				{new SRL(new RegisterParameter(Register.L)), new byte[] {(byte) 0xca, 0x3d}},
				{new SRL(new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xca, 0x3e}},
				{new SRL(new RegisterParameter(Register.A)), new byte[] {(byte) 0xca, 0x3f}},
				{new SRL(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xdd, (byte) 0xca, 0x30, 0x3e}},
				{new SRL(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x30)), 8))), new byte[] {(byte) 0xfd, (byte) 0xca, 0x30, 0x3e}}
			}).map(Arguments::of);
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(ConverterArgumentProvider.class)
	public void testOneParameterConverterFromInstruction(OneParameterInstruction instruction, byte[] assembly) {
		byte[] result = new OneParameterInstructionConverter().convert(null, instruction);
		
		assertNotNull(result, "Converted result expected");
		assertArrayEquals(assembly, result, "Opcode must be equal");
	}
	
	@ParameterizedTest
	@ArgumentsSource(ConverterArgumentProvider.class)
	public void testOneParameterConverterToInstruction(OneParameterInstruction instruction, byte[] assembly) throws IOException {
		OneParameterInstruction result = new OneParameterInstructionConverter().convert(new PushbackInputStream(new ByteArrayInputStream(assembly)));
			
		assertNotNull(result, "Converted result expected");
		assertEquals(instruction.getClass(), result.getClass(), "Class type must match");
		assertEquals(instruction.getParameter(), result.getParameter(), "Parameter must match");
	}
	
	@Test
	public void testUnknownInstructionOneByte() throws IOException {
		PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01}));
		OneParameterInstruction result = new OneParameterInstructionConverter().convert(pis);
			
		assertNull(result, "Converted result not expected");
		assertEquals(0x01, pis.read(), "Rolled back value expected");
		assertEquals(-1, pis.read(), "End of stream expected");
	}
	
	@Test
	public void testUnknownInstructionTwoByte() throws IOException {
		PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01, 0x02}));
		OneParameterInstruction result = new OneParameterInstructionConverter().convert(pis);
			
		assertNull(result, "Converted result not expected");
		assertEquals(0x01, pis.read(), "First rolled back value expected");
		assertEquals(0x02, pis.read(), "Second rolled back value expected");
		assertEquals(-1, pis.read(), "End of stream expected");
	}
	
	@Test
	public void testUnknownInstructionThreeByte() throws IOException {
		PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01, 0x02, 0x03}));
		OneParameterInstruction result = new OneParameterInstructionConverter().convert(pis);
			
		assertNull(result, "Converted result not expected");
		assertEquals(0x01, pis.read(), "First rolled back value expected");
		assertEquals(0x02, pis.read(), "Second rolled back value expected");
		assertEquals(0x03, pis.read(), "Third rolled back value expected");
		assertEquals(-1, pis.read(), "End of stream expected");
	}
	
	@Test
	public void testUnknownInstructionFourByte() throws IOException {
		PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01, 0x02, 0x03, 0x04}));
		OneParameterInstruction result = new OneParameterInstructionConverter().convert(pis);
			
		assertNull(result, "Converted result not expected");
		assertEquals(0x01, pis.read(), "First rolled back value expected");
		assertEquals(0x02, pis.read(), "Second rolled back value expected");
		assertEquals(0x03, pis.read(), "Third rolled back value expected");
		assertEquals(0x04, pis.read(), "Fourth rolled back value expected");
		assertEquals(-1, pis.read(), "End of stream expected");
	}
	
}
