package org.eaSTars.z80asm.ast.parameter;

public class IndexedAddressingParameter extends AddressingParameter {

	private RegisterPair registerPair;
	
	private ExpressionParameter displacement;

	public IndexedAddressingParameter() {
	}
	
	public IndexedAddressingParameter(RegisterPair registerPair, ExpressionParameter displacement) {
		this.registerPair = registerPair;
		this.displacement = displacement;
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
}
