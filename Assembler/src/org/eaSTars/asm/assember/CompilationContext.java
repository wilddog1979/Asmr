package org.eaSTars.asm.assember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eaSTars.asm.ast.AssemblerLine;
import org.eaSTars.asm.ast.Directive;
import org.eaSTars.asm.ast.DirectiveLine;
import org.eaSTars.asm.ast.InstructionLine;
import org.eaSTars.asm.ast.directives.ORG;

public class CompilationContext {

	public enum Phase {
		LABELPROCESS, COMPILATION;
	}
	
	private class CompilationLine {
		private int address;
		
		private AssemblerLine assemblerLine;
		
		public CompilationLine(int address, AssemblerLine assemblerLine) {
			this.address = address;
			this.assemblerLine = assemblerLine;
		}
	}
	
	private Phase phase = Phase.LABELPROCESS;
	
	private int address = 0;
	
	private List<CompilationLine> lines = new ArrayList<CompilationLine>();
	
	private Map<String, CompilationLine> labels = new HashMap<String, CompilationLine>();

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
		
		if (getPhase() != Phase.LABELPROCESS) {
			CompilationLine compilationLine = labels.get(label);
			if (compilationLine == null) {
				throw new LabelNotFoundException(label);
			}
			if (compilationLine.assemblerLine instanceof InstructionLine) {
				result = compilationLine.address;
			} else if (compilationLine.assemblerLine instanceof DirectiveLine) {
				result = ((DirectiveLine)compilationLine.assemblerLine).getDirective().evaluate(this);
			}
		}
		
		return result;
	}
	
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
	
	private void increaseAddress(int value) {
		this.address += value;
	}
	
}
