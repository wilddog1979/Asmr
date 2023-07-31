package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.assember.LabelNotFoundException;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;

import java.util.Optional;

public class ConstantValueExpression implements Expression {

	private ConstantValueParameter constantValueParameter;

	public ConstantValueExpression() {
	}
	
	public ConstantValueExpression(ConstantValueParameter contantValueParameter) {
		setConstantValueParameter(contantValueParameter);
	}
	
	@Override
	public int evaluate(CompilationContext compilationContext) {
		return Optional.ofNullable(constantValueParameter.getIntValue())
				.orElseGet(() -> Optional.ofNullable(constantValueParameter.getValue())
						.map(compilationContext::getLabelValue)
						.orElseThrow(() -> new LabelNotFoundException(constantValueParameter.getValue())));
	}
	
	@Override
	public String getAssembly() {
		return Optional.ofNullable(constantValueParameter.getIntValue())
				.map(i -> {
					String result = "0000" + Integer.toHexString(i) + "h";
					return result.substring(result.length() - 5);
				}).orElseGet(() -> constantValueParameter.getValue());
	}
	
	public ConstantValueParameter getConstantValueParameter() {
		return constantValueParameter;
	}

	public void setConstantValueParameter(ConstantValueParameter contantValueParameter) {
		this.constantValueParameter = contantValueParameter;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConstantValueExpression &&
				((constantValueParameter == null && ((ConstantValueExpression)obj).getConstantValueParameter() == null) ||
						(constantValueParameter != null && constantValueParameter.equals(((ConstantValueExpression)obj).getConstantValueParameter())));
	}
	
}
