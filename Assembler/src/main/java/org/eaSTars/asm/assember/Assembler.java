package org.eaSTars.asm.assember;

import lombok.Getter;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.eaSTars.asm.assember.CompilationContext.Phase;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.asm.ast.InstructionLine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class Assembler {

	@Getter
	private ANTLRErrorListener errorListener;
	
	protected abstract CompilationUnit parseInstructions(String sourceFileName);
	
	public void assemble(String sourceFile, String outfile) {
		errorListener = new AssemblerErrorListener(sourceFile);
		
		CompilationUnit result = parseInstructions(sourceFile);
		
		process(result, outfile);
	}
	
	protected abstract byte[] getInstruction(CompilationContext compilationContext, Instruction instruction);
	
	private void process(CompilationUnit instructions, String outfile) {
		CompilationContext ctx = new CompilationContext();
		try (OutputStream out = new FileOutputStream(outfile)) {
			ctx.setPhase(Phase.LABEL_PROCESS);
			for (int i = 0; i < instructions.getLineCount(); ++i) {
				AssemblerLine line = instructions.getLine(i);
				System.out.println(line.toString());
				if (line instanceof InstructionLine instructionline) {
					Instruction instruction = instructionline.getInstruction();
					if (instruction != null) {
						byte[] inst = getInstruction(ctx, instruction);
						ctx.addInstructionLine(line, inst.length);
					} else {
						ctx.addInstructionLine(line, 0);
					}
				} else {
					ctx.addInstructionLine(line, 0);
				}
			}
			ctx.setPhase(Phase.COMPILATION);
			for (int i = 0; i < instructions.getLineCount(); ++i) {
				AssemblerLine line = instructions.getLine(i);
				if (line instanceof InstructionLine instructionline) {
					Instruction instruction = instructionline.getInstruction();
					if (instruction != null) {
						byte[] inst = getInstruction(ctx, instruction);
						out.write(inst);
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
