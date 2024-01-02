package org.eastars.z80asm.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eastars.asm.assember.CompilationContext;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class TwoOperandExpression implements Expression {

  @Getter
  @RequiredArgsConstructor
  public enum Operation {
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    DIV("/"),
    SHL("<<"),
    SHR(">>"),
    AND("&"),
    XOR("^"),
    OR("|");

    private final String value;

  }

  private final Expression leftOperand;

  private final Operation operation;

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

}
