package org.eastars.asm.assember;

import lombok.Getter;

import java.io.Serial;

@Getter
public class LabelNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5692979883104582747L;

  private final String label;
  
  public LabelNotFoundException(String label) {
    super(String.format("%s label was not found", label));
    this.label = label;
  }

}
