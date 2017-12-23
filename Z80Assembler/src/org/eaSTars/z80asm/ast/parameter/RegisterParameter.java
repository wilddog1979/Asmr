package org.eaSTars.z80asm.ast.parameter;

public class RegisterParameter extends Parameter {

	private Register register;

	public RegisterParameter() {
	}
	
	public RegisterParameter(Register register) {
		setRegister(register);
	}
	
	@Override
	public String getAssembly() {
		return register.getValue();
	}
	
	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof RegisterParameter &&
				((register == null && ((RegisterParameter)obj).getRegister() == null) ||
						(register != null && register == ((RegisterParameter)obj).getRegister()));
	}
}
