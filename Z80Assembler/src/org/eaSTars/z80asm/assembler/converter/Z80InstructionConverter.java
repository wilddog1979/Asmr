package org.eaSTars.z80asm.assembler.converter;

import java.util.Arrays;
import java.util.List;

import org.eaSTars.asm.assember.AssemblyConverter;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public abstract class Z80InstructionConverter<T extends Instruction> extends AssemblyConverter<T> {

	private static final List<RegisterPair> TABLE_SS = Arrays.asList(
			RegisterPair.BC,
			RegisterPair.DE,
			RegisterPair.HL,
			RegisterPair.SP);
	
	private static final List<RegisterPair> TABLE_QQ = Arrays.asList(
			RegisterPair.BC,
			RegisterPair.DE,
			RegisterPair.HL,
			RegisterPair.AF);
	
	private static final List<Register> TABLE_R = Arrays.asList(
			Register.B,
			Register.C,
			Register.D,
			Register.E,
			Register.H,
			Register.L,
			null,
			Register.A);
	
	protected <T2 extends Instruction> T2 instanciate(Class<T2> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	private static int tableLookup(List<RegisterPair> map, Parameter parameter) {
		int result = -1;
		
		if (parameter instanceof RegisterPairParameter) {
			RegisterPairParameter registerPairParameter = (RegisterPairParameter) parameter;
			result = map.indexOf(registerPairParameter.getRegisterPair());
		}
		
		return result;
	}
	
	protected static int getRegisterSSIndex(Parameter parameter) {
		return tableLookup(TABLE_SS, parameter);
	}
	
	protected static int getRegisterQQIndex(Parameter parameter) {
		return tableLookup(TABLE_QQ, parameter);
	}
	
	protected static int getRegisterRIndex(Parameter parameter) {
		int result = -1;
		
		if (parameter instanceof RegisterParameter) {
			RegisterParameter registerParameter = (RegisterParameter) parameter;
			result = TABLE_R.indexOf(registerParameter.getRegister());
		}
		
		return result;
	}
	
	protected static int getRegisterRHIndex(Parameter parameter) {
		int result = getRegisterRIndex(parameter);
		
		if (result == -1 &&
				parameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)parameter).getRegisterPair() == RegisterPair.HL) {
			result = 6;
		}
		
		return result;
	}
	
}
