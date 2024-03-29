package org.eastars.asm.assember;

import lombok.Getter;

import java.io.Serial;

@Getter
public class LabelAlreadyDefinedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7531480576547923852L;

  private final String label;
  
  public LabelAlreadyDefinedException(String label) {
    super(String.format("%s label was already defined", label));
    this.label = label;
  }

}
