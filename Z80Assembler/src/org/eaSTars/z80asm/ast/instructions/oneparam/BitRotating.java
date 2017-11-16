package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;

public abstract class BitRotating extends OneParameterInstruction {

	@Override
	protected byte[] getOpcode() {
		return null;
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = getRH();
			result[1] |= registerindex;
		} else if (parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerpair = indexedAddressParameter.getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = getRefIXIY();
				result[2] = (byte) indexedAddressParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerpair == RegisterPair.IY) {
				result = getRefIXIY();
				result[0] |= 0x20;
				result[2] = (byte) indexedAddressParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}
	
	protected abstract byte[] getRH();
	
	protected abstract byte[] getRefIXIY();

}
