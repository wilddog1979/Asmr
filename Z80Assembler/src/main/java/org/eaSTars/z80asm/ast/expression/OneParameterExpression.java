package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.assember.CompilationContext;

public class OneParameterExpression implements Expression {

	public static enum Operation {
		MINUS("-"), NOT("!");
		
		private final String value;
		
		private Operation(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private Operation operation;
	
	private Expression parameter;

	public OneParameterExpression() {
	}
	
	public OneParameterExpression(Operation operation, Expression parameter) {
		setOperation(operation);
		setParameter(parameter);
	}
	
	@Override
	public int evaluate(CompilationContext compilationContext) {
		int parameterValue = parameter.evaluate(compilationContext);
		if (operation == Operation.MINUS) {
			return -parameterValue;
		} else {
			return ~parameterValue;
		}
	}
	
	@Override
	public String getAssembly() {
		return String.format("%s(%s)", operation.getValue(), parameter.getAssembly());
	}
	
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Expression getParameter() {
		return parameter;
	}

	public void setParameter(Expression parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof OneParameterExpression &&
				((operation == null && ((OneParameterExpression)obj).getOperation() == null) ||
						(operation != null && operation == ((OneParameterExpression)obj).getOperation())) &&
				((parameter == null && ((OneParameterExpression)obj).getParameter() == null) ||
						(parameter != null && parameter.equals(((OneParameterExpression)obj).getParameter())));
	}
	
}
