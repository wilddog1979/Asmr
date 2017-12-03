package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RLC extends OneParameterInstruction {

	public RLC() {
	}
	
	public RLC(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "RLC";
	}

}
