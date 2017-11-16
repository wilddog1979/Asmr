package org.eaSTars.z80asm.ast.parameter;

public class RegisterParameter extends Parameter {

	private Register register;

	public RegisterParameter() {
	}
	
	public RegisterParameter(Register register) {
		this.register = register;
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
}
