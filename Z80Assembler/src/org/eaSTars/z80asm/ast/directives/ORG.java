package org.eaSTars.z80asm.ast.directives;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.Z80Directive;

public class ORG extends Z80Directive {

	private Integer value;

	@Override
	public void apply(CompilationUnit cu) {
		cu.setAddresscounter(value);
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
