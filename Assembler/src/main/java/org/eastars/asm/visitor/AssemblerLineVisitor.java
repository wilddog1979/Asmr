package org.eastars.asm.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eastars.asm.ast.AssemblerLine;
import org.eastars.asm.ast.Instruction;
import org.eastars.asm.ast.InstructionLine;

@Getter
@AllArgsConstructor
public class AssemblerLineVisitor {

  private AbstractParseTreeVisitor<? extends Instruction> instructionVisitor;

  public AssemblerLine visitAssemblerLine(TerminalNode label, ParserRuleContext instruction, TerminalNode comment) {
    if (label == null && instruction == null && comment == null) {
      return null;
    }
    InstructionLine line = new InstructionLine();

    if (label != null) {
      line.setLabel(label.getText());
    }
    if (instruction != null) {
      line.setInstruction(getInstructionVisitor().visit(instruction));
    }
    if (comment != null) {
      line.setComment(comment.getText());
    }

    return line;
  }

}
