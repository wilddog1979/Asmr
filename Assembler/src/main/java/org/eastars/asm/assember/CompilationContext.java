package org.eastars.asm.assember;

import lombok.Getter;
import lombok.Setter;
import org.eastars.asm.ast.AssemblerLine;
import org.eastars.asm.ast.Directive;
import org.eastars.asm.ast.DirectiveLine;
import org.eastars.asm.ast.InstructionLine;
import org.eastars.asm.ast.directives.ORG;

import java.util.*;

public class CompilationContext {

  public enum Phase {
    LABEL_PROCESS, COMPILATION;
  }

  private record CompilationLine(int address, AssemblerLine assemblerLine) {
  }

  @Getter
  @Setter
  private Phase phase = Phase.LABEL_PROCESS;

  @Getter
  @Setter
  private int address = 0;
  
  private final List<CompilationLine> lines = new ArrayList<>();
  
  private final Map<String, CompilationLine> labels = new HashMap<>();

  public void addInstructionLine(AssemblerLine assemblerLine, int length) {
    if (assemblerLine instanceof DirectiveLine) {
      Directive directive = ((DirectiveLine) assemblerLine).getDirective();
      if (directive instanceof ORG) {
        setAddress(((ORG) directive).getValue());
      }
    }
    CompilationLine cline = new CompilationLine(getAddress(), assemblerLine);
    lines.add(cline);
    Optional.ofNullable(assemblerLine.getLabel()).ifPresent(l -> labels.put(l, cline));
    increaseAddress(length);
  }
  
  public int getLabelValue(String label) {
    int result = 0;
    
    if (getPhase() != Phase.LABEL_PROCESS) {
      CompilationLine compilationLine = labels.get(label);
      if (compilationLine == null) {
        throw new LabelNotFoundException(label);
      }
      if (compilationLine.assemblerLine instanceof InstructionLine) {
        result = compilationLine.address;
      } else if (compilationLine.assemblerLine instanceof DirectiveLine) {
        result = ((DirectiveLine) compilationLine.assemblerLine).getDirective().evaluate(this);
      }
    }
    
    return result;
  }
  
  private void increaseAddress(int value) {
    this.address += value;
  }
  
}
