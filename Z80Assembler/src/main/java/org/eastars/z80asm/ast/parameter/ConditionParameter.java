package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConditionParameter extends Parameter {

  @Getter
  private final Condition condition;

  @Override
  public String getAssembly() {
    return condition.getValue();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ConditionParameter &&
        ((condition == null && ((ConditionParameter)obj).getCondition() == null) ||
            (condition != null && condition == ((ConditionParameter)obj).getCondition()));
  }

}
