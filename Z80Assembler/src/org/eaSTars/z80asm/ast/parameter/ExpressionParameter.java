package org.eaSTars.z80asm.ast.parameter;

import org.eaSTars.asm.assember.MismatchingParameterSizeException;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.expression.Expression;

public class ExpressionParameter extends Parameter {

	private Expression expression;
	
	private int expectedBitCount;
	
	public ExpressionParameter() {
	}
	
	public ExpressionParameter(Expression expression, int expectedBitCount) {
		setExpression(expression);
		setExpectedBitCount(expectedBitCount);
	}
	
	@Override
	public String getAssembly() {
		return expression.getAssembly();
	}

	public int getExpressionValue(CompilationUnit compilationUnit) {
		int result = expression.evaluate(compilationUnit);
		
		if ((result & ((1 << expectedBitCount) - 1)) != result) {
			throw new MismatchingParameterSizeException(result, expectedBitCount);
		}
		
		return result;
	}
	
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public int getExpectedBitCount() {
		return expectedBitCount;
	}

	public void setExpectedBitCount(int expectedBitCount) {
		this.expectedBitCount = expectedBitCount;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ExpressionParameter &&
				((expression == null && ((ExpressionParameter)obj).getExpression() == null) ||
						(expression != null && expression.equals(((ExpressionParameter)obj).getExpression())));
	}

}
