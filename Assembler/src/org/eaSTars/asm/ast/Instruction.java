package org.eaSTars.asm.ast;

public interface Instruction {

	public abstract String getMnemonic();
	
	public abstract String getAssembly();
	
	public abstract byte[] getOpcode(CompilationUnit compilationUnit);
}
