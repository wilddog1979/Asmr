package org.eaSTars.z80asm.ast.instructions;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Instruction;

public abstract class NoParameterInstruction extends Z80Instruction {

	@Override
	public String getAssembly() {
		return getMnemonic();
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		return getOpcode();
	}
	
	protected abstract byte[] getOpcode();
	
}
