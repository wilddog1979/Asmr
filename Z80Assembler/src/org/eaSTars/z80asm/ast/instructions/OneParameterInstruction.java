package org.eaSTars.z80asm.ast.instructions;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public abstract class OneParameterInstruction extends Z80Instruction {

	private Parameter parameter;

	@Override
	public String getAssembly() {
		return String.format("%s %s", getMnemonic(), parameter.getAssembly());
	}

	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode();
	}
	
	public Parameter getParameter() {
		return parameter;
	}

	public OneParameterInstruction setParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

}
