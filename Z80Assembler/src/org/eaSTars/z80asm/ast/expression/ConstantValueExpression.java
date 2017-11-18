package org.eaSTars.z80asm.ast.expression;

import java.util.Optional;

import org.eaSTars.asm.assember.LabelNotFoundException;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;

public class ConstantValueExpression implements Expression {

	private ConstantValueParameter contantValueParameter;

	public ConstantValueExpression() {
	}
	
	public ConstantValueExpression(ConstantValueParameter contantValueParameter) {
		setContantValueParameter(contantValueParameter);
	}
	
	@Override
	public int evaluate(CompilationUnit compilationUnit) {
		return Optional.ofNullable(contantValueParameter.getIntValue()).map(i -> i.intValue())
				.orElseGet(() -> Optional.ofNullable(contantValueParameter.getValue())
						.map(v -> compilationUnit.getLabelValue(v))
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
	
}
