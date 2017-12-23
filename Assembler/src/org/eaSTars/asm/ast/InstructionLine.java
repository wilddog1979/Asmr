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
	public String toString() {
		return String.format("%s%s%s",
				label != null ? label + ": " : "",
						instruction != null ? instruction.getAssembly() + " " : "",
								comment != null ? comment : "");
	}

}
