package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;

public abstract class INCDEC extends OneParameterInstruction {

	@Override
	protected byte[] getOpcode() {
		return null;
	}
	
	protected byte[] getOpcode(CompilationUnit compilationUnit, byte ss, byte rh) {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		int registerindex = getRegisterSSIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (ss | (registerindex << 4))};
		} else if ((registerindex = getRegisterRHIndex(parameter)) != -1) {
			result = new byte[] {(byte) (rh | (registerindex << 3))};
		} else if (parameter instanceof RegisterPairParameter) {
			RegisterPair registerpair = ((RegisterPairParameter) parameter).getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = getIXIY();
			} else if (registerpair == RegisterPair.IY) {
				result = getIXIY();
				result[0] |= 0x20;
			}
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerpair = indexedAddressingParameter.getRegisterPair();
			if (registerpair == RegisterPair.IX) {
				result = getRefIXIY();
				result[3] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerpair == RegisterPair.IY) {
				result = getRefIXIY();
				result[0] |= 0x20;
				result[3] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}
	
	protected abstract byte[] getIXIY();
	
	protected abstract byte[] getRefIXIY();

}
