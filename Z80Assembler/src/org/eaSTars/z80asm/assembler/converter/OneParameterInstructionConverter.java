package org.eaSTars.z80asm.assembler.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eaSTars.asm.ast.CompilationUnit;
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
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;

public class OneParameterInstructionConverter extends Z80InstructionConverter<OneParameterInstruction> {
	
	private static class InstructionEntry {
		private Class<? extends OneParameterInstruction> instruction;
		
		private List<MaskedOpcode<OneParameterInstruction>> masks;
		
		private InstructionAssemblyGenerator generator;
		
		public InstructionEntry(Class<? extends OneParameterInstruction> instruction, List<MaskedOpcode<OneParameterInstruction>> masks, InstructionAssemblyGenerator generator) {
			this.instruction = instruction;
			this.masks = masks;
			masks.forEach(m -> m.instruction = instruction);
			this.generator = generator;
		}
		
	}
	
	@FunctionalInterface
	protected static interface InstructionAssemblyGenerator {
		
		public byte[] generate(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks);
		
	}
	
	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(AND.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xa0}, (r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xe6, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1]))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xa6, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(CP.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0xb8}, (r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xfe, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1]))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xbe, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(DEC.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xcf}, new byte[] {0x0b}, (r, v) -> r.setParameter(reverseRegisterSS((v[0] >> 4) & 0x03))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xc7}, new byte[] {0x05}, (r, v) -> r.setParameter(reverseRegisterRH((v[0] >> 3) & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, 0x2b}, (r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, 0x35, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateINCDEC(c, i, m)),
			new InstructionEntry(DJNZ.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00},  new byte[] {0x10, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1] + 2)))
					), (c, i, m) -> generateDJNZ(c, i, m)) ,
			new InstructionEntry(INC.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xcf}, new byte[] {0x03}, (r, v) -> r.setParameter(reverseRegisterSS((v[0] >> 4) & 0x03))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xc7}, new byte[] {0x04}, (r, v) -> r.setParameter(reverseRegisterRH((v[0] >> 3) & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, 0x23}, (r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, 0x34, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateINCDEC(c, i, m)),
			new InstructionEntry(OR.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xb0}, (r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xf6, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1]))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xb6, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(POP.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xcf}, new byte[] {(byte) 0xc1}, (r, v) -> r.setParameter(reverseRegisterQQ((v[0] >> 4) & 0x03))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe1}, (r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00)))
					), (c, i, m) -> generatePUSHPOP(c, i, m)),
			new InstructionEntry(PUSH.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xcf}, new byte[] {(byte) 0xc5}, (r, v) -> r.setParameter(reverseRegisterQQ((v[0] >> 4) & 0x03))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe5}, (r, v) -> r.setParameter(reverseIXIY((v[0] & 0x20) == 0x00)))
					), (c, i, m) -> generatePUSHPOP(c, i, m)),
			new InstructionEntry(RET.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xc9}, (r, v) -> r.setParameter(null)),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xc7}, new byte[] {(byte) 0xc0}, (r, v) -> r.setParameter(reverseCondition((v[0] >> 3) & 0x07)))
					), (c, i, m) -> generateRET(c, i, m)),
			new InstructionEntry(RL.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x10}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x16}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RLC.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x00}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x06}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RR.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x18}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x1e}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RRC.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x08}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x0e}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RST.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xc7}, new byte[] {(byte) 0xc7}, (r, v) -> r.setParameter(reverseRSTValue(v[0] & 0x38)))
					), (c, i, m) -> generateRST(c, i, m)),
			new InstructionEntry(SLA.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x20}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x26}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SRA.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x28}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x2e}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SRL.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x38}, (r, v) -> r.setParameter(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x3e}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SUB.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x90}, (r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xd6, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1]))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x96, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(XOR.class, Arrays.asList(
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xa8}, (r, v) -> r.setParameter(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xee, 0x00}, (r, v) -> r.setParameter(reverseImmediate(v[1]))),
					new MaskedOpcode<OneParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xae, 0x00}, (r, v) -> r.setParameter(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, i, m) -> generateSUBANDXORORCP(c, i, m))
	});
	
	private static Map<Class<? extends OneParameterInstruction>, InstructionEntry> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e));
	
	private static Map<Integer, MaskedOpcodeMap<OneParameterInstruction>> reverse =
			instructionlist.stream().flatMap(e -> e.masks.stream()).collect(Collectors.groupingBy(
					m -> m.mask.length,
					Collectors.toMap(
							m -> new OpcodeMask<OneParameterInstruction>(m.mask, m.value, m.extractor),
							m -> m.instruction,
							(u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
							MaskedOpcodeMap<OneParameterInstruction>::new)));
	
	@Override
	protected MaskedOpcodeMap<OneParameterInstruction> getReverse(int index) {
		return reverse.get(index);
	}
	
	@Override
	public byte[] convert(CompilationUnit compilationUnit, OneParameterInstruction instruction) {
		byte[] result = null;
		InstructionEntry entry = instructions.get(instruction.getClass());
		if (entry != null && entry.generator != null) {
			result = entry.generator.generate(compilationUnit, instruction, entry.masks);
		}
		return result;
	}

	private static byte[] generateSUBANDXORORCP(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks.get(0).value[0] | registerindex)};
		} else if (parameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter) parameter).getExpressionValue(compilationUnit);
			result = new byte[] {masks.get(1).value[0], 0};
			result[1] = (byte)(value & 0xff);
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			result = generateIndexedAddressing(compilationUnit, (IndexedAddressingParameter) parameter, masks.get(2).value);
		}
		
		return result;
	}
	
	private static byte[] generateINCDEC(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterSSIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks.get(0).value[0] | (registerindex << 4))};
		} else if ((registerindex = getRegisterRHIndex(parameter)) != -1) {
			result = new byte[] {(byte) (masks.get(1).value[0] | (registerindex << 3))};
		} else if (parameter instanceof RegisterPairParameter) {
			result = generateIndexRegisters(compilationUnit, ((RegisterPairParameter) parameter).getRegisterPair(), masks.get(2).value);
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			result = generateIndexedAddressing(compilationUnit, (IndexedAddressingParameter) parameter, masks.get(3).value);
		}
		
		return result;
	}

	private static byte[] generateDJNZ(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		if (parameter instanceof ExpressionParameter) {
			ExpressionParameter expressionParameter = (ExpressionParameter) parameter;
			result = new byte[] {masks.get(0).value[0], (byte) (expressionParameter.getExpressionValue(compilationUnit) - 2)};
		}
		
		return result;
	}
	
	private static byte[] generatePUSHPOP(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterQQIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks.get(0).value[0] | (registerindex << 4))};
		} else if (parameter instanceof RegisterPairParameter) {
			result = generateIndexRegisters(compilationUnit, ((RegisterPairParameter) parameter).getRegisterPair(), masks.get(1).value);
		}
		
		return result;
	}
	
	private static byte[] generateBitrotating(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = Arrays.copyOf(masks.get(0).value, 2);
			result[1] |= registerindex;
		} else if (parameter instanceof IndexedAddressingParameter) {
			result = generateIndexedAddressing(compilationUnit, (IndexedAddressingParameter) parameter, masks.get(1).value);
		}
		
		return result;
	}

	private static byte[] generateRET(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		Parameter parameter = instruction.getParameter();
		if (parameter == null || parameter instanceof ConditionParameter) {
			result = parameter == null ?
					new byte[] {masks.get(0).value[0]} :
						new byte[] {(byte) (masks.get(1).value[0] | (((ConditionParameter)parameter).getCondition().getOpcode() << 3))};
		}
		
		return result;
	}
	
	private static byte[] generateRST(CompilationUnit compilationUnit, OneParameterInstruction instruction, List<MaskedOpcode<OneParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		if (parameter instanceof ConstantValueParameter) {
			ConstantValueParameter constantValeParameter = (ConstantValueParameter) parameter;
			int value = Integer.parseInt(constantValeParameter.getValue(), 16);
			if (value == 0x00 || value == 0x08 || value == 0x10 || value == 0x18 ||
					value == 0x20 || value == 0x28 || value == 0x30 || value == 0x38) {
				result = new byte[] {(byte) (masks.get(0).value[0] | value)};
			}
		}
		
		return result;
	}
	
}
