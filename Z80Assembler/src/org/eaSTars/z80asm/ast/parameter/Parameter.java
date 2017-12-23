package org.eaSTars.z80asm.ast.parameter;

public abstract class Parameter {

	public abstract String getAssembly();
	
	@Override
	public String toString() {
		return getAssembly();
	}
	
}
