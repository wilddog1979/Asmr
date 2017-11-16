package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class CALL extends TwoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "CALL";
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
		
		if (sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			if (targetparameter != null && targetparameter instanceof ConditionParameter) {
				Condition condition = ((ConditionParameter)targetparameter).getCondition();
				result = new byte[] {(byte) (0xc4 | (condition.getOpcode() << 3)), (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (targetparameter == null) {
				result = new byte[] {(byte) 0xcd, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			}
		}
		
		return result;
	}

}
