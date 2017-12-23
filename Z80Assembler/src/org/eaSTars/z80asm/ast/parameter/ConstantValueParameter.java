package org.eaSTars.z80asm.ast.parameter;

import java.util.Optional;

public class ConstantValueParameter extends Parameter {

	private Integer intValue;
	
	private String value;
	
	public ConstantValueParameter() {
	}
	
	public ConstantValueParameter(String value) {
		setValue(value);
	}
	
	public ConstantValueParameter(int intvalue) {
		setIntValue(intvalue);
	}
	
	@Override
	public String getAssembly() {
		return Optional.ofNullable(intValue).map(i -> String.format("%xh", intValue)).orElseGet(() -> value);
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConstantValueParameter &&
				((intValue == null && ((ConstantValueParameter)obj).getIntValue() == null) ||
						(intValue != null && intValue.equals(((ConstantValueParameter)obj).getIntValue()))) &&
				((value == null && ((ConstantValueParameter)obj).getValue() == null) ||
						(value != null && value.equals(((ConstantValueParameter)obj).getValue())));
	}
	
}
