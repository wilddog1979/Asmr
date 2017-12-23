package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RES extends TwoParameterInstruction {

	public RES() {
	}
	
	public RES(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "RES";
	}

}
