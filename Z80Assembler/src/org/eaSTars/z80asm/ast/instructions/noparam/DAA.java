package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class DAA extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "DAA";
	}

}
