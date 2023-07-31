package org.eaSTars.asm.ast;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.eaSTars.asm.assember.LabelAlreadyDefinedException;

public class CompilationUnit {

	private List<AssemblerLine> lines = new Vector<AssemblerLine>();
	
	public void addLine(AssemblerLine line) {
		Optional.ofNullable(line.getLabel()).ifPresent(label -> lines.stream()
				.filter(l -> label.equals(l.getLabel())).findFirst()
				.ifPresent(l -> {throw new LabelAlreadyDefinedException(label);}));
		
		lines.add(line);
	}
	
	public int getLineCount() {
		return lines.size();
	}
	
	public AssemblerLine getLine(int index) {
		return lines.get(index);
	}
}
