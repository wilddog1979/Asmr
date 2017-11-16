package org.eaSTars.asm.ast;

public abstract class AssemblerLine {

	private int address;
	
	protected String label;

	protected String comment;
	
	public abstract int getRenderedLength(CompilationUnit compilationUnit);

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return fixlength(address, 4);
	}
	
	private String fixlength(int value, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			sb.append('0');
		}
		sb.append(Integer.toHexString(value));
		
		return sb.substring(sb.length() - length);
	}

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
