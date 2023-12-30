package org.eastars.asm.ast;

import lombok.Getter;
import lombok.Setter;

public class DirectiveLine extends AssemblerLine {

  @Getter
  @Setter
  private Directive directive;

  @Override
  public String getLabel() {
    return directive.getLabel();
  }

  @Override
  public void setLabel(String label) {
    directive.setLabel(label);
  }

  @Override
  public String toString() {
    return directive.toString();
  }

}
