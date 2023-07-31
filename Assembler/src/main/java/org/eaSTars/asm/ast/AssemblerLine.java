package org.eaSTars.asm.ast;

public abstract class AssemblerLine {

	protected String label;

	protected String comment;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
