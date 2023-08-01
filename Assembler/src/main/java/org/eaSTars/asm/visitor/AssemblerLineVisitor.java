package org.eaSTars.asm.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.asm.ast.InstructionLine;

import java.util.Optional;

@AllArgsConstructor
public class AssemblerLineVisitor {

	@Getter
	private AbstractParseTreeVisitor<? extends Instruction> instructionVisitor;
	
	public AssemblerLine visitAssemblerLine(TerminalNode label, ParserRuleContext instruction, TerminalNode comment) {
		if (label == null && instruction == null && comment == null) {
			return null;
		}
		InstructionLine line = new InstructionLine();

		Optional.ofNullable(label).ifPresent(l -> line.setLabel(l.getText()));
		Optional.ofNullable(instruction).ifPresent(i -> line.setInstruction(getInstructionVisitor().visit(instruction)));
		Optional.ofNullable(comment).ifPresent(c -> line.setComment(c.getText()));
		
		return line;
	}

}
