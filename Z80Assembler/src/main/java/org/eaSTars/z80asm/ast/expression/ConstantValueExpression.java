package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.assember.LabelNotFoundException;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;

import java.util.Optional;

public class ConstantValueExpression implements Expression {

	private ConstantValueParameter contantValueParameter;

	public ConstantValueExpression() {
	}
	
	public ConstantValueExpression(ConstantValueParameter contantValueParameter) {
		setContantValueParameter(contantValueParameter);
	}
	
	@Override
	public int evaluate(CompilationContext compilationContext) {
		return Optional.ofNullable(contantValueParameter.getIntValue()).map(i -> i.intValue())
				.orElseGet(() -> Optional.ofNullable(contantValueParameter.getValue())
						.map(v -> compilationContext.getLabelValue(v))
						.orElseThrow(() -> new LabelNotFoundException(contantValueParameter.getValue())));
	}
	
	@Override
	public String getAssembly() {
		return Optional.ofNullable(contantValueParameter.getIntValue())
				.map(i -> {
					String result = "0000" + Integer.toHexString(i) + "h";
					return result.substring(result.length() - 5, result.length());
				}).orElseGet(() -> contantValueParameter.getValue());
	}
	
	public ConstantValueParameter getContantValueParameter() {
		return contantValueParameter;
	}

	public void setContantValueParameter(ConstantValueParameter contantValueParameter) {
		this.contantValueParameter = contantValueParameter;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConstantValueExpression &&
				((contantValueParameter == null && ((ConstantValueExpression)obj).getContantValueParameter() == null) ||
						(contantValueParameter != null && contantValueParameter.equals(((ConstantValueExpression)obj).getContantValueParameter())));
	}
	
}
