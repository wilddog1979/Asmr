package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class ConditionParameter extends Parameter {

  private final Condition condition;

  @Override
  public String getAssembly() {
    return condition.getValue();
  }

}
