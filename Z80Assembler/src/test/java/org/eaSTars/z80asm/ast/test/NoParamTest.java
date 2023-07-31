package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

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
					{"NOP", NOP.class},
					{"RLCA", RLCA.class},
					{"RRCA", RRCA.class},
					{"RLA", RLA.class},
					{"RRA", RRA.class},
					{"DAA", DAA.class},
					{"CPL", CPL.class},
					{"SCF", SCF.class},
					{"CCF", CCF.class},
					{"HALT", HALT.class},
					{"EXX", EXX.class},
					{"DI", DI.class},
					{"EI", EI.class},
					{"NEG", NEG.class},
					{"IM0", IM0.class},
					{"RETN", RETN.class},
					{"RETI", RETI.class},
					{"IM1", IM1.class},
					{"IM2", IM2.class},
					{"RRD", RRD.class},
					{"RLD", RLD.class},
					{"LDI", LDI.class},
					{"CPI", CPI.class},
					{"INI", INI.class},
					{"OUTI", OUTI.class},
					{"LDD", LDD.class},
					{"CPD", CPD.class},
					{"IND", IND.class},
					{"OUTD", OUTD.class},
					{"LDIR", LDIR.class},
					{"CPIR", CPIR.class},
					{"INIR", INIR.class},
					{"OTIR", OTIR.class},
					{"LDDR", LDDR.class},
					{"CPDR", CPDR.class},
					{"INDR", INDR.class},
					{"OTDR", OTDR.class}
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(InstructionArgumentProvider.class)
	public void testNoParameterInstructions(String testinstruction, Class<? extends Z80Instruction> instructionclass) {
		Z80Instruction result = getZ80Instruction(testinstruction);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result.getClass().isAssignableFrom(instructionclass), () -> String.format("Test instruction must be an instance of %s", instructionclass.getName()));
		assertEquals(testinstruction, result.getAssembly(), "Instruction assembly does not match");
	}
	
}
