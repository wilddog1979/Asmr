package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Condition;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class JR extends TwoParameterInstruction {

	public JR() {
	}
	
	public JR(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "JR";
	}

	@Override
	public String getAssembly() {
		Parameter target = getTarget();
		return String.format(
				"%s%s %s",
				getMnemonic(),
				target == null ? "" : target.getAssembly(),
						getSource().getAssembly());
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		if (targetparameter != null && targetparameter instanceof ConditionParameter &&
				sourceparameter instanceof ExpressionParameter) {
			Condition condition = ((ConditionParameter)targetparameter).getCondition();
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit) - 2;
			if (condition == Condition.NZ || condition == Condition.Z ||
					condition == Condition.NC || condition == Condition.C) {
				result = new byte[] {(byte) (0x20 | (condition.getOpcode() << 3)), (byte) (value & 0xff)};
			}
		} else if (sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit) - 2;
			result = new byte[] {0x18, (byte) (value & 0xff)};
		}
		
		return result;
	}
}
