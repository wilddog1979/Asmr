package org.eaSTars.z80asm.ast.directives;

import org.eaSTars.z80asm.ast.Z80Directive;

public class ORG extends Z80Directive {

	private Integer value;

	public ORG() {
	}
	
	public ORG(Integer value) {
		setValue(value);
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
