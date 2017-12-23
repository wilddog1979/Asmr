package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RET extends OneParameterInstruction {

	public RET() {
	}
	
	public RET(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "RET";
	}
	
	@Override
	public String getAssembly() {
		Parameter parameter = getParameter();
		return String.format("%s%s", getMnemonic(), parameter != null ? parameter.getAssembly() : "");
	}

}
