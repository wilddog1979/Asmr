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
	
	protected byte[] getOpcode() {
		return null;
	}

	public Parameter getTarget() {
		return target;
	}

	public void setTarget(Parameter target) {
		this.target = target;
	}

	public Parameter getSource() {
		return source;
	}

	public void setSource(Parameter source) {
		this.source = source;
	}
}
