package org.eastars.asm.ast;

import org.eastars.asm.assember.LabelAlreadyDefinedException;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class CompilationUnit {

  private final List<AssemblerLine> lines = new Vector<>();

  public void addLine(AssemblerLine line) {
    Optional.ofNullable(line.getLabel()).ifPresent(label -> lines.stream()
        .filter(l -> label.equals(l.getLabel())).findFirst()
        .ifPresent(l -> {
          throw new LabelAlreadyDefinedException(label);
        }));

    lines.add(line);
  }

  public int getLineCount() {
    return lines.size();
  }

  public AssemblerLine getLine(int index) {
    return lines.get(index);
  }
}
