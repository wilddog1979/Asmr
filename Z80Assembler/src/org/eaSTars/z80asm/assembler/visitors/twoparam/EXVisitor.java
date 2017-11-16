package org.eaSTars.z80asm.assembler.visitors.twoparam;

import org.eaSTars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eaSTars.z80asm.ast.instructions.twoparam.EX;
import org.eaSTars.z80asm.ast.parameter.Parameter;
import org.eaSTars.z80asm.ast.parameter.RegisterIndirectAddressing;
import org.eaSTars.z80asm.ast.parameter.RegisterPair;
import org.eaSTars.z80asm.ast.parameter.RegisterPairParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.EXContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExafafmarkedContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExdehlContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExrefsphlContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExrefspixContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ExrefspiyContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.InstructionEXparametersContext;

public class EXVisitor extends TwoParameterInstructionVisitor<EX, EXContext, InstructionEXparametersContext> {

	@Override
	protected EX getInstruction() {
		return new EX();
	}
	
	@Override
	protected InstructionEXparametersContext getInstructionParameters(EXContext ctx) {
		return ctx.instructionEXparameters();
	}
	
	@Override
	protected Parameter getSourceParameter(InstructionEXparametersContext paramctx) {
		Parameter parameter = null;
		
		if (paramctx instanceof ExafafmarkedContext) {
			parameter = new RegisterPairParameter(RegisterPair.AFMarked);
		} else if (paramctx instanceof ExrefsphlContext ||
				paramctx instanceof ExdehlContext) {
			parameter = new RegisterPairParameter(RegisterPair.HL);
		} else if (paramctx instanceof ExrefspixContext) {
			parameter = new RegisterPairParameter(RegisterPair.IX);
		} else if (paramctx instanceof ExrefspiyContext) {
			parameter = new RegisterPairParameter(RegisterPair.IY);
		}
		
		return parameter;
	}
	
	@Override
	protected Parameter getTargetParameter(InstructionEXparametersContext paramctx) {
		Parameter parameter = null;
		
		if (paramctx instanceof ExafafmarkedContext) {
			parameter = new RegisterPairParameter(RegisterPair.AF);
		} else if (paramctx instanceof ExrefsphlContext ||
				paramctx instanceof ExrefspixContext ||
				paramctx instanceof ExrefspiyContext) {
			parameter = new RegisterIndirectAddressing(RegisterPair.SP);
		} else if (paramctx instanceof ExdehlContext) {
			parameter = new RegisterPairParameter(RegisterPair.DE);
		}
		
		return parameter;
	}
	
}
