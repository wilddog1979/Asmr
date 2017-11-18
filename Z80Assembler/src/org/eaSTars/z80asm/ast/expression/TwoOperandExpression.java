package org.eaSTars.z80asm.ast.expression;

import org.eaSTars.asm.ast.CompilationUnit;

public class TwoOperandExpression implements Expression {

	public static enum Operation {
		PLUS("+"), MINUS("-"), STAR("*"), DIV("/"), SHL("<<"), SHR(">>"), AND("&"), XOR("^"), OR("|");
		
		private String value;
		
		private Operation(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private Expression leftOperand;
	
	private Operation operation;
	
	private Expression rightOperand;

	public TwoOperandExpression() {
	}
	
	public TwoOperandExpression(Expression left, Operation operation, Expression right) {
		setLeftOperand(left);
		setOperation(operation);
		setRightOperand(right);
	}
	
	@Override
	public int evaluate(CompilationUnit compilationUnit) {
		int leftParameterValue = leftOperand.evaluate(compilationUnit);
		int rightParameterValue = rightOperand.evaluate(compilationUnit);
		if (operation == Operation.PLUS) {
			return leftParameterValue + rightParameterValue;
		} else if (operation == Operation.MINUS) {
			return leftParameterValue - rightParameterValue;
		} else if (operation == Operation.STAR) {
			return leftParameterValue * rightParameterValue;
		} else if (operation == Operation.DIV) {
			return leftParameterValue / rightParameterValue;
		} else if (operation == Operation.SHL) {
			return leftParameterValue << rightParameterValue;
		} else if (operation == Operation.SHR) {
			return leftParameterValue >> rightParameterValue;
		} else if (operation == Operation.AND) {
			return leftParameterValue & rightParameterValue;
		} else if (operation == Operation.XOR) {
			return leftParameterValue ^ rightParameterValue;
		} else {
			return leftParameterValue | rightParameterValue;
		}
	}
	
	@Override
	public String getAssembly() {
		return String.format("(%s) %s (%s)", leftOperand.getAssembly(), operation.getValue(), rightOperand.getAssembly());
	}
	
	public Expression getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(Expression leftOperand) {
		this.leftOperand = leftOperand;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(Expression rightOperand) {
		this.rightOperand = rightOperand;
	}
	
}
