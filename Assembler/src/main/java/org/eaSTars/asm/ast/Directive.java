package org.eaSTars.asm.ast;

import lombok.Getter;
import lombok.Setter;
import org.eaSTars.asm.assember.CompilationContext;

public abstract class Directive {

	@Getter
	@Setter
	protected String label;

	public abstract int evaluate(CompilationContext compilationContext);
	
}
