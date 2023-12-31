package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.assember.MismatchingParameterSizeException;
import org.eastars.z80asm.ast.expression.Expression;

@Getter
@RequiredArgsConstructor
public class ExpressionParameter extends Parameter {

  private final Expression expression;

  private final int expectedBitCount;

  @Override
  public String getAssembly() {
    return expression.getAssembly();
  }

  public int getExpressionValue(CompilationContext compilationContext) {
    int result = expression.evaluate(compilationContext);

    if ((result & ((1 << expectedBitCount) - 1)) != result) {
      throw new MismatchingParameterSizeException(result, expectedBitCount);
    }

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ExpressionParameter
        && ((expression == null && ((ExpressionParameter) obj).expression == null)
        || (expression != null && expression.equals(((ExpressionParameter) obj).expression)));
  }

}
