package org.eaSTars.asm.assember;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.eaSTars.asm.assember.CompilationContext.Phase;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.Instruction;
import org.eaSTars.asm.ast.InstructionLine;

public abstract class Assembler {

	private ANTLRErrorListener errorListener;
	
	protected abstract CompilationUnit parseInstructions(String sourcefilename);
	
	public void assemble(String sourcefile, String outfile) {
		errorListener = new AssemblerErrorListener(sourcefile);
		
		CompilationUnit result = parseInstructions(sourcefile);
		
		process(result, outfile);
	}
	
	protected abstract byte[] getInstuction(CompilationContext compilationContext, Instruction instruction);
	
	private void process(CompilationUnit instructions, String outfile) {
		CompilationContext ctx = new CompilationContext();
		try (OutputStream out = new FileOutputStream(outfile)) {
			ctx.setPhase(Phase.LABELPROCESS);
			for (int i = 0; i < instructions.getLineCount(); ++i) {
				AssemblerLine line = instructions.getLine(i);
				System.out.println(line.toString());
				if (line instanceof InstructionLine) {
					InstructionLine instructionline = (InstructionLine) line;
					Instruction instruction = instructionline.getInstruction();
					if (instruction != null) {
						byte[] inst = getInstuction(ctx, instruction);
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
				if (line instanceof InstructionLine) {
					InstructionLine instructionline = (InstructionLine) line;
					Instruction instruction = instructionline.getInstruction();
					if (instruction != null) {
						byte[] inst = getInstuction(ctx, instruction);
						out.write(inst);
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public ANTLRErrorListener getErrorListener() {
		return errorListener;
	}

	public void setErrorListener(ANTLRErrorListener errorListener) {
		this.errorListener = errorListener;
	}
	
}
