package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class DJNZ extends OneParameterInstruction {

	public DJNZ() {
	}
	
	public DJNZ(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "DJNZ";
	}
	
	@Override
	public String getAssembly() {
		return String.format("%s %s", getMnemonic(), getParameter().getAssembly());
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		if (parameter instanceof ExpressionParameter) {
			ExpressionParameter expressionParameter = (ExpressionParameter) parameter;
			result = new byte[] {0x10, (byte) (expressionParameter.getExpressionValue(compilationUnit) - 2)};
		}
		
		return result;
	}

}
