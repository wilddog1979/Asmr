package org.eaSTars.z80asm.ast.parameter;

public class IndexedAddressingParameter extends AddressingParameter {

	private RegisterPair registerPair;
	
	private ExpressionParameter displacement;

	public IndexedAddressingParameter() {
	}
	
	public IndexedAddressingParameter(RegisterPair registerPair, ExpressionParameter displacement) {
		setRegisterPair(registerPair);
		setDisplacement(displacement);
	}
	
	@Override
	public String getAssembly() {
		return String.format("[%s+%s]", registerPair.getValue(), displacement.getAssembly());
	}
	
	public RegisterPair getRegisterPair() {
		return registerPair;
	}

	public void setRegisterPair(RegisterPair registerPair) {
		this.registerPair = registerPair;
	}

	public ExpressionParameter getDisplacement() {
		return displacement;
	}

	public void setDisplacement(ExpressionParameter displacement) {
		this.displacement = displacement;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IndexedAddressingParameter &&
				((registerPair == null && ((IndexedAddressingParameter)obj).getRegisterPair() == null) ||
						(registerPair != null && registerPair == ((IndexedAddressingParameter)obj).getRegisterPair())) &&
				((displacement == null && ((IndexedAddressingParameter)obj).getDisplacement() == null) ||
						(displacement != null && displacement.equals(((IndexedAddressingParameter)obj).getDisplacement())));
	}
	
}
