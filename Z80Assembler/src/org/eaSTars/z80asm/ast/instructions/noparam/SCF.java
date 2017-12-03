package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class SCF extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "SCF";
	}

}
