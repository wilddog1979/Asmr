package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RL extends OneParameterInstruction {

	public RL() {
	}
	
	public RL(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "RL";
	}

}
