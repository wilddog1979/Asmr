package org.eaSTars.asm.ast.directives;

import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.ast.Directive;

public class ORG extends Directive {

	private Integer value;

	public ORG() {
	}
	
	public ORG(Integer value) {
		setValue(value);
	}
	
	@Override
	public int evaluate(CompilationContext compilationContext) {
		return 0;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("org %xh", getValue());
	}
	
}
