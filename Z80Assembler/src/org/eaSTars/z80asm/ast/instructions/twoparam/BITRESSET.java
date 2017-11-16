package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;

public abstract class BITRESSET extends TwoParameterInstruction {
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		if (targetparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)targetparameter).getExpressionValue(compilationUnit);
			if (value >= 0 && value <= 7) {
				int sourceindex = getRegisterRHIndex(sourceparameter);
				if (sourceindex != -1) {
					result = getRH();
					result[1] |= (byte)((value << 3) | sourceindex);
				} else if (sourceparameter instanceof IndexedAddressingParameter) {
					IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) sourceparameter;
					if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
						result = getRefIXIY();
						result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
						result[3] |= (byte)(value << 3);
					} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
						result = getRefIXIY();
						result[0] |= 0x20;
						result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
						result[3] |= (byte)(value << 3);
					}
				}
			}
		}
		
		return result;
	}

	protected abstract byte[] getRH();
	
	protected abstract byte[] getRefIXIY();
	
}
