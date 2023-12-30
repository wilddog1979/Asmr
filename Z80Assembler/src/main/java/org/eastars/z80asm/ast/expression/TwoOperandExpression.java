package org.eastars.z80asm.ast.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eastars.asm.assember.CompilationContext;

@RequiredArgsConstructor
public class TwoOperandExpression implements Expression {

  @RequiredArgsConstructor
  public enum Operation {
    PLUS("+"), MINUS("-"), STAR("*"), DIV("/"), SHL("<<"), SHR(">>"), AND("&"), XOR("^"), OR("|");

    @Getter
    private final String value;

  }

  @Getter
  private final Expression leftOperand;

  @Getter
  private final Operation operation;

  @Getter
  private final Expression rightOperand;

  @Override
  public int evaluate(CompilationContext compilationContext) {
    int leftParameterValue = leftOperand.evaluate(compilationContext);
    int rightParameterValue = rightOperand.evaluate(compilationContext);
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

  @Override
  public boolean equals(Object obj) {
    return obj instanceof TwoOperandExpression &&
        ((leftOperand == null && ((TwoOperandExpression)obj).leftOperand == null) ||
            (leftOperand != null && leftOperand.equals(((TwoOperandExpression)obj).leftOperand))) &&
        ((operation == null && ((TwoOperandExpression)obj).operation == null) ||
            (operation != null && operation == ((TwoOperandExpression)obj).operation)) &&
        ((rightOperand == null && ((TwoOperandExpression)obj).rightOperand == null) ||
            (rightOperand != null && rightOperand.equals(((TwoOperandExpression)obj).rightOperand)));
  }

}
