package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;

public abstract class SUBANDXORORCP extends OneParameterInstruction {
	
	protected byte[] getOpcode(CompilationUnit compilationUnit, byte rh, byte immediate) {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		int registerindex = getRegisterRHIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (rh | registerindex)};
		} else if (parameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter) parameter).getExpressionValue(compilationUnit);
			result = new byte[] {immediate, 0};
			result[1] = (byte)(value & 0xff);
		}
		if (result == null && parameter instanceof IndexedAddressingParameter) {
			IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) parameter;
			RegisterPair registerPair = indexedAddressingParameter.getRegisterPair();
			if (registerPair == RegisterPair.IX) {
				result = getRefIXIY();
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			} else if (registerPair == RegisterPair.IY) {
				result = getRefIXIY();
				result[0] |= 0x20;
				result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
			}
		}
		
		return result;
	}
	
	protected abstract byte[] getRefIXIY();
	
}
