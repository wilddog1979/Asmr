package org.eaSTars.asm.ast;

public abstract class Directive {

	private String label;
	
	public abstract void apply(CompilationUnit cu);
	
	public abstract int getValue(CompilationUnit compilationUnit);

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
