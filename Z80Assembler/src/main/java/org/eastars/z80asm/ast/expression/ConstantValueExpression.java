package org.eastars.z80asm.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.assember.LabelNotFoundException;
import org.eastars.z80asm.ast.parameter.ConstantValueParameter;

import java.util.Optional;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ConstantValueExpression implements Expression {

  private final ConstantValueParameter constantValueParameter;

  @Override
  public int evaluate(CompilationContext compilationContext) {
    return Optional.ofNullable(constantValueParameter.getIntValue())
        .orElseGet(() -> Optional.ofNullable(constantValueParameter.getValue())
            .map(compilationContext::getLabelValue)
            .orElseThrow(() -> new LabelNotFoundException(constantValueParameter.getValue())));
  }

  @Override
  public String getAssembly() {
    return Optional.ofNullable(constantValueParameter.getIntValue())
        .map(i -> {
          String result = "0000" + Integer.toHexString(i) + "h";
          return result.substring(result.length() - 5);
        }).orElseGet(constantValueParameter::getValue);
  }

}
