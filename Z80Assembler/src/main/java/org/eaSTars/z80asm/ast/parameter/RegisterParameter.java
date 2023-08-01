package org.eaSTars.z80asm.ast.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterParameter extends Parameter {

	@Getter
	private final Register register;

	@Override
	public String getAssembly() {
		return register.getValue();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RegisterParameter &&
				((register == null && ((RegisterParameter)obj).getRegister() == null) ||
						(register != null && register == ((RegisterParameter)obj).getRegister()));
	}

}
