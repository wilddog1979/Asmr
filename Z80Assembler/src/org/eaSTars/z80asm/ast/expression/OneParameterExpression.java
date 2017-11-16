package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.ast.CompilationUnit;

public class OneParameterExpression implements Expression {

	public static enum Operation {
		MINUS("-"), NOT("!");
		
		private String value;
		
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
		this.operation = operation;
		this.parameter = parameter;
	}
	
	@Override
	public int evaluate(CompilationUnit compilationUnit) {
		int parameterValue = parameter.evaluate(compilationUnit);
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
	
}
