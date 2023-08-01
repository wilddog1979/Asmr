package org.eaSTars.z80asm.ast.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eaSTars.asm.assember.CompilationContext;

@RequiredArgsConstructor
public class OneParameterExpression implements Expression {

	@RequiredArgsConstructor
	public enum Operation {
		MINUS("-"), NOT("!");

		@Getter
		private final String value;

	}

	@Getter
	private final Operation operation;

	@Getter
	private final Expression parameter;
	
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

	@Override
	public boolean equals(Object obj) {
		return obj instanceof OneParameterExpression &&
				((operation == null && ((OneParameterExpression)obj).operation == null) ||
						(operation != null && operation == ((OneParameterExpression)obj).operation)) &&
				((parameter == null && ((OneParameterExpression)obj).parameter == null) ||
						(parameter != null && parameter.equals(((OneParameterExpression)obj).parameter)));
	}
	
}
