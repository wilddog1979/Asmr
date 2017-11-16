package org.eaSTars.asm.ast;

public class InstructionLine extends AssemblerLine {

	private Instruction instruction;
	
	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	@Override
	public int getRenderedLength(CompilationUnit compilationUnit) {
		return instruction != null ? instruction.getOpcode(compilationUnit).length : 0;
	}
	
	@Override
	public String toString() {
		return String.format("%s\t%s%s%s",
				super.toString(),
				label != null ? label + ": " : "",
						instruction != null ? instruction.getAssembly() + " " : "",
								comment != null ? comment : "");
	}

}
