package org.eaSTars.z80asm.assembler.converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eaSTars.asm.assember.PushbackInputStream;
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
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;

public class OneParameterInstructionConverter extends Z80InstructionConverter<OneParameterInstruction> {

	private static class MaskedOpcode {
		private Class<? extends OneParameterInstruction> instruction;
		
		private byte[] mask;
		
		private byte[] value;
		
		public MaskedOpcode(byte[] mask, byte[] value) {
			this.mask = mask;
			this.value = value;
		}
		
	}
	
	private static class InstructionEntry {
		private Class<? extends OneParameterInstruction> instruction;
		
		private MaskedOpcode[] masks;
		
		private InstructionAssemblyGenerateor generator;
		
		public InstructionEntry(Class<? extends OneParameterInstruction> instruction, MaskedOpcode[] masks, InstructionAssemblyGenerateor generator) {
			this.instruction = instruction;
			this.masks = masks;
			for (MaskedOpcode m : masks) {
				m.instruction = instruction;
			}
			this.generator = generator;
		}
		
	}
	
	@FunctionalInterface
	private static interface InstructionAssemblyGenerateor {
		
		public byte[] generate(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks);
		
	}
	
	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(AND.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xa0}),
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xe6, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xa6, 0x00})
			}, (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(CP.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0xb8}),
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xfe, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xbe, 0x00})
			}, (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(DEC.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xcf}, new byte[] {0x0b}),
					new MaskedOpcode(new byte[] {(byte) 0xc7}, new byte[] {0x05}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, 0x2b}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, 0x35, 0x00})
			}, (c, i, m) -> generateINCDEC(c, i, m)),
			new InstructionEntry(DJNZ.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00},  new byte[] {0x10, 0x00})
			}, (c, i, m) -> generateDJNZ(c, i, m)) ,
			new InstructionEntry(INC.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xcf}, new byte[] {0x03}),
					new MaskedOpcode(new byte[] {(byte) 0xc7}, new byte[] {0x04}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, 0x23}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, 0x34, 0x00})
			}, (c, i, m) -> generateINCDEC(c, i, m)),
			new InstructionEntry(OR.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xb0}),
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xf6, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xb6, 0x00})
			}, (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(POP.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xcf}, new byte[] {(byte) 0xc1}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe1})
			}, (c, i, m) -> generatePUSHPOP(c, i, m)),
			new InstructionEntry(PUSH.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xcf}, new byte[] {(byte) 0xc5}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe5})
			}, (c, i, m) -> generatePUSHPOP(c, i, m)),
			new InstructionEntry(RET.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xc9}),
					new MaskedOpcode(new byte[] {(byte) 0xc7}, new byte[] {(byte) 0xc0})
			}, (c, i, m) -> generateRET(c, i, m)),
			new InstructionEntry(RL.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x10}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x16})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RLC.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x06})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RR.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x18}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x1e})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RRC.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x08}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x0e})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(RST.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xc7}, new byte[] {(byte) 0xc7})
			}, (c, i, m) -> generateRST(c, i, m)),
			new InstructionEntry(SLA.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x20}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x26})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SRA.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x28}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x2e})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SRL.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xff, (byte) 0xf8}, new byte[] {(byte) 0xca, 0x38}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xca, 0x00, 0x3e})
			}, (c, i, m) -> generateBitrotating(c, i, m)),
			new InstructionEntry(SUB.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x90}),
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xd6, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x96, 0x00})
			}, (c, i, m) -> generateSUBANDXORORCP(c, i, m)),
			new InstructionEntry(XOR.class, new MaskedOpcode[] {
					new MaskedOpcode(new byte[] {(byte) 0xf8}, new byte[] {(byte)0xa8}),
					new MaskedOpcode(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte)0xee, 0x00}),
					new MaskedOpcode(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0xae, 0x00})
			}, (c, i, m) -> generateSUBANDXORORCP(c, i, m))
	});
	
	private static Map<Class<? extends OneParameterInstruction>, InstructionEntry> instructions =
			instructionlist.stream().collect(Collectors.toMap(e -> e.instruction, e -> e));
	
	private static Map<Integer, MaskedOpcodeMap<Class<? extends OneParameterInstruction>>> reverse =
			instructionlist.stream().flatMap(e -> Arrays.asList(e.masks).stream()).collect(Collectors.groupingBy(
					m -> m.mask.length,
					Collectors.toMap(
							m -> new OpcodeMask(m.mask, m.value),
							m -> m.instruction,
							(u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
							MaskedOpcodeMap<Class<? extends OneParameterInstruction>>::new)));
	
	@Override
	public byte[] convert(CompilationUnit compilationUnit, OneParameterInstruction instruction) {
		byte[] result = null;
		InstructionEntry entry = instructions.get(instruction.getClass());
		if (entry != null && entry.generator != null) {
			result = entry.generator.generate(compilationUnit, instruction, entry.masks);
		}
		return result;
	}
	
	private OneParameterInstruction convertRecursive(PushbackInputStream pushbackInputStream, byte[] buffer)  throws IOException{
		OneParameterInstruction result = null;
		
		int current = pushbackInputStream.read();
		if (current != -1) {
			byte[] currentbuffer = Arrays.copyOf(buffer, buffer.length + 1);
			currentbuffer[buffer.length] = (byte) current;
			Class<? extends OneParameterInstruction> resultclass = reverse.get(currentbuffer.length).getInstruction(currentbuffer);
			if ((resultclass == null || (result = instanciate(resultclass)) == null) && (result = convertRecursive(pushbackInputStream, currentbuffer)) == null) {
				pushbackInputStream.unread((byte) current);
			}
		}
		
		return result;
	}
	
	@Override
	public OneParameterInstruction convert(PushbackInputStream pushbackInputStream) throws IOException {
		return convertRecursive(pushbackInputStream, new byte[] {});
	}

	private static byte[] generateSUBANDXORORCP(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks[0].value[0] | registerindex)};
		} else if (parameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter) parameter).getExpressionValue(compilationUnit);
			result = new byte[] {masks[1].value[0], 0};
			result[1] = (byte)(value & 0xff);
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerPair = indexedAddressingParameter.getRegisterPair();
			if (registerPair == RegisterPair.IX) {
				result = Arrays.copyOf(masks[2].value, 3);
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerPair == RegisterPair.IY) {
				result = Arrays.copyOf(masks[2].value, 3);
				result[0] |= 0x20;
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}
	
	private static byte[] generateINCDEC(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterSSIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks[0].value[0] | (registerindex << 4))};
		} else if ((registerindex = getRegisterRHIndex(parameter)) != -1) {
			result = new byte[] {(byte) (masks[1].value[0] | (registerindex << 3))};
		} else if (parameter instanceof RegisterPairParameter) {
			RegisterPair registerpair = ((RegisterPairParameter) parameter).getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = Arrays.copyOf(masks[2].value, 2);
			} else if (registerpair == RegisterPair.IY) {
				result = Arrays.copyOf(masks[2].value, 2);
				result[0] |= 0x20;
			}
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerpair = indexedAddressingParameter.getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = Arrays.copyOf(masks[3].value, 3);
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerpair == RegisterPair.IY) {
				result = Arrays.copyOf(masks[3].value, 3);
				result[0] |= 0x20;
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}

	private static byte[] generateDJNZ(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		if (parameter instanceof ExpressionParameter) {
			ExpressionParameter expressionParameter = (ExpressionParameter) parameter;
			result = new byte[] {masks[0].value[0], (byte) (expressionParameter.getExpressionValue(compilationUnit) - 2)};
		}
		
		return result;
	}
	
	private static byte[] generatePUSHPOP(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterQQIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (masks[0].value[0] | (registerindex << 4))};
		} else if (parameter instanceof RegisterPairParameter) {
			RegisterPair registerPair = ((RegisterPairParameter)parameter).getRegisterPair();
			if (registerPair == RegisterPair.IX) {
				result = Arrays.copyOf(masks[1].value, 2);
			} else if (registerPair == RegisterPair.IY) {
				result = Arrays.copyOf(masks[1].value, 2);
				result[0] |= 0x20;
			}
		}
		
		return result;
	}
	
	private static byte[] generateBitrotating(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = Arrays.copyOf(masks[0].value, 2);
			result[1] |= registerindex;
		} else if (parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerpair = indexedAddressParameter.getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = Arrays.copyOf(masks[1].value, 4);
				result[2] = (byte) indexedAddressParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerpair == RegisterPair.IY) {
				result = Arrays.copyOf(masks[1].value, 4);
				result[0] |= 0x20;
				result[2] = (byte) indexedAddressParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}

	private static byte[] generateRET(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		Parameter parameter = instruction.getParameter();
		if (parameter == null || parameter instanceof ConditionParameter) {
			result = parameter == null ?
					new byte[] {masks[0].value[0]} :
						new byte[] {(byte) (masks[1].value[0] | (((ConditionParameter)parameter).getCondition().getOpcode() << 3))};
		}
		
		return result;
	}
	
	private static byte[] generateRST(CompilationUnit compilationUnit, OneParameterInstruction instruction, MaskedOpcode[] masks) {
		byte[] result = null;
		
		Parameter parameter = instruction.getParameter();
		if (parameter instanceof ConstantValueParameter) {
			ConstantValueParameter constantValeParameter = (ConstantValueParameter) parameter;
			int value = Integer.parseInt(constantValeParameter.getValue(), 16);
			if (value == 0x00 || value == 0x08 || value == 0x10 || value == 0x18 ||
					value == 0x20 || value == 0x28 || value == 0x30 || value == 0x38) {
				result = new byte[] {(byte) (masks[0].value[0] | value)};
			}
		}
		
		return result;
	}
	
}
