package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public class IN extends TwoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "IN";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();

		int targetindex = -1;
		if (sourceparameter instanceof ExpressionParameter &&
				targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			result = new byte[] {(byte) 0xdb, (byte) ((ExpressionParameter) sourceparameter).getExpressionValue(compilationUnit)};
		} else if (sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.C &&
				(targetindex = getRegisterRIndex(targetparameter)) != -1) {
			result = new byte[] {(byte) 0xed, (byte) (0x40 | (targetindex << 3))};
		}
		
		return result;
	}

}
