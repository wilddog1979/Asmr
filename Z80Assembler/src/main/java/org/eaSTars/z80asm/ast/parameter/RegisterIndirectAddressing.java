package org.eaSTars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterIndirectAddressing extends AddressingParameter {

	@Getter
	private final RegisterPair registerPair;

	@Override
	public String getAssembly() {
		return String.format("[%s]", registerPair.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RegisterIndirectAddressing &&
				((registerPair == null && ((RegisterIndirectAddressing)obj).getRegisterPair() == null) ||
						(registerPair != null && registerPair == ((RegisterIndirectAddressing)obj).getRegisterPair()));
	}
	
}
