package org.eaSTars.z80asm.assembler.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eaSTars.asm.assember.CompilationContext;
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
import org.eaSTars.z80asm.ast.parameter.ImmediateAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public class TwoParameterInstructionConverter extends AbstractZ80InstructionConverter<TwoParameterInstruction> {

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
		
		public byte[] generate(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks);
		
	}

	private static List<InstructionEntry> instructionlist = Arrays.asList(new InstructionEntry[]{
			new InstructionEntry(ADC.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x88}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xce, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x4a}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL)).setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x8e, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, t, s, m) -> generateADCSBC(c, t, s, m)),
			new InstructionEntry(ADD.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xcf}, new byte[] {0x09}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL)).setSource(reverseRegisterSS((v[0] >> 4) & 0x03))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x80}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xc6, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x86, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xcf}, new byte[] {(byte) 0xdd, 0x09}, (r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00)).setSource(reverseRegisterPPRR((v[0] & 0x20) == 0x00, (v[1] >> 4) & 0x03)))
					), (c, t, s, m) -> generateADD(c, t, s, m)),
			new InstructionEntry(BIT.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, 0x40}, (r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, 0x46}, (r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, t, s, m) -> generateBITRESSET(c, t, s, m)),
			new InstructionEntry(CALL.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc7, 0x00, 0x00}, new byte[] {(byte) 0xc4, 0x00, 0x00}, (r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x07)).setSource(reverseImmediate16(v[2], v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xcd, 0x00, 0x00}, (r, v) -> r.setSource(reverseImmediate16(v[2], v[1])))
					), (c, t, s, m) -> generateCALL(c, t, s, m)),
			new InstructionEntry(EX.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {0x08}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.AF)).setSource(new RegisterPairParameter(RegisterPair.AFMarked))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xeb}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.DE)).setSource(new RegisterPairParameter(RegisterPair.HL))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xe3}, (r, v) -> r.setTarget(new RegisterIndirectAddressing(RegisterPair.SP)).setSource(new RegisterPairParameter(RegisterPair.HL))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe3}, (r, v) -> r.setTarget(new RegisterIndirectAddressing(RegisterPair.SP)).setSource(reverseIXIY((v[0] & 0x20) == 0x00)))
					), (c, t, s, m) -> generateEX(t, s, m)),
			new InstructionEntry(IN.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xdb, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xed, 0x40}, (r, v) -> r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07)).setSource(new RegisterParameter(Register.C)))
					), (c, t, s, m) -> generateIN(c, t, s, m)),
			new InstructionEntry(JP.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xe9}, (r, v) -> r.setSource(new RegisterIndirectAddressing(RegisterPair.HL))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xe9}, (r, v) -> r.setSource(new RegisterIndirectAddressing((v[0] & 0x20) == 0x00 ? RegisterPair.IX : RegisterPair.IY))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc7, 0x00, 0x00}, new byte[] {(byte) 0xc2, 0x00, 0x00}, (r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x07)).setSource(reverseImmediate16(v[2], v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xc3, 0x00, 0x00}, (r, v) -> r.setSource(reverseImmediate16(v[2], v[1])))
					), (c, t, s, m) -> generateJP(c, t, s, m)),
			new InstructionEntry(JR.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xe7, 0x00}, new byte[] {0x20, 0x00}, (r, v) -> r.setTarget(reverseCondition((v[0] >> 3) & 0x03)).setSource(reverseImmediate8(v[1] + 2))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {0x18, 0x00}, (r, v) -> r.setSource(reverseImmediate8(v[1] + 2)))
					), (c, t, s, m) -> generateJR(c, t, s, m)),
			new InstructionEntry(LD.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {0x02}, (r, v) -> r.setTarget(new RegisterIndirectAddressing(RegisterPair.BC)).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {0x12}, (r, v) -> r.setTarget(new RegisterIndirectAddressing(RegisterPair.DE)).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {0x0a}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterIndirectAddressing(RegisterPair.BC))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {0x1a}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterIndirectAddressing(RegisterPair.DE))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc0}, new byte[] {0x40}, (r, v) -> {
						if ((v[0] & 0x07) == 0x06) {
							return r.setTarget(reverseRegisterR((v[0] >> 3) & 0x07)).setSource(new RegisterIndirectAddressing(RegisterPair.HL));
						} else if ((v[0] & 0x38) == 0x30) {
							return r.setTarget(new RegisterIndirectAddressing(RegisterPair.HL)).setSource(reverseRegisterR(v[0] & 0x07));
						} else {
							return r.setTarget(reverseRegisterR((v[0] >> 3) & 0x07)).setSource(reverseRegisterRMarked(v[0] & 0x07));
						}
					}),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xf9}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.SP)).setSource(new RegisterPairParameter(RegisterPair.HL))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xc7, 0x00}, new byte[] {0x06, 0x00}, (r, v) -> r.setTarget(reverseRegisterRH((v[0] >> 3) & 0x07)).setSource(reverseImmediate8(v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff}, new byte[] {(byte) 0xdd, (byte) 0xf9}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.SP)).setSource(reverseIXIY((v[0] & 0x20) == 0x00))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x47}, (r, v) -> r.setTarget(new RegisterParameter(Register.I)).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x57}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterParameter(Register.I))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x4f}, (r, v) -> r.setTarget(new RegisterParameter(Register.R)).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xff}, new byte[] {(byte) 0xed, (byte) 0x5f}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(new RegisterParameter(Register.R))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xcf, 0x00, 0x00}, new byte[] {0x01, 0x00, 0x00}, (r, v) -> r.setTarget(reverseRegisterSS((v[0] >> 4) & 0x03)).setSource(reverseImmediate16(v[2], v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x22, 0x00, 0x00}, (r, v) -> r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1]))).setSource(new RegisterPairParameter(RegisterPair.HL))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x2a, 0x00, 0x00}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL)).setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x32, 0x00, 0x00}, (r, v) -> r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1]))).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00, 0x00}, new byte[] {0x3a, 0x00, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[2], v[1])))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xf8, 0x00}, new byte[] {(byte) 0xdd, 0x70, 0x00}, (r, v) -> r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])).setSource(reverseRegisterR(v[1] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xc7, 0x00}, new byte[] {(byte) 0xdd, 0x46, 0x00}, (r, v) -> r.setTarget(reverseRegisterR((v[1] >> 3) & 0x07)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x34, 0x00, 0x00}, (r, v) -> r.setTarget(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])).setSource(reverseImmediate8(v[3]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00}, new byte[] {(byte) 0xed, (byte) 0x43, 0x00, 0x00}, (r, v) -> r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2]))).setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf, 0x00, 0x00}, new byte[] {(byte) 0xed, (byte) 0x4b, 0x00, 0x00}, (r, v) -> r.setTarget(reverseRegisterSS((v[1] >> 4) & 0x03)).setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2])))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x21, 0x00, 0x00}, (r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00)).setSource(reverseImmediate16(v[3], v[2]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x22, 0x00, 0x00}, (r, v) -> r.setTarget(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2]))).setSource(reverseIXIY((v[0] & 0x20) == 0x00))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x2a, 0x00, 0x00}, (r, v) -> r.setTarget(reverseIXIY((v[0] & 0x20) == 0x00)).setSource(new ImmediateAddressingParameter((ExpressionParameter) reverseImmediate16(v[3], v[2]))))
					), (c, t, s, m) -> generateLD(c, t, s, m)),
			new InstructionEntry(OUT.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xd3, 0x00}, (r, v) -> r.setTarget(reverseImmediate8(v[1])).setSource(new RegisterParameter(Register.A))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc7}, new byte[] {(byte) 0xed, 0x41}, (r, v) -> r.setTarget(new RegisterParameter(Register.C)).setSource(reverseRegisterR((v[1] >> 3) & 0x07)))
					), (c, t, s, m) -> generateOUT(c, t, s, m)),
			new InstructionEntry(RES.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, (byte) 0x80}, (r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0x86}, (r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, t, s, m) -> generateBITRESSET(c, t, s, m)),
			new InstructionEntry(SBC.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xf8}, new byte[] {(byte) 0x98}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseRegisterRH(v[0] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, 0x00}, new byte[] {(byte) 0xde, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseImmediate8(v[1]))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xcf}, new byte[] {(byte) 0xed, 0x42}, (r, v) -> r.setTarget(new RegisterPairParameter(RegisterPair.HL)).setSource(reverseRegisterSS((v[1] >> 4) & 0x03))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00}, new byte[] {(byte) 0xdd, (byte) 0x9e, 0x00}, (r, v) -> r.setTarget(new RegisterParameter(Register.A)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, t, s, m) -> generateADCSBC(c, t, s, m)),
			new InstructionEntry(SET.class, Arrays.asList(
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xff, (byte) 0xc0}, new byte[] {(byte) 0xcb, (byte) 0xc0}, (r, v) -> r.setTarget(reverseImmediate8((v[1] >> 3) & 0x07)).setSource(reverseRegisterRH(v[1] & 0x07))),
					new MaskedOpcode<TwoParameterInstruction>(new byte[] {(byte) 0xdf, (byte) 0xff, 0x00, (byte) 0xc7}, new byte[] {(byte) 0xdd, (byte) 0xcb, 0x00, (byte) 0xc6}, (r, v) -> r.setTarget(reverseImmediate8((v[3] >> 3) & 0x07)).setSource(reverseIndexedAddressing((v[0] & 0x20) == 0x00, v[2])))
					), (c, t, s, m) -> generateBITRESSET(c, t, s, m))
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
	public byte[] convert(CompilationContext compilationContext, TwoParameterInstruction instruction) {
		byte[] result = null;
		InstructionEntry entry = instructions.get(instruction.getClass());
		if (entry != null && entry.generator != null) {
			result = entry.generator.generate(compilationContext, instruction.getTarget(), instruction.getSource(), entry.masks);
		}
		return result;
	}

	private static byte[] generateADCSBC(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			int sourceindex = getRegisterRHIndex(sourceparameter);
			if (sourceindex != -1) {
				result = selectMask(masks.get(0));
				result[0] |= sourceindex & 0x07;
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = selectMask(masks.get(1));
				result[1] = (byte) ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext);
			} else if (sourceparameter instanceof IndexedAddressingParameter) {
				result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(3).value);
			}
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL) {
			int sourceindex = getRegisterSSIndex(sourceparameter);
			if (sourceindex != -1) {
				result = selectMask(masks.get(2));
				result[1] |= (byte)(sourceindex << 4);
			}
		}
		
		return result;
	}
	
	private static byte[] generateBITRESSET(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (targetparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)targetparameter).getExpressionValue(compilationContext);
			if (value >= 0 && value <= 7) {
				int sourceindex = getRegisterRHIndex(sourceparameter);
				if (sourceindex != -1) {
					result = selectMask(masks.get(0));
					result[1] |= (byte)((value << 3) | sourceindex);
				} else if (sourceparameter instanceof IndexedAddressingParameter) {
					result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(1).value);
					if (result != null) {
						result[3] |= (byte)(value << 3);
					}
				}
			}
		}
		
		return result;
	}
	
	private static byte[] generateCALL(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (sourceparameter instanceof ExpressionParameter) {
			if (targetparameter != null && targetparameter instanceof ConditionParameter) {
				result = generateWithExpressionValue(compilationContext, selectMask(masks.get(0)), (ExpressionParameter)sourceparameter, 1);
				Condition condition = ((ConditionParameter)targetparameter).getCondition();
				result[0] |= (condition.getOpcode() << 3);
			} else if (targetparameter == null) {
				result = generateWithExpressionValue(compilationContext, selectMask(masks.get(1)), (ExpressionParameter)sourceparameter, 1);
			}
		}
		
		return result;
	}
	
	private static byte[] generateJP(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (targetparameter == null) {
			if (sourceparameter instanceof RegisterIndirectAddressing) {
				RegisterIndirectAddressing registerIndirectAddressing = (RegisterIndirectAddressing) sourceparameter;
				RegisterPair registerPair = registerIndirectAddressing.getRegisterPair();
				if (registerPair == RegisterPair.HL) {
					result = selectMask(masks.get(0));
				} else {
					result = generateIndexRegisters(registerPair, masks.get(1).value);
				}
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = generateWithExpressionValue(compilationContext, selectMask(masks.get(3)), (ExpressionParameter)sourceparameter, 1);
			}
		} else if (targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			result = generateWithExpressionValue(compilationContext, selectMask(masks.get(2)), (ExpressionParameter)sourceparameter, 1);
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			result[0] |= (condition.getOpcode() << 3);
		}
		
		return result;
	}
	
	private static byte[] generateJR(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (targetparameter != null && targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext) - 2;
			if (condition == Condition.NZ || condition == Condition.Z ||
					condition == Condition.NC || condition == Condition.C) {
				result = selectMask(masks.get(0));
				result[0] |= (condition.getOpcode() << 3);
				result[1] = (byte) (value & 0xff);
			}
		} else if (sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext) - 2;
			result = selectMask(masks.get(1));
			result[1] = (byte) (value & 0xff);
		}
		
		return result;
	}
	
	private static byte[] generateIN(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		int targetindex = -1;
		if (sourceparameter instanceof ExpressionParameter &&
				targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(0));
			result[1] = (byte) ((ExpressionParameter) sourceparameter).getExpressionValue(compilationContext);
		} else if (sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.C &&
				(targetindex = getRegisterRIndex(targetparameter)) != -1) {
			result = selectMask(masks.get(1));
			result[1] |=  (targetindex << 3) & 0x38;
		}
		
		return result;
	}
	
	private static byte[] generateOUT(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		int sourceindex = -1;
		if (targetparameter instanceof ExpressionParameter &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(0));
			result[1] = (byte) ((ExpressionParameter) targetparameter).getExpressionValue(compilationContext);
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.C &&
				(sourceindex = getRegisterRIndex(sourceparameter)) != -1) {
			result = selectMask(masks.get(1));
			result[1] |=  (sourceindex << 3) & 0x38;
		}

		return result;
	}
	
	private static byte[] generateEX(Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.AF &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.AFMarked) {
			result = selectMask(masks.get(0));
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.DE &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = selectMask(masks.get(1));
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.SP) {
			if (sourceparameter instanceof RegisterPairParameter) {
				RegisterPair registerPair = ((RegisterPairParameter) sourceparameter).getRegisterPair();
				if (registerPair == RegisterPair.HL) {
					result = selectMask(masks.get(2));
				} else {
					result = generateIndexRegisters(registerPair, masks.get(3).value);
				}
			}
		}
		
		return result;
	}
	
	private static byte[] generateADD(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		int sourceindex = getRegisterSSIndex(sourceparameter);
		if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL &&
				sourceindex != -1) {
			result = selectMask(masks.get(0));
			result[0] |= (sourceindex << 4) & 0x30;
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			sourceindex = getRegisterRHIndex(sourceparameter);
			if (sourceindex != -1) {
				result = selectMask(masks.get(1));
				result[0] |= sourceindex & 0x07;
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = selectMask(masks.get(2));
				result[1] |= (byte) ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext);
			} else if (sourceparameter instanceof IndexedAddressingParameter) {
				result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(3).value);
			}
		}
		if (result == null && targetparameter instanceof RegisterPairParameter) {
			result = generateIndexRegisters(((RegisterPairParameter)targetparameter).getRegisterPair(), masks.get(4).value);
			if (result != null && ((sourceindex = getRegisterPPIndex(sourceparameter)) != -1 || (result != null && (sourceindex = getRegisterRRIndex(sourceparameter)) != -1))) {
				result[1] |= (sourceindex << 4) & 0x30;
			}
		}
		
		return result;
	}
	
	private static byte[] generateLD(CompilationContext compilationContext, Parameter targetparameter, Parameter sourceparameter, List<MaskedOpcode<TwoParameterInstruction>> masks) {
		byte[] result = null;
		
		int registerindex = -1;
		int markedregisterindex = -1;
		if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.BC &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(0));
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.DE &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(1));
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.BC) {
			result = selectMask(masks.get(2));
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.DE) {
			result = selectMask(masks.get(3));
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.HL &&
				(registerindex = getRegisterRIndex(sourceparameter)) != -1) {
			result = selectMask(masks.get(4));
			result[0] |= 0x30 | (registerindex & 0x07);
		} else if ((registerindex = getRegisterRIndex(targetparameter)) != -1 &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = selectMask(masks.get(4));
			result[0] |= ((registerindex << 3) & 0x38) | 0x06;
		} else if ((registerindex = getRegisterRIndex(targetparameter)) != -1 &&
				(markedregisterindex = getMarkedRegisterRIndex(sourceparameter)) != -1) {
			result = selectMask(masks.get(4));
			result [0] |= ((registerindex << 3) & 0x38) | (markedregisterindex & 0x07);
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.SP &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = selectMask(masks.get(5));
		} else if ((registerindex = getRegisterRHIndex(targetparameter)) != -1 &&
				sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext);
			result = selectMask(masks.get(6));
			result[0] |= (registerindex << 3) & 0x38;
			result[1] = (byte) (value & 0xff);
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.SP &&
				sourceparameter instanceof RegisterPairParameter &&
				(((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IX || ((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IY)) {
			result = generateIndexRegisters(((RegisterPairParameter)sourceparameter).getRegisterPair(), masks.get(7).value);
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.I &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(8));
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.I) {
			result = selectMask(masks.get(9));
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.R &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = selectMask(masks.get(10));
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.R) {
			result = selectMask(masks.get(11));
		} else if ((registerindex = getRegisterSSIndex(targetparameter)) != -1 &&
				sourceparameter instanceof ExpressionParameter) {
			result = generateWithExpressionValue(compilationContext, selectMask(masks.get(12)), (ExpressionParameter)sourceparameter, 1);
			result[0] |= (registerindex << 4) & 0x30;
		} else if (targetparameter instanceof ImmediateAddressingParameter &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = generateWithImmediateValue(compilationContext, selectMask(masks.get(13)), targetparameter, 1);
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL &&
				sourceparameter instanceof ImmediateAddressingParameter) {
			result = generateWithImmediateValue(compilationContext, selectMask(masks.get(14)), sourceparameter, 1);
		} else if (targetparameter instanceof ImmediateAddressingParameter &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = generateWithImmediateValue(compilationContext, selectMask(masks.get(15)), targetparameter, 1);
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof ImmediateAddressingParameter) {
			result = generateWithImmediateValue(compilationContext, selectMask(masks.get(16)), sourceparameter, 1);
		} else if (targetparameter instanceof IndexedAddressingParameter &&
				(registerindex = getRegisterRIndex(sourceparameter)) != -1) {
			result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) targetparameter, masks.get(17).value);
			if (result != null) {
				result[1] |= registerindex & 0x07;
			}
		}
		
		if (result == null) {
			if (sourceparameter instanceof IndexedAddressingParameter &&
					(registerindex = getRegisterRIndex(targetparameter)) != -1) {
				result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) sourceparameter, masks.get(18).value);
				if (result != null) {
					result[1] |= (registerindex << 3) & 0x38;
				}
			}
		}
		
		if (result == null) {
			if (targetparameter instanceof IndexedAddressingParameter &&
					sourceparameter instanceof ExpressionParameter) {
				result = generateIndexedAddressing(compilationContext, (IndexedAddressingParameter) targetparameter, masks.get(19).value);
				if (result != null) {
					int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationContext);
					result[3] = (byte) (value & 0xff);
				}
			}
		}
		
		if (result == null) {
			if (targetparameter instanceof ImmediateAddressingParameter &&
					(registerindex = getRegisterSSIndex(sourceparameter)) != -1) {
				result = generateWithImmediateValue(compilationContext, selectMask(masks.get(20)), targetparameter, 2);
				result[1] |= (registerindex << 4) & 0x38;
			} else if (sourceparameter instanceof ImmediateAddressingParameter &&
					(registerindex = getRegisterSSIndex(targetparameter)) != -1) {
				result = generateWithImmediateValue(compilationContext, selectMask(masks.get(21)), sourceparameter, 2);
				result[1] |= (registerindex << 4) & 0x38;
			} else if (targetparameter instanceof RegisterPairParameter &&
					(((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IX || ((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IY) &&
					sourceparameter instanceof ExpressionParameter) {
				result = generateWithExpressionValue(compilationContext, generateIndexRegisters(((RegisterPairParameter)targetparameter).getRegisterPair(), masks.get(22).value), (ExpressionParameter)sourceparameter, 2);
			} else if (targetparameter instanceof ImmediateAddressingParameter &&
					sourceparameter instanceof RegisterPairParameter &&
					(((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IX || ((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IY)) {
				result = generateWithImmediateValue(compilationContext, generateIndexRegisters(((RegisterPairParameter)sourceparameter).getRegisterPair(), masks.get(23).value), targetparameter, 2);
			} else if (sourceparameter instanceof ImmediateAddressingParameter &&
					targetparameter instanceof RegisterPairParameter &&
					(((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IX || ((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IY)) {
				result = generateWithImmediateValue(compilationContext, generateIndexRegisters(((RegisterPairParameter)targetparameter).getRegisterPair(), masks.get(24).value), sourceparameter, 2);
			}
		}
		
		return result;
	}
	
	private static byte[] generateWithImmediateValue(CompilationContext compilationContext, byte[] result, Parameter parameter, int idx) {
		return generateWithExpressionValue(compilationContext, result, ((ImmediateAddressingParameter)parameter).getValue(), idx);
	}

	private static byte[] generateWithExpressionValue(CompilationContext compilationContext, byte[] result, ExpressionParameter parameter, int idx) {
		int value = parameter.getExpressionValue(compilationContext);
		result[idx] = (byte) (value & 0xff);
		result[idx+1] = (byte) ((value >> 8) & 0xff);
		return result;
	}
	
}
