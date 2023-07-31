package org.eaSTars.z80asm.converter.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.eaSTars.asm.assember.PushbackInputStream;
import org.eaSTars.z80asm.assembler.converter.NoParameterInstructionConverter;
import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.noparam.CCF;
import org.eaSTars.z80asm.ast.instructions.noparam.CPD;
import org.eaSTars.z80asm.ast.instructions.noparam.CPDR;
import org.eaSTars.z80asm.ast.instructions.noparam.CPI;
import org.eaSTars.z80asm.ast.instructions.noparam.CPIR;
import org.eaSTars.z80asm.ast.instructions.noparam.CPL;
import org.eaSTars.z80asm.ast.instructions.noparam.DAA;
import org.eaSTars.z80asm.ast.instructions.noparam.DI;
import org.eaSTars.z80asm.ast.instructions.noparam.EI;
import org.eaSTars.z80asm.ast.instructions.noparam.EXX;
import org.eaSTars.z80asm.ast.instructions.noparam.HALT;
import org.eaSTars.z80asm.ast.instructions.noparam.IM0;
import org.eaSTars.z80asm.ast.instructions.noparam.IM1;
import org.eaSTars.z80asm.ast.instructions.noparam.IM2;
import org.eaSTars.z80asm.ast.instructions.noparam.IND;
import org.eaSTars.z80asm.ast.instructions.noparam.INDR;
import org.eaSTars.z80asm.ast.instructions.noparam.INI;
import org.eaSTars.z80asm.ast.instructions.noparam.INIR;
import org.eaSTars.z80asm.ast.instructions.noparam.LDD;
import org.eaSTars.z80asm.ast.instructions.noparam.LDDR;
import org.eaSTars.z80asm.ast.instructions.noparam.LDI;
import org.eaSTars.z80asm.ast.instructions.noparam.LDIR;
import org.eaSTars.z80asm.ast.instructions.noparam.NEG;
import org.eaSTars.z80asm.ast.instructions.noparam.NOP;
import org.eaSTars.z80asm.ast.instructions.noparam.OTDR;
import org.eaSTars.z80asm.ast.instructions.noparam.OTIR;
import org.eaSTars.z80asm.ast.instructions.noparam.OUTD;
import org.eaSTars.z80asm.ast.instructions.noparam.OUTI;
import org.eaSTars.z80asm.ast.instructions.noparam.RETI;
import org.eaSTars.z80asm.ast.instructions.noparam.RETN;
import org.eaSTars.z80asm.ast.instructions.noparam.RLA;
import org.eaSTars.z80asm.ast.instructions.noparam.RLCA;
import org.eaSTars.z80asm.ast.instructions.noparam.RLD;
import org.eaSTars.z80asm.ast.instructions.noparam.RRA;
import org.eaSTars.z80asm.ast.instructions.noparam.RRCA;
import org.eaSTars.z80asm.ast.instructions.noparam.RRD;
import org.eaSTars.z80asm.ast.instructions.noparam.SCF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class NoParameterConverterTest {

	private static class ConverterArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{new NOP(), new byte[] {0x00}},
				{new RLCA(), new byte[] {0x07}},
				{new RRCA(), new byte[] {0x0f}},
				{new RLA(), new byte[] {0x17}},
				{new RRA(), new byte[] {0x1f}},
				{new DAA(), new byte[] {0x27}},
				{new CPL(), new byte[] {0x2f}},
				{new SCF(), new byte[] {0x37}},
				{new CCF(), new byte[] {0x3f}},
				{new HALT(), new byte[] {0x76}},
				{new EXX(), new byte[] {(byte) 0xd9}},
				{new DI(), new byte[] {(byte) 0xf3}},
				{new EI(), new byte[] {(byte) 0xfb}},
				{new NEG(), new byte[] {(byte) 0xed, 0x44}},
				{new IM0(), new byte[] {(byte) 0xed, 0x46}},
				{new RETN(), new byte[] {(byte) 0xed, 0x45}},
				{new RETI(), new byte[] {(byte) 0xed, 0x4d}},
				{new IM1(), new byte[] {(byte) 0xed, 0x56}},
				{new IM2(), new byte[] {(byte) 0xed, 0x5e}},
				{new RRD(), new byte[] {(byte) 0xed, 0x67}},
				{new RLD(), new byte[] {(byte) 0xed, 0x6f}},
				{new LDI(), new byte[] {(byte) 0xed, (byte) 0xa0}},
				{new CPI(), new byte[] {(byte) 0xed, (byte) 0xa1}},
				{new INI(), new byte[] {(byte) 0xed, (byte) 0xa2}},
				{new OUTI(), new byte[] {(byte) 0xed, (byte) 0xa3}},
				{new LDD(), new byte[] {(byte) 0xed, (byte) 0xa8}},
				{new CPD(), new byte[] {(byte) 0xed, (byte) 0xa9}},
				{new IND(), new byte[] {(byte) 0xed, (byte) 0xaa}},
				{new OUTD(), new byte[] {(byte) 0xed, (byte) 0xab}},
				{new LDIR(), new byte[] {(byte) 0xed, (byte) 0xb0}},
				{new CPIR(), new byte[] {(byte) 0xed, (byte) 0xb1}},
				{new INIR(), new byte[] {(byte) 0xed, (byte) 0xb2}},
				{new OTIR(), new byte[] {(byte) 0xed, (byte) 0xb3}},
				{new LDDR(), new byte[] {(byte) 0xed, (byte) 0xb8}},
				{new CPDR(), new byte[] {(byte) 0xed, (byte) 0xb9}},
				{new INDR(), new byte[] {(byte) 0xed, (byte) 0xba}},
				{new OTDR(), new byte[] {(byte) 0xed, (byte) 0xbb}},
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(ConverterArgumentProvider.class)
	public void testNoParameterConverterFromInstruction(NoParameterInstruction instruction, byte[] assembly) {
		byte[] result = new NoParameterInstructionConverter().convert(null, instruction);
		
		assertNotNull(result, "Converted result expected");
		assertArrayEquals(assembly, result, "Opcode must be equal");
	}
	
	@ParameterizedTest
	@ArgumentsSource(ConverterArgumentProvider.class)
	public void testNoParameterConverterToInstruction(NoParameterInstruction instruction, byte[] assembly) {
		try {
			NoParameterInstruction result = new NoParameterInstructionConverter().convert(new PushbackInputStream(new ByteArrayInputStream(assembly)));
			
			assertNotNull(result, "Converted result expected");
			assertEquals(instruction.getClass(), result.getClass(), "Class type must match");
		} catch (IOException e) {
			fail("Unexpected exception", e);
		}
	}
	
	@Test
	public void testUnknownInstructionOneByte() {
		try {
			PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01}));
			NoParameterInstruction result = new NoParameterInstructionConverter().convert(pis);
			
			assertNull(result, "Converted result not expected");
			assertEquals(0x01, pis.read(), "Rolled back value expected");
			assertEquals(-1, pis.read(), "End of stream expected");
		} catch (IOException e) {
			fail("Unexpected exception", e);
		}
	}
	
	@Test
	public void testUnknownInstructionTwoByte() {
		try {
			PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01, 0x02}));
			NoParameterInstruction result = new NoParameterInstructionConverter().convert(pis);
			
			assertNull(result, "Converted result not expected");
			assertEquals(0x01, pis.read(), "First rolled back value expected");
			assertEquals(0x02, pis.read(), "Second rolled back value expected");
			assertEquals(-1, pis.read(), "End of stream expected");
		} catch (IOException e) {
			fail("Unexpected exception", e);
		}
	}
	
}
