package org.eaSTars.z80asm.ast.parameter;

public class RegisterIndirectAddressing extends AddressingParameter {

	private RegisterPair registerPair;

	public RegisterIndirectAddressing() {
	}
	
	public RegisterIndirectAddressing(RegisterPair registerPair) {
		setRegisterPair(registerPair);
	}
	
	@Override
	public String getAssembly() {
		return String.format("[%s]", registerPair.getValue());
	}
	
	public RegisterPair getRegisterPair() {
		return registerPair;
	}

	public void setRegisterPair(RegisterPair registerPair) {
		this.registerPair = registerPair;
	}
}
