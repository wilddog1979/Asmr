package org.eaSTars.z80asm.ast.instructions;

import lombok.Getter;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public abstract class OneParameterInstruction extends Z80Instruction {

	@Getter
	private Parameter parameter;

	@Override
	public String getAssembly() {
		return String.format("%s %s", getMnemonic(), parameter.getAssembly());
	}

	public OneParameterInstruction setParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

}
