package org.eaSTars.z80asm.assembler.converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eaSTars.asm.assember.AssemblyConverter;
import org.eaSTars.asm.assember.PushbackInputStream;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.z80asm.ast.expression.ConstantValueExpression;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
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
	
	protected static Parameter reverseRegisterSS(int index) {
		Parameter result = null;
		
		if (index >= 0 && index < 4) {
			result = new RegisterPairParameter(TABLE_SS.get(index));
		}
		
		return result;
	}
	
	protected static int getRegisterQQIndex(Parameter parameter) {
		return tableLookup(TABLE_QQ, parameter);
	}
	
	protected static Parameter reverseRegisterQQ(int index) {
		Parameter result = null;
		
		if (index >= 0 && index < 4) {
			result = new RegisterPairParameter(TABLE_QQ.get(index));
		}
		
		return result;
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
	
	protected static Parameter reverseRegisterRH(int index) {
		Parameter result = null;
		
		if (index == 6) {
			result = new RegisterIndirectAddressing(RegisterPair.HL);
		} else if (index >= 0 && index < 8) {
			result = new RegisterParameter(TABLE_R.get(index));
		}
		
		return result;
	}
	
	protected static Parameter reverseIndexedAddressing(boolean ix, int displacement) {
		return new IndexedAddressingParameter(ix ? RegisterPair.IX : RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(displacement)), 8));
	}
	
	protected static Parameter reverseImmediate(int value) {
		return new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(value)), 8);
	}
	
	protected static Parameter reverseCondition(int index) {
		Parameter result = null;
		
		for (Condition condition : Condition.values()) {
			if (condition.getOpcode() == index) {
				result = new ConditionParameter(condition);
				break;
			}
		}
		
		return result;
	}
	
	protected static Parameter reverseRSTValue(int value) {
		return new ConstantValueParameter(String.format("%02x", value));
	}
	
	protected static Parameter reverseIXIY(boolean ix) {
		return new RegisterPairParameter(ix ? RegisterPair.IX : RegisterPair.IY);
	}
	
	protected static byte[] generateIndexedAddressing(CompilationUnit compilationUnit, IndexedAddressingParameter indexedAddressingParameter, byte[] bytes) {
		byte[] result = null;
		
		RegisterPair registerPair = indexedAddressingParameter.getRegisterPair();
		if (registerPair == RegisterPair.IX) {
			result = Arrays.copyOf(bytes, bytes.length);
			result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
		} else if (registerPair == RegisterPair.IY) {
			result = Arrays.copyOf(bytes, bytes.length);
			result[0] |= 0x20;
			result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
		}
		
		return result;
	}

	protected static byte[] generateIndexRegisters(CompilationUnit compilationUnit, RegisterPair parameter, byte[] bytes) {
		byte[] result = null;
		
		if (parameter == RegisterPair.IX) {
			result = Arrays.copyOf(bytes, bytes.length);
		} else if (parameter == RegisterPair.IY) {
			result = Arrays.copyOf(bytes, bytes.length);
			result[0] |= 0x20;
		}
		
		return result;
	}

	protected abstract MaskedOpcodeMap<T> getReverse(int index);
	
	protected T convertRecursive(PushbackInputStream pushbackInputStream, byte[] buffer)  throws IOException{
		T result = null;
		
		int current = pushbackInputStream.read();
		if (current != -1) {
			byte[] currentbuffer = Arrays.copyOf(buffer, buffer.length + 1);
			currentbuffer[buffer.length] = (byte) current;
			result = Optional.ofNullable(getReverse(currentbuffer.length)).map(r -> r.getInstruction(currentbuffer)).orElseGet(() -> null);
			if (result == null && (result = convertRecursive(pushbackInputStream, currentbuffer)) == null) {
				pushbackInputStream.unread((byte) current);
			}
		}
		
		return result;
	}
	
	@Override
	public T convert(PushbackInputStream pushbackInputStream) throws IOException {
		return convertRecursive(pushbackInputStream, new byte[] {});
	}
	
}
