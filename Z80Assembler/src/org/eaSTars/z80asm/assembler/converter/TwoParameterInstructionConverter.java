package org.eaSTars.z80asm.assembler.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eaSTars.asm.ast.CompilationUnit;
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

public class TwoParameterInstructionConverter extends Z80InstructionConverter<TwoParameterInstruction> {

	private static class InstructionEntry {
		private Class<? extends TwoParameterInstruction> instruction;
		
		private List<MaskedOpcode<TwoParameterInstruction>> masks;
		
		private InstructionAssemblyGenerator generator;
		
		public InstructionEntry(Class<? extends TwoParameterInstruction> instruction, List<MaskedOpcode<TwoParameterInstruction>> masks, InstructionAssemblyGenerator generator) {
			this.instruction = instruction;
			this.masks = masks;
			masks.forEach(m -> m.instruction = instruction);
			this.generator = generator;
		}
		
	}
	
	@FunctionalInterface
	protected static interface InstructionAssemblyGenerator {
		
		public byte[] generate(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks);
		
	}

	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(ADC.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(ADD.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(BIT.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(CALL.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(EX.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(IN.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(JP.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(JR.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(LD.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(OUT.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(RES.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(SBC.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(SET.class, Arrays.asList(), (c, i, m) -> null)
	});
	
	private static Map<Class<? extends TwoParameterInstruction>, InstructionEntry> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e));
	
	private static Map<Integer, MaskedOpcodeMap<TwoParameterInstruction>> reverse =
			instructionlist.stream().flatMap(e -> e.masks.stream()).collect(Collectors.groupingBy(
					m -> m.mask.length,
					Collectors.toMap(
							m -> new OpcodeMask<TwoParameterInstruction>(m.mask, m.value, m.extractor),
							m -> m.instruction,
							(u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
							MaskedOpcodeMap<TwoParameterInstruction>::new)));
	
	@Override
	protected MaskedOpcodeMap<TwoParameterInstruction> getReverse(int index) {
		return reverse.get(index);
	}

	@Override
	public byte[] convert(CompilationUnit compilationUnit, TwoParameterInstruction instruction) {
		byte[] result = null;
		InstructionEntry entry = instructions.get(instruction.getClass());
		if (entry != null && entry.generator != null) {
			result = entry.generator.generate(compilationUnit, instruction, entry.masks);
		}
		return result;
	}


}
