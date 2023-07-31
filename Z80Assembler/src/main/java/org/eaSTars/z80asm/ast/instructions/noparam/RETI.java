package org.eaSTars.z80asm.ast.instructions.noparam;

import org.eaSTars.z80asm.ast.instructions.NoParameterInstruction;

public class RETI extends NoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RETI";
	}

}
