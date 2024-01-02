package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
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
  
}
