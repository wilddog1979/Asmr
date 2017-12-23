package org.eaSTars.z80asm.ast.parameter;

public class RegisterPairParameter extends Parameter {

	private RegisterPair registerPair;

	public RegisterPairParameter() {
	}
	
	public RegisterPairParameter(RegisterPair registerPair) {
		setRegisterPair(registerPair);
	}
	
	@Override
	public String getAssembly() {
		return registerPair.getValue();
	}
	
	public RegisterPair getRegisterPair() {
		return registerPair;
	}

	public void setRegisterPair(RegisterPair registerPair) {
		this.registerPair = registerPair;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof RegisterPairParameter &&
				((registerPair == null && ((RegisterPairParameter)obj).getRegisterPair() == null) ||
						(registerPair != null && registerPair == ((RegisterPairParameter)obj).getRegisterPair()));
	}
	
}
