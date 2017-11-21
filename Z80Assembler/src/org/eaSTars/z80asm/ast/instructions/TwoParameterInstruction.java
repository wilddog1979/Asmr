package org.eaSTars.z80asm.ast.instructions;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public abstract class TwoParameterInstruction extends Z80Instruction {

	private Parameter target;
	
	private Parameter source;
	
	@Override
	public String getAssembly() {
		return String.format("%s %s, %s", getMnemonic(), target.getAssembly(), source.getAssembly());
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode();
	}
	
	public Parameter getTarget() {
		return target;
	}

	public TwoParameterInstruction setTarget(Parameter target) {
		this.target = target;
		return this;
	}

	public Parameter getSource() {
		return source;
	}

	public TwoParameterInstruction setSource(Parameter source) {
		this.source = source;
		return this;
	}
}
