package org.eaSTars.z80asm.assembler.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eaSTars.asm.ast.CompilationUnit;
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

public class NoParameterInstructionConverter extends Z80InstructionConverter<NoParameterInstruction> {
	
	private static class InstructionEntry {
		private Class<? extends NoParameterInstruction> instruction;
		
		private MaskedOpcode<NoParameterInstruction> mask;
		
		public InstructionEntry(Class<? extends NoParameterInstruction> instruction, MaskedOpcode<NoParameterInstruction> mask) {
			this.instruction = instruction;
			this.mask = mask;
		}
		
	}
	
	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(CCF.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte)0x3f})),
			new InstructionEntry(CPD.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa9})),
			new InstructionEntry(CPDR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb9})),
			new InstructionEntry(CPI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa1})),
			new InstructionEntry(CPIR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb1})),
			new InstructionEntry(CPL.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x2f})),
			new InstructionEntry(DAA.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x27})),
			new InstructionEntry(DI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xf3})),
			new InstructionEntry(EI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xfb})),
			new InstructionEntry(EXX.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xd9})),
			new InstructionEntry(HALT.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x76})),
			new InstructionEntry(IM0.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x46})),
			new InstructionEntry(IM1.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x56})),
			new InstructionEntry(IM2.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x5e})),
			new InstructionEntry(IND.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xaa})),
			new InstructionEntry(INDR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xba})),
			new InstructionEntry(INI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa2})),
			new InstructionEntry(INIR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb2})),
			new InstructionEntry(LDD.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa8})),
			new InstructionEntry(LDDR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb8})),
			new InstructionEntry(LDI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa0})),
			new InstructionEntry(LDIR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb0})),
			new InstructionEntry(NEG.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x44})),
			new InstructionEntry(NOP.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x00})),
			new InstructionEntry(OTDR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xbb})),
			new InstructionEntry(OTIR.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xb3})),
			new InstructionEntry(OUTD.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xab})),
			new InstructionEntry(OUTI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0xa3})),
			new InstructionEntry(RETI.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x4d})),
			new InstructionEntry(RETN.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x45})),
			new InstructionEntry(RLA.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x17})),
			new InstructionEntry(RLCA.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x07})),
			new InstructionEntry(RLD.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x6f})),
			new InstructionEntry(RRA.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x1f})),
			new InstructionEntry(RRCA.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x0f})),
			new InstructionEntry(RRD.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0xed, (byte) 0x67})),
			new InstructionEntry(SCF.class, new MaskedOpcode<NoParameterInstruction>(new byte[] {(byte) 0x37}))
	});
	
	private static Map<Class<? extends NoParameterInstruction>, MaskedOpcode<NoParameterInstruction>> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e.mask));
	
	private static Map<Integer, MaskedOpcodeMap<NoParameterInstruction>> reverse =
			instructionlist.stream().collect(Collectors.groupingBy(
					m -> m.mask.mask.length,
					Collectors.toMap(
							m -> new OpcodeMask<NoParameterInstruction>(m.mask.mask, m.mask.value),
							m -> m.instruction,
							(u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
							MaskedOpcodeMap<NoParameterInstruction>::new)));
	
	@Override
	protected MaskedOpcodeMap<NoParameterInstruction> getReverse(int index) {
		return reverse.get(index);
	}
	
	@Override
	public byte[] convert(CompilationUnit compilationUnit, NoParameterInstruction instruction) {
		byte[] result = null;
		MaskedOpcode<NoParameterInstruction> lookupresult = instructions.get(instruction.getClass());
		if (lookupresult != null) {
			result = Arrays.copyOf(lookupresult.value, lookupresult.value.length);
		}
		return result;
	}

}
