package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class HALT extends NoParameterInstruction{

	@Override
	public String getMnemonic() {
		return "HALT";
	}

}
