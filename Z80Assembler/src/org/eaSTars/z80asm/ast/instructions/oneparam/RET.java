package org.eaSTars.z80asm.ast.instructions.oneparam;

import org.eaSTars.z80asm.ast.instructions.OneParameterInstruction;
import org.eaSTars.z80asm.ast.parameter.ConditionParameter;
import org.eaSTars.z80asm.ast.parameter.Parameter;

public class RET extends OneParameterInstruction {

	@Override
	public String getMnemonic() {
		return "RET";
	}
	
	@Override
	public String getAssembly() {
		Parameter parameter = getParameter();
		return String.format("%s%s", getMnemonic(), parameter != null ? parameter.getAssembly() : "");
	}
	
	@Override
	public byte[] getOpcode() {
		ConditionParameter parameter = (ConditionParameter) getParameter();
		
		return parameter == null ?
				new byte[] {(byte) 0xc9} :
					new byte[] {(byte)(0xc0 | (parameter.getCondition().getOpcode() << 3))};
	}

}
