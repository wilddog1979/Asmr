package org.eaSTars.asm.ast;

public class DirectiveLine extends AssemblerLine {

	private Directive directive;

	public Directive getDirective() {
		return directive;
	}

	public void setDirective(Directive directive) {
		this.directive = directive;
	}
	
	@Override
	public int getRenderedLength(CompilationUnit compilationUnit) {
		return 0;
	}
}
