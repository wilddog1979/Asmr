package org.eaSTars.asm.assember;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.InstructionLine;

public abstract class Assembler {

	private ANTLRErrorListener errorListener;
	
	protected abstract CompilationUnit parseInstructions(String sourcefilename);
	
	public void assemble(String sourcefile, String outfile) {
		errorListener = new AssemblerErrorListener(sourcefile);
		
		CompilationUnit result = parseInstructions(sourcefile);
		
		process(result, outfile);
	}
	
	private void process(CompilationUnit instructions, String outfile) {
		try (OutputStream out = new FileOutputStream(outfile)) {
			for (int i = 0; i < instructions.getLineCount(); ++i) {
				AssemblerLine line = instructions.getLine(i);
				System.out.println(line.toString());
				if (line instanceof InstructionLine) {
					InstructionLine instructionline = (InstructionLine) line;
					if (instructionline.getInstruction() != null) {
						
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
