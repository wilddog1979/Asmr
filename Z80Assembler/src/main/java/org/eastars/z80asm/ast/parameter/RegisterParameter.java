package org.eastars.z80asm.ast.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class RegisterParameter extends Parameter {

  private final Register register;

  @Override
  public String getAssembly() {
    return register.getValue();
  }

}
