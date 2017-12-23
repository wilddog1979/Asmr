package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class PUSH extends OneParameterInstruction {

	public PUSH() {
	}
	
	public PUSH(Parameter parameter) {
		setParameter(parameter);
	}
	
	@Override
	public String getMnemonic() {
		return "PUSH";
	}

}
