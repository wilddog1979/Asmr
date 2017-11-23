package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public class OUT extends TwoParameterInstruction {

	public OUT() {
	}
	
	public OUT(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "OUT";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		int sourceindex = -1;
		if (targetparameter instanceof ExpressionParameter &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = new byte[] {(byte) 0xd3, (byte) ((ExpressionParameter) targetparameter).getExpressionValue(compilationUnit)};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.C &&
				(sourceindex = getRegisterRIndex(sourceparameter)) != -1) {
			result = new byte[] {(byte) 0xed, (byte) (0x41 | (sourceindex << 3))};
		}

		return result;
	}

}
