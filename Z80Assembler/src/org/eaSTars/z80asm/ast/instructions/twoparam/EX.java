package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;

public class EX extends TwoParameterInstruction {

	public EX() {
	}
	
	public EX(Parameter target, Parameter source) {
		setTarget(target);
		setSource(source);
	}
	
	@Override
	public String getMnemonic() {
		return "EX";
	}
	
	@Override
	public byte[] getOpcode() {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();
		
		if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.AF &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.AFMarked) {
			result = new byte[] {0x08};
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.DE &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = new byte[] {(byte) 0xeb};
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.SP) {
			if (sourceparameter instanceof RegisterPairParameter) {
				RegisterPair registerPair = ((RegisterPairParameter) sourceparameter).getRegisterPair();
				if (registerPair == RegisterPair.HL) {
					result = new byte[] {(byte) 0xe3};
				} else if (registerPair == RegisterPair.IX) {
					result = new byte[] {(byte) 0xdd, (byte) 0xe3};
				} else if (registerPair == RegisterPair.IY) {
					result = new byte[] {(byte) 0xfd, (byte) 0xe3};
				}
			}
		}
		
		return result;
	}

}
