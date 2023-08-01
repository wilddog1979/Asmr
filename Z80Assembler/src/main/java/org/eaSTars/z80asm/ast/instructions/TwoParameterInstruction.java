package org.eaSTars.z80asm.ast.instructions;

import lombok.Getter;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public abstract class TwoParameterInstruction extends Z80Instruction {

	@Getter
	private Parameter target;

	@Getter
	private Parameter source;
	
	@Override
	public String getAssembly() {
		return String.format("%s %s, %s", getMnemonic(), target.getAssembly(), source.getAssembly());
	}

	public TwoParameterInstruction setTarget(Parameter target) {
		this.target = target;
		return this;
	}

	public TwoParameterInstruction setSource(Parameter source) {
		this.source = source;
		return this;
	}
	
}
