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
	public String getLabel() {
		return directive.getLabel();
	}
	
	@Override
	public void setLabel(String label) {
		directive.setLabel(label);
	}
	
	@Override
	public String toString() {
		return directive.toString();
	}
	
}
