package org.eaSTars.asm.ast;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.eaSTars.asm.assember.LabelAlreadyDefinedException;
import org.eaSTars.asm.assember.LabelNotFoundException;

public class CompilationUnit {

	private int addresscounter = 0;
	
	private List<AssemblerLine> lines = new Vector<AssemblerLine>();

	public int getAddresscounter() {
		return addresscounter;
	}

	public void setAddresscounter(int addresscounter) {
		this.addresscounter = addresscounter;
	}
	
	public void increaseAddresscounter(int value) {
		this.addresscounter += value;
	}
	
	public void addLine(AssemblerLine line) {
		Optional.ofNullable(line.getLabel()).ifPresent(label -> lines.stream()
				.filter(l -> label.equals(l.getLabel())).findFirst()
				.ifPresent(l -> {throw new LabelAlreadyDefinedException(label);}));
		
		line.setAddress(getAddresscounter());
		lines.add(line);
		increaseAddresscounter(line.getRenderedLength(this));
	}
	
	public int getLabelValue(String label) {
		return lines.stream().filter(l -> label.equals(l.getLabel()))
				.findFirst().map(l -> {
					int result = 0;
					if (l instanceof InstructionLine) {
						result = l.getAddress();
					} else if (l instanceof DirectiveLine) {
						result = ((DirectiveLine)l).getDirective().getValue(this);
					}
					return result;
				})
				.orElseThrow(() -> {throw new LabelNotFoundException(label);});
	}
	
	public int getLineCount() {
		return lines.size();
	}
	
	public AssemblerLine getLine(int index) {
		return lines.get(index);
	}
}
