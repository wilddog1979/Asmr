package org.eaSTars.asm.ast.directives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.ast.Directive;

@AllArgsConstructor
public class ORG extends Directive {

	@Getter
	@Setter
	private Integer value;

	@Override
	public int evaluate(CompilationContext compilationContext) {
		return 0;
	}

	@Override
	public String toString() {
		return String.format("org %xh", getValue());
	}
	
}
