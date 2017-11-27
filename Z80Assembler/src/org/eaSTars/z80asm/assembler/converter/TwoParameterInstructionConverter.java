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
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

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
			new InstructionEntry(ADC.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x88}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xce, 0x00}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x4a}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x8e, 0x00}, (r, v) -> null)
					), (c, i, m) -> generateADCSBC(c, i, m)),
			new InstructionEntry(ADD.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(BIT.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xcb, 0x40}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, 0x46}, (r, v) -> null)
					), (c, i, m) -> generateBITRESSET(c, i, m)),
			new InstructionEntry(CALL.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc7, 0x00, 0x00}, new byte[] {(byte) 0xc4, 0x00, 0x00}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xcd, 0x00, 0x00}, (r, v) -> null)
					), (c, i, m) -> generateCALL(c, i, m)),
			new InstructionEntry(EX.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(IN.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(JP.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xe9}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe9}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc8, 0x00, 0x00}, new byte[] {(byte) 0xc2, 0x00, 0x00}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xc3, 0x00, 0x00}, (r, v) -> null)
					), (c, i, m) -> generateJP(c, i, m)),
			new InstructionEntry(JR.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xe7, 0x00}, new byte[] {0x20, 0x00}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {0x18, 0x00}, (r, v) -> null)
					), (c, i, m) -> generateJR(c, i, m)),
			new InstructionEntry(LD.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(OUT.class, Arrays.asList(), (c, i, m) -> null),
			new InstructionEntry(RES.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xcb, (byte) 0x80}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0x86}, (r, v) -> null)
					), (c, i, m) -> generateBITRESSET(c, i, m)),
			new InstructionEntry(SBC.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x98}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xde, 0x00}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x42}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x9e, 0x00}, (r, v) -> null)
					), (c, i, m) -> generateADCSBC(c, i, m)),
			new InstructionEntry(SET.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xcb, (byte) 0xc0}, (r, v) -> null),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0xc6}, (r, v) -> null)
					), (c, i, m) -> generateBITRESSET(c, i, m))
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

	private static byte[] generateADCSBC(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter targetparameter = instruction.getTarget();
		Parameter sourceparameter = instruction.getSource();
		
		if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			int sourceindex = getRegisterRHIndex(sourceparameter);
			if (sourceindex != -1) {
				result = new byte[] {(byte) (masks.get(0).value[0] | sourceindex)};
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = new byte[] {masks.get(1).value[0], (byte) ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit)};
			} else if (sourceparameter instanceof IndexedAddressingParameter) {
				result = generateIndexedAddressing(compilationUnit, (IndexedAddressingParameter) sourceparameter, masks.get(3).value);
			}
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL) {
			int sourceindex = getRegisterSSIndex(sourceparameter);
			if (sourceindex != -1) {
				result = Arrays.copyOf(masks.get(2).value, 2);
				result[1] |= (byte)(sourceindex << 4);
			}
		}
		
		return result;
	}
	
	private static byte[] generateBITRESSET(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter targetparameter = instruction.getTarget();
		Parameter sourceparameter = instruction.getSource();
		
		if (targetparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)targetparameter).getExpressionValue(compilationUnit);
			if (value >= 0 && value <= 7) {
				int sourceindex = getRegisterRHIndex(sourceparameter);
				if (sourceindex != -1) {
					result = Arrays.copyOf(masks.get(0).value, masks.get(0).value.length);
					result[1] |= (byte)((value << 3) | sourceindex);
				} else if (sourceparameter instanceof IndexedAddressingParameter) {
					result = generateIndexedAddressing(compilationUnit, (IndexedAddressingParameter) sourceparameter, masks.get(1).value);
					if (result != null) {
						result[3] |= (byte)(value << 3);
					}
				}
			}
		}
		
		return result;
	}
	
	private static byte[] generateCALL(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter targetparameter = instruction.getTarget();
		Parameter sourceparameter = instruction.getSource();
		
		if (sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			if (targetparameter != null && targetparameter instanceof ConditionParameter) {
				Condition condition = ((ConditionParameter)targetparameter).getCondition();
				result = Arrays.copyOf(masks.get(0).value, masks.get(0).value.length);
				result[0] |= (condition.getOpcode() << 3);
				result[1] = (byte) (value & 0xff);
				result[2] = (byte) ((value >> 8) & 0xff);
			} else if (targetparameter == null) {
				result = Arrays.copyOf(masks.get(1).value, masks.get(1).value.length);
				result[1] = (byte) (value & 0xff);
				result[2] = (byte) ((value >> 8) & 0xff);
			}
		}
		
		return result;
	}
	
	private static byte[] generateJP(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks) {
byte[] result = null;
		
		Parameter targetparameter = instruction.getTarget();
		Parameter sourceparameter = instruction.getSource();
		
		if (targetparameter == null) {
			if (sourceparameter instanceof RegisterIndirectAddressing) {
				RegisterIndirectAddressing registerIndirectAddressing = (RegisterIndirectAddressing) sourceparameter;
				RegisterPair registerPair = registerIndirectAddressing.getRegisterPair();
				if (registerPair == RegisterPair.HL) {
					result = Arrays.copyOf(masks.get(0).value, masks.get(0).value.length);
				} else {
					result = generateIndexRegisters(compilationUnit, registerPair, masks.get(1).value);
				}
			} else if (sourceparameter instanceof ExpressionParameter) {
				int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
				result = Arrays.copyOf(masks.get(3).value, masks.get(3).value.length);
				result[1] = (byte) (value & 0xff);
				result[2] = (byte) ((value >> 8) & 0xff);
			}
		} else if (targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			result = Arrays.copyOf(masks.get(2).value, masks.get(2).value.length);
			result[0] |= (condition.getOpcode() << 3);
			result[1] = (byte) (value & 0xff);
			result[2] = (byte) ((value >> 8) & 0xff);
		}
		
		return result;
	}
	
	private static byte[] generateJR(CompilationUnit compilationUnit, TwoParameterInstruction instruction, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		Parameter targetparameter = instruction.getTarget();
		Parameter sourceparameter = instruction.getSource();
		
		if (targetparameter != null && targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit) - 2;
			if (condition == Condition.NZ || condition == Condition.Z ||
					condition == Condition.NC || condition == Condition.C) {
				result = Arrays.copyOf(masks.get(0).value, masks.get(0).value.length);
				result[0] |= (condition.getOpcode() << 3);
				result[1] = (byte) (value & 0xff);
			}
		} else if (sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit) - 2;
			result = Arrays.copyOf(masks.get(1).value, masks.get(1).value.length);
			result[1] = (byte) (value & 0xff);
		}
		
		return result;
	}

}
