package org.eaSTars.asm.ast;

import java.util.List;
import java.util.Vector;

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
	
	public void addLine(AssemblerLine line) {
		line.setAddress(addresscounter);
		lines.add(line);
		addresscounter += line.getRenderedLength(this);
	}
	
	public int getLabelValue(String label) {
		return lines.stream().filter(l -> label.equals(l.getLabel()))
				.findFirst().map(l -> l.getAddress())
				.orElseThrow(() -> new LabelNotFoundException(label));
	}
	
	public int getLineCount() {
		return lines.size();
	}
	
	public AssemblerLine getLine(int index) {
		return lines.get(index);
	}
}
