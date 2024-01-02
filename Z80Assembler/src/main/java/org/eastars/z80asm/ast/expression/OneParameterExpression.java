package org.eastars.z80asm.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eastars.asm.assember.CompilationContext;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class OneParameterExpression implements Expression {

  @Getter
  @RequiredArgsConstructor
  public enum Operation {
    MINUS("-"), NOT("!");

    private final String value;

  }

  private final Operation operation;

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

}
