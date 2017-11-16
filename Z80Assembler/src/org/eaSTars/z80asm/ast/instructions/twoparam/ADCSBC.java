package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public abstract class ADCSBC extends TwoParameterInstruction {
	
	@Override
	protected byte[] getOpcode() {
		return null;
	}
	
	protected byte[] getOpcode(CompilationUnit compilationUnit, byte rh, byte immediate) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			int sourceindex = getRegisterRHIndex(sourceparameter);
			if (sourceindex != -1) {
				result = new byte[] {(byte) (rh | sourceindex)};
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = new byte[] {immediate, (byte) ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit)};
			} else if (sourceparameter instanceof IndexedAddressingParameter) {
				IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) sourceparameter;
				if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
					result = getRefIXIY();
					result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
				} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
					result = getRefIXIY();
					result[0] |= 0x20;
					result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit);
				}
			}
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL) {
			int sourceindex = getRegisterSSIndex(sourceparameter);
			if (sourceindex != -1) {
				result = getSS();
				result[1] |= (byte)(sourceindex << 4);
			}
		}
		
		return result;
	}

	protected abstract byte[] getRefIXIY();
	
	protected abstract byte[] getSS();
	
}
