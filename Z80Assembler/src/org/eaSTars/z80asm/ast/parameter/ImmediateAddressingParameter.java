package org.eaSTars.z80asm.ast.parameter;

public class ImmediateAddressingParameter extends AddressingParameter {

	private ExpressionParameter value;

	public ImmediateAddressingParameter() {
	}
	
	public ImmediateAddressingParameter(ExpressionParameter value) {
		setValue(value);
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
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ImmediateAddressingParameter &&
				((value == null && ((ImmediateAddressingParameter)obj).getValue() == null) ||
						(value != null && value.equals(((ImmediateAddressingParameter)obj).getValue())));
	}
	
}
