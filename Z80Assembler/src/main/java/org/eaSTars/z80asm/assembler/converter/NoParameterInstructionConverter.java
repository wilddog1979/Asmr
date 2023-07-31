package org.eaSTars.z80asm.assembler.converter;

import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;
import org.eaSTars.z80asm.ast.instructions.noparam.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoParameterInstructionConverter extends AbstractZ80InstructionConverter<NoParameterInstruction> {

	private record InstructionEntry(Class<? extends NoParameterInstruction> instruction,
																	MaskedOpcode<NoParameterInstruction> mask) {

	}
	
	private static final List<InstructionEntry> instructionlist = Arrays.asList(
		new InstructionEntry(CCF.class, new MaskedOpcode<>(new byte[] {(byte)0x3f})),
		new InstructionEntry(CPD.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa9})),
		new InstructionEntry(CPDR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb9})),
		new InstructionEntry(CPI.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa1})),
		new InstructionEntry(CPIR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb1})),
		new InstructionEntry(CPL.class, new MaskedOpcode<>(new byte[] {(byte) 0x2f})),
		new InstructionEntry(DAA.class, new MaskedOpcode<>(new byte[] {(byte) 0x27})),
		new InstructionEntry(DI.class, new MaskedOpcode<>(new byte[] {(byte) 0xf3})),
		new InstructionEntry(EI.class, new MaskedOpcode<>(new byte[] {(byte) 0xfb})),
		new InstructionEntry(EXX.class, new MaskedOpcode<>(new byte[] {(byte) 0xd9})),
		new InstructionEntry(HALT.class, new MaskedOpcode<>(new byte[] {(byte) 0x76})),
		new InstructionEntry(IM0.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x46})),
		new InstructionEntry(IM1.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x56})),
		new InstructionEntry(IM2.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x5e})),
		new InstructionEntry(IND.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xaa})),
		new InstructionEntry(INDR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xba})),
		new InstructionEntry(INI.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa2})),
		new InstructionEntry(INIR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb2})),
		new InstructionEntry(LDD.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa8})),
		new InstructionEntry(LDDR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb8})),
		new InstructionEntry(LDI.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa0})),
		new InstructionEntry(LDIR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb0})),
		new InstructionEntry(NEG.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x44})),
		new InstructionEntry(NOP.class, new MaskedOpcode<>(new byte[] {(byte) 0x00})),
		new InstructionEntry(OTDR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xbb})),
		new InstructionEntry(OTIR.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xb3})),
		new InstructionEntry(OUTD.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xab})),
		new InstructionEntry(OUTI.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0xa3})),
		new InstructionEntry(RETI.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x4d})),
		new InstructionEntry(RETN.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x45})),
		new InstructionEntry(RLA.class, new MaskedOpcode<>(new byte[] {(byte) 0x17})),
		new InstructionEntry(RLCA.class, new MaskedOpcode<>(new byte[] {(byte) 0x07})),
		new InstructionEntry(RLD.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x6f})),
		new InstructionEntry(RRA.class, new MaskedOpcode<>(new byte[] {(byte) 0x1f})),
		new InstructionEntry(RRCA.class, new MaskedOpcode<>(new byte[] {(byte) 0x0f})),
		new InstructionEntry(RRD.class, new MaskedOpcode<>(new byte[] {(byte) 0xed, (byte) 0x67})),
		new InstructionEntry(SCF.class, new MaskedOpcode<>(new byte[] {(byte) 0x37})));
	
	private static final Map<Class<? extends NoParameterInstruction>, MaskedOpcode<NoParameterInstruction>> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e.mask));
	
	private static final Map<Integer, MaskedOpcodeMap<NoParameterInstruction>> reverse =
			instructionlist.stream().collect(Collectors.groupingBy(
					m -> m.mask.mask.length,
					Collectors.toMap(
							m -> new OpcodeMask<>(m.mask.mask, m.mask.value),
							m -> m.instruction,
							(u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
							MaskedOpcodeMap<NoParameterInstruction>::new)));
	
	@Override
	protected MaskedOpcodeMap<NoParameterInstruction> getReverse(int index) {
		return reverse.get(index);
	}
	
	@Override
	public byte[] convert(CompilationContext compilationContext, NoParameterInstruction instruction) {
		byte[] result = null;
		MaskedOpcode<NoParameterInstruction> lookupResult = instructions.get(instruction.getClass());
		if (lookupResult != null) {
			result = Arrays.copyOf(lookupResult.value, lookupResult.value.length);
		}
		return result;
	}

}
