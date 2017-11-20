package org.eaSTars.z80asm.assembler.converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eaSTars.asm.assember.PushbackInputStream;
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
		
		private List<Byte> value;
		
		public InstructionEntry(Class<? extends NoParameterInstruction> instruction, List<Byte> value) {
			this.instruction = instruction;
			this.value = value;
		}
		
	}
	
	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(CCF.class, Arrays.asList((byte)0x3f)),
			new InstructionEntry(CPD.class, Arrays.asList((byte) 0xed, (byte) 0xa9)),
			new InstructionEntry(CPDR.class, Arrays.asList((byte) 0xed, (byte) 0xb9)),
			new InstructionEntry(CPI.class, Arrays.asList((byte) 0xed, (byte) 0xa1)),
			new InstructionEntry(CPIR.class, Arrays.asList((byte) 0xed, (byte) 0xb1)),
			new InstructionEntry(CPL.class, Arrays.asList((byte) 0x2f)),
			new InstructionEntry(DAA.class, Arrays.asList((byte) 0x27)),
			new InstructionEntry(DI.class, Arrays.asList((byte) 0xf3)),
			new InstructionEntry(EI.class, Arrays.asList((byte) 0xfb)),
			new InstructionEntry(EXX.class, Arrays.asList((byte) 0xd9)),
			new InstructionEntry(HALT.class, Arrays.asList((byte) 0x76)),
			new InstructionEntry(IM0.class, Arrays.asList((byte) 0xed, (byte) 0x46)),
			new InstructionEntry(IM1.class, Arrays.asList((byte) 0xed, (byte) 0x56)),
			new InstructionEntry(IM2.class, Arrays.asList((byte) 0xed, (byte) 0x5e)),
			new InstructionEntry(IND.class, Arrays.asList((byte) 0xed, (byte) 0xaa)),
			new InstructionEntry(INDR.class, Arrays.asList((byte) 0xed, (byte) 0xba)),
			new InstructionEntry(INI.class, Arrays.asList((byte) 0xed, (byte) 0xa2)),
			new InstructionEntry(INIR.class, Arrays.asList((byte) 0xed, (byte) 0xb2)),
			new InstructionEntry(LDD.class, Arrays.asList((byte) 0xed, (byte) 0xa8)),
			new InstructionEntry(LDDR.class, Arrays.asList((byte) 0xed, (byte) 0xb8)),
			new InstructionEntry(LDI.class, Arrays.asList((byte) 0xed, (byte) 0xa0)),
			new InstructionEntry(LDIR.class, Arrays.asList((byte) 0xed, (byte) 0xb0)),
			new InstructionEntry(NEG.class, Arrays.asList((byte) 0xed, (byte) 0x44)),
			new InstructionEntry(NOP.class, Arrays.asList((byte) 0x00)),
			new InstructionEntry(OTDR.class, Arrays.asList((byte) 0xed, (byte) 0xbb)),
			new InstructionEntry(OTIR.class, Arrays.asList((byte) 0xed, (byte) 0xb3)),
			new InstructionEntry(OUTD.class, Arrays.asList((byte) 0xed, (byte) 0xab)),
			new InstructionEntry(OUTI.class, Arrays.asList((byte) 0xed, (byte) 0xa3)),
			new InstructionEntry(RETI.class, Arrays.asList((byte) 0xed, (byte) 0x4d)),
			new InstructionEntry(RETN.class, Arrays.asList((byte) 0xed, (byte) 0x45)),
			new InstructionEntry(RLA.class, Arrays.asList((byte) 0x17)),
			new InstructionEntry(RLCA.class, Arrays.asList((byte) 0x07)),
			new InstructionEntry(RLD.class, Arrays.asList((byte) 0xed, (byte) 0x6f)),
			new InstructionEntry(RRA.class, Arrays.asList((byte) 0x1f)),
			new InstructionEntry(RRCA.class, Arrays.asList((byte) 0x0f)),
			new InstructionEntry(RRD.class, Arrays.asList((byte) 0xed, (byte) 0x67)),
			new InstructionEntry(SCF.class, Arrays.asList((byte) 0x37))
	});
	
	private static Map<Class<? extends NoParameterInstruction>, List<Byte>> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e.value));
	
	private static Map<Integer, Map<List<Byte>, Class<? extends NoParameterInstruction>>> reverse =
			instructionlist.stream().collect(Collectors.groupingBy(e -> e.value.size(), Collectors.toMap(e2 -> e2.value, e2 -> e2.instruction)));
	
	@Override
	public byte[] convert(CompilationUnit compilationUnit, NoParameterInstruction instruction) {
		byte[] result = null;
		List<Byte> lookupresult = instructions.get(instruction.getClass());
		if (lookupresult != null) {
			result = new byte[lookupresult.size()];
			for (int i = 0; i < lookupresult.size(); ++i) {
				result[i] = lookupresult.get(i);
			}
		}
		return result;
	}

	private NoParameterInstruction convert2byte(PushbackInputStream pushbackInputStream, int firstbyte) throws IOException {
		NoParameterInstruction result = null;

		int secondbyte = pushbackInputStream.read();
		if (secondbyte != -1) {
			result = Optional.ofNullable(reverse.get(2).get(Arrays.asList((byte) firstbyte, (byte) secondbyte)))
					.map(c -> instanciate(c)).orElseGet(() -> null);
			if (result == null) {
				pushbackInputStream.unread((byte) secondbyte);
			}
		}

		return result;
	}

	@Override
	public NoParameterInstruction convert(PushbackInputStream pushbackInputStream) throws IOException {
		NoParameterInstruction result = null;

		int firstbyte = pushbackInputStream.read();
		if (firstbyte != -1) {
			result = Optional.ofNullable(reverse.get(1).get(Arrays.asList((byte) firstbyte)))
					.map(c -> instanciate(c)).orElseGet(() -> null);

			if (result == null && (result = convert2byte(pushbackInputStream, firstbyte)) == null) {
				pushbackInputStream.unread((byte) firstbyte);
			}
		}

		return result;
	}

}
