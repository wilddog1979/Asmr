package org.eastars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class ConstantValueParameter extends Parameter {

  private Integer intValue;

  private String value;
  
  public ConstantValueParameter(String value) {
    this.value = value;
  }
  
  public ConstantValueParameter(int intValue) {
    this.intValue = intValue;
  }
  
  @Override
  public String getAssembly() {
    return Optional.ofNullable(intValue).map(i -> String.format("%xh", intValue)).orElseGet(() -> value);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ConstantValueParameter
        && ((intValue == null && ((ConstantValueParameter) obj).getIntValue() == null)
        || (intValue != null && intValue.equals(((ConstantValueParameter) obj).getIntValue())))
        && ((value == null && ((ConstantValueParameter) obj).getValue() == null)
        || (value != null && value.equals(((ConstantValueParameter) obj).getValue())));
  }
  
}
