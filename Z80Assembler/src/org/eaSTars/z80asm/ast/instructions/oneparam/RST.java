package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RST extends OneParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RST";
	}
	
	@Override
	public byte[] getOpcode() {
		byte[] result = null;
		
		Parameter parameter = getParameter();
		if (parameter instanceof ConstantValueParameter) {
			ConstantValueParameter constantValeParameter = (ConstantValueParameter) parameter;
			int value = Integer.parseInt(constantValeParameter.getValue(), 16);
			if (value == 0x00 || value == 0x08 || value == 0x10 || value == 0x18 ||
					value == 0x20 || value == 0x28 || value == 0x30 || value == 0x38) {
				result = new byte[] {(byte) (0xc7 | value)};
			}
		}
		
		return result;
	}

}
