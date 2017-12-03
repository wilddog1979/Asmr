package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
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

}
