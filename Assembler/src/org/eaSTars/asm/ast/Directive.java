package org.eaSTars.asm.ast;

import org.eaSTars.asm.assember.CompilationContext;

public abstract class Directive {

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public abstract int evaluate(CompilationContext compilationContext);
	
}
