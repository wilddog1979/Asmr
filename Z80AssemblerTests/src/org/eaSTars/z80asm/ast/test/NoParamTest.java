package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;
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
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class NoParamTest extends InstructionTester {

	private static class InstructionArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
					{"NOP", NOP.class, new byte[] {0x00}},
					{"RLCA", RLCA.class, new byte[] {0x07}},
					{"RRCA", RRCA.class, new byte[] {0x0f}},
					{"RLA", RLA.class, new byte[] {0x17}},
					{"RRA", RRA.class, new byte[] {0x1f}},
					{"DAA", DAA.class, new byte[] {0x27}},
					{"CPL", CPL.class, new byte[] {0x2f}},
					{"SCF", SCF.class, new byte[] {0x37}},
					{"CCF", CCF.class, new byte[] {0x3f}},
					{"HALT", HALT.class, new byte[] {0x76}},
					{"EXX", EXX.class, new byte[] {(byte) 0xd9}},
					{"DI", DI.class, new byte[] {(byte) 0xf3}},
					{"EI", EI.class, new byte[] {(byte) 0xfb}},
					{"NEG", NEG.class, new byte[] {(byte) 0xed, 0x44}},
					{"IM0", IM0.class, new byte[] {(byte) 0xed, 0x46}},
					{"RETN", RETN.class, new byte[] {(byte) 0xed, 0x45}},
					{"RETI", RETI.class, new byte[] {(byte) 0xed, 0x4d}},
					{"IM1", IM1.class, new byte[] {(byte) 0xed, 0x56}},
					{"IM2", IM2.class, new byte[] {(byte) 0xed, 0x5e}},
					{"RRD", RRD.class, new byte[] {(byte) 0xed, 0x67}},
					{"RLD", RLD.class, new byte[] {(byte) 0xed, 0x6f}},
					{"LDI", LDI.class, new byte[] {(byte) 0xed, (byte) 0xa0}},
					{"CPI", CPI.class, new byte[] {(byte) 0xed, (byte) 0xa1}},
					{"INI", INI.class, new byte[] {(byte) 0xed, (byte) 0xa2}},
					{"OUTI", OUTI.class, new byte[] {(byte) 0xed, (byte) 0xa3}},
					{"LDD", LDD.class, new byte[] {(byte) 0xed, (byte) 0xa8}},
					{"CPD", CPD.class, new byte[] {(byte) 0xed, (byte) 0xa9}},
					{"IND", IND.class, new byte[] {(byte) 0xed, (byte) 0xaa}},
					{"OUTD", OUTD.class, new byte[] {(byte) 0xed, (byte) 0xab}},
					{"LDIR", LDIR.class, new byte[] {(byte) 0xed, (byte) 0xb0}},
					{"CPIR", CPIR.class, new byte[] {(byte) 0xed, (byte) 0xb1}},
					{"INIR", INIR.class, new byte[] {(byte) 0xed, (byte) 0xb2}},
					{"OTIR", OTIR.class, new byte[] {(byte) 0xed, (byte) 0xb3}},
					{"LDDR", LDDR.class, new byte[] {(byte) 0xed, (byte) 0xb8}},
					{"CPDR", CPDR.class, new byte[] {(byte) 0xed, (byte) 0xb9}},
					{"INDR", INDR.class, new byte[] {(byte) 0xed, (byte) 0xba}},
					{"OTDR", OTDR.class, new byte[] {(byte) 0xed, (byte) 0xbb}},
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(InstructionArgumentProvider.class)
	public void testNoParameterInstructions(String testinstruction, Class<? extends Z80Instruction> instructionclass, byte[] opcode) {
		Z80Instruction result = getZ80Instruction(testinstruction);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result.getClass().isAssignableFrom(instructionclass), () -> String.format("Test instruction must be an instance of %s", instructionclass.getName()));
		assertEquals(testinstruction, result.getAssembly(), "Instruction assembly does not match");
		
		CompilationUnit compilationUnit = new CompilationUnit();
		
		assertOpcode(opcode, result.getOpcode(compilationUnit));
	}
	
}
