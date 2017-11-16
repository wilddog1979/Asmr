package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;

public class JP extends TwoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "JP";
	}
	
	@Override
	public String getAssembly() {
		Parameter target = getTarget();
		return String.format("%s%s %s", getMnemonic(), target != null ? target.getAssembly() : "", getSource().getAssembly());
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		if (targetparameter == null) {
			if (sourceparameter instanceof RegisterIndirectAddressing) {
				RegisterIndirectAddressing registerIndirectAddressing = (RegisterIndirectAddressing) sourceparameter;
				RegisterPair registerPair = registerIndirectAddressing.getRegisterPair();
				if (registerPair == RegisterPair.HL) {
					result = new byte[] {(byte) 0xe9};
				} else if (registerPair == RegisterPair.IX) {
					result = new byte[] {(byte) 0xdd, (byte) 0xe9};
				} else if (registerPair == RegisterPair.IY) {
					result = new byte[] {(byte) 0xfd, (byte) 0xe9};
				}
			} else if (sourceparameter instanceof ExpressionParameter) {
				int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xc3, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			}
		} else if (targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			result = new byte[] {(byte) (0xc2 | (condition.getOpcode() << 3)), (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		}
		
		return result;
	}

}
