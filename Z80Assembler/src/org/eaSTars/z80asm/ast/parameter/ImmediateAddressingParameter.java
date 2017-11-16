package org.eaSTars.z80asm.ast.parameter;

public class ImmediateAddressingParameter extends AddressingParameter {

	private ExpressionParameter value;

	public ImmediateAddressingParameter() {
	}
	
	public ImmediateAddressingParameter(ExpressionParameter value) {
		this.value = value;
	}
	
	@Override
	public String getAssembly() {
		return String.format("[%s]", value.getAssembly());
	}
	
	public ExpressionParameter getValue() {
		return value;
	}

	public void setValue(ExpressionParameter value) {
		this.value = value;
	}
}
