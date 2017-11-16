package org.eaSTars.z80asm.ast.instructions.twoparam;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
import org.eaSTars.z80asm.ast.parameter.ImmediateAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.IndexedAddressingParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.Register;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.ast.parameter.RegisterParameter;

public class LD extends TwoParameterInstruction {

	@Override
	public String getMnemonic() {
		return "LD";
	}
	
	@Override
	public byte[] getOpcode(CompilationUnit compilationUnit) {
		byte[] result = null;
		
		Parameter targetparameter = getTarget();
		Parameter sourceparameter = getSource();

		int registerindex = -1;
		int markedregisterindex = -1;
		if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.BC &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = new byte[] {0x02};
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.DE &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = new byte[] {0x12};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.BC) {
			result = new byte[] {0x0a};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.DE) {
			result = new byte[] {0x1a};
		} else if (targetparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)targetparameter).getRegisterPair() == RegisterPair.HL &&
				(registerindex = getRegisterRIndex(sourceparameter)) != -1) {
			result = new byte[] {(byte) (0x70 | registerindex)};
		} else if ((registerindex = getRegisterRIndex(targetparameter)) != -1 &&
				sourceparameter instanceof RegisterIndirectAddressing &&
				((RegisterIndirectAddressing)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = new byte[] {(byte) (0x46 | (registerindex << 3))};
		} else if ((registerindex = getRegisterRIndex(targetparameter)) != -1 &&
				(markedregisterindex = getMarkedRegisterRIndex(sourceparameter)) != -1) {
			result = new byte[] {(byte) (0x40 | (registerindex << 3) | markedregisterindex)};
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.SP &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			result = new byte[] {(byte) 0xf9};
		} else if ((registerindex = getRegisterRHIndex(targetparameter)) != -1 &&
				sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			result = new byte[] {(byte) (0x06 | (registerindex << 3)), (byte) (value & 0xff)};
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.SP &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IX) {
			result = new byte[] {(byte) 0xdd, (byte) 0xf9};
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.SP &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IY) {
			result = new byte[] {(byte) 0xfd, (byte) 0xf9};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.I &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = new byte[] {(byte) 0xed, 0x47};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.I) {
			result = new byte[] {(byte) 0xed, 0x57};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.R &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			result = new byte[] {(byte) 0xed, 0x4f};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.R) {
			result = new byte[] {(byte) 0xed, 0x5f};
		} else if ((registerindex = getRegisterSSIndex(targetparameter)) != -1 &&
				sourceparameter instanceof ExpressionParameter) {
			int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
			result = new byte[] {(byte) (0x01 | (registerindex << 4)), (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		} else if (targetparameter instanceof ImmediateAddressingParameter &&
				sourceparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.HL) {
			int value = ((ImmediateAddressingParameter)targetparameter).getValue().getExpressionValue(compilationUnit);
			result = new byte[] {0x22, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		} else if (targetparameter instanceof RegisterPairParameter &&
				((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.HL &&
				sourceparameter instanceof ImmediateAddressingParameter) {
			int value = ((ImmediateAddressingParameter)sourceparameter).getValue().getExpressionValue(compilationUnit);
			result = new byte[] {0x2a, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		} else if (targetparameter instanceof ImmediateAddressingParameter &&
				sourceparameter instanceof RegisterParameter &&
				((RegisterParameter)sourceparameter).getRegister() == Register.A) {
			int value = ((ImmediateAddressingParameter)targetparameter).getValue().getExpressionValue(compilationUnit);
			result = new byte[] {0x32, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		} else if (targetparameter instanceof RegisterParameter &&
				((RegisterParameter)targetparameter).getRegister() == Register.A &&
				sourceparameter instanceof ImmediateAddressingParameter) {
			int value = ((ImmediateAddressingParameter)sourceparameter).getValue().getExpressionValue(compilationUnit);
			result = new byte[] {0x3a, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
		} else if (targetparameter instanceof IndexedAddressingParameter &&
				(registerindex = getRegisterRIndex(sourceparameter)) != -1) {
			IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) targetparameter;
			if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
				result = new byte[] {(byte) 0xdd, (byte) (0x70 | registerindex), (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
			} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
				result = new byte[] {(byte) 0xfd, (byte) (0x70 | registerindex), (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
			}
		}
		
		if (result == null) {
			if (sourceparameter instanceof IndexedAddressingParameter &&
					(registerindex = getRegisterRIndex(targetparameter)) != -1) {
				IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) sourceparameter;
				if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
					result = new byte[] {(byte) 0xdd, (byte) (0x46 | (registerindex << 3)), (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
				} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
					result = new byte[] {(byte) 0xfd, (byte) (0x46 | (registerindex << 3)), (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit)};
				}
			}
		}
		
		if (result == null) {
			if (targetparameter instanceof IndexedAddressingParameter &&
					sourceparameter instanceof ExpressionParameter) {
				IndexedAddressingParameter indexedAddressingParameter = (IndexedAddressingParameter) targetparameter;
				int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
				if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IX) {
					result = new byte[] {(byte) 0xdd, 0x34, (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit), (byte) (value & 0xff)};
				} else if (indexedAddressingParameter.getRegisterPair() == RegisterPair.IY) {
					result = new byte[] {(byte) 0xfd, 0x34, (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationUnit), (byte) (value & 0xff)};
				}
			}
		}
		
		if (result == null) {
			if (targetparameter instanceof ImmediateAddressingParameter &&
					(registerindex = getRegisterSSIndex(sourceparameter)) != -1) {
				int value = ((ImmediateAddressingParameter)targetparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xed, (byte) (0x43 | (registerindex << 4)), (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (sourceparameter instanceof ImmediateAddressingParameter &&
					(registerindex = getRegisterSSIndex(targetparameter)) != -1) {
				int value = ((ImmediateAddressingParameter)sourceparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xed, (byte) (0x4b | (registerindex << 4)), (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (targetparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IX &&
					sourceparameter instanceof ExpressionParameter) {
				int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xdd, 0x21, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (targetparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IY &&
					sourceparameter instanceof ExpressionParameter) {
				int value = ((ExpressionParameter)sourceparameter).getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xfd, 0x21, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (targetparameter instanceof ImmediateAddressingParameter &&
					sourceparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IX) {
				int value = ((ImmediateAddressingParameter)targetparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xdd, 0x22, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (targetparameter instanceof ImmediateAddressingParameter &&
					sourceparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)sourceparameter).getRegisterPair() == RegisterPair.IY) {
				int value = ((ImmediateAddressingParameter)targetparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xfd, 0x22, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (sourceparameter instanceof ImmediateAddressingParameter &&
					targetparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IX) {
				int value = ((ImmediateAddressingParameter)sourceparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xdd, 0x2a, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			} else if (sourceparameter instanceof ImmediateAddressingParameter &&
					targetparameter instanceof RegisterPairParameter &&
					((RegisterPairParameter)targetparameter).getRegisterPair() == RegisterPair.IY) {
				int value = ((ImmediateAddressingParameter)sourceparameter).getValue().getExpressionValue(compilationUnit);
				result = new byte[] {(byte) 0xfd, 0x2a, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
			}
		}
		
		return result;
	}
	
}
