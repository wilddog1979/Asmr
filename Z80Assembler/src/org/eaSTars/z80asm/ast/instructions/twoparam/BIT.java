package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class BIT extends TwoParameterInstruction {

	public BIT() {
	}
	
	public BIT(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "BIT";
	}

}
