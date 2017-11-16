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

public class ADD extends TwoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "ADD";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		int sourceindex = getRegisterSSIndex(sourceparameter);
		if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL &&
				sourceindex != -1) {
			result = new byte[] {(byte) (0x09 | (sourceindex << 4))};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A) {
			sourceindex = getRegisterRHIndex(sourceparameter);
			if (sourceindex != -1) {
				result = new byte[] {(byte) (0x80 | sourceindex)};
			} else if (sourceparameter instanceof ExpressionParameter) {
				result = new byte[] {(byte) 0xc6, (byte) ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit)};
			} else if (sourceparameter instanceof IndexedAddressingParameter) {
				IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) sourceparameter;
				if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
					result = new byte[] {(byte) 0xdd, (byte) 0x86, (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
				} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
					result = new byte[] {(byte) 0xfd, (byte) 0x86, (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
				}
			}
		}
		if (result == null && targetparameter instanceof RegisterPairParameter) {
			RegisterPair registerpair = ((RegisterPairParameter)targetparameter).getRegisterPair();
			if (registerpair == RegisterPair.IX &&
					(sourceindex = getRegisterPPIndex(sourceparameter)) != -1) {
				result = new byte[] {(byte) 0xdd, (byte) (0x09 | (sourceindex << 4))};
			} else if (registerpair == RegisterPair.IY &&
					(sourceindex = getRegisterRRIndex(sourceparameter)) != -1) {
				result = new byte[] {(byte) 0xfd, (byte) (0x09 | (sourceindex << 4))};
			}
		}
		
		return result;
	}

}
