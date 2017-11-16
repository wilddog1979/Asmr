package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;

public abstract class PUSHPOP extends OneParameterInstruction {

	protected byte[] getOpcode(byte qq) {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		int registerindex = getRegisterQQIndex(parameter);
		if (registerindex != -1) {
			result = new byte[] {(byte) (qq | (registerindex << 4))};
		} else if (parameter instanceof RegisterPairParameter) {
			RegisterPair registerPair = ((RegisterPairParameter)parameter).getRegisterPair();
			if (registerPair == RegisterPair.IX) {
				result = getIXIY();
			} else if (registerPair == RegisterPair.IY) {
				result = getIXIY();
				result[0] |= 0x20;
			}
		}
		
		return result;
	}
	
	protected abstract byte[] getIXIY();

}
