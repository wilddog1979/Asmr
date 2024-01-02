package org.eastars.asm.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eastars.asm.ast.AssemblerLine;
import org.eastars.asm.ast.Instruction;
import org.eastars.asm.ast.InstructionLine;

import static org.eastars.asm.utilities.Utilities.ifNotNull;

@Getter
@AllArgsConstructor
public class AssemblerLineVisitor {

  private AbstractParseTreeVisitor<? extends Instruction> instructionVisitor;

  public AssemblerLine visitAssemblerLine(TerminalNode label, ParserRuleContext instruction, TerminalNode comment) {
    InstructionLine line = new InstructionLine();
    return ifNotNull(label, l -> line.setLabel(l.getText()))
        | ifNotNull(instruction, i -> line.setInstruction(getInstructionVisitor().visit(i)))
        | ifNotNull(comment, c -> line.setComment(c.getText())) ? line : null;
  }

}
