package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.assembler.visitors.TwoParameterInstructionVisitor;
import org.eastars.z80asm.ast.instructions.twoparam.JR;
import org.eastars.z80asm.ast.parameter.Condition;
import org.eastars.z80asm.ast.parameter.ConditionParameter;
import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionJRparametersContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.JRContext;

public class JRVisitor extends TwoParameterInstructionVisitor<JR, JRContext, InstructionJRparametersContext> {

  @Override
  protected JR getInstruction() {
    return new JR();
  }
  
  @Override
  protected InstructionJRparametersContext getInstructionParameters(JRContext ctx) {
    return ctx.instructionJRparameters();
  }
  
  @Override
  protected Parameter getSourceParameter(InstructionJRparametersContext paramCtx) {
    return getExpression(paramCtx.hex8bits()).orElseGet(() -> null);
  }

  @Override
  protected Parameter getTargetParameter(InstructionJRparametersContext paramCtx) {
    Parameter parameter = null;
    
    if (paramCtx.NZ != null) {
      parameter = new ConditionParameter(Condition.NZ);
    } else if (paramCtx.Z != null) {
      parameter = new ConditionParameter(Condition.Z);
    } else if (paramCtx.NC != null) {
      parameter = new ConditionParameter(Condition.NC);
    } else if (paramCtx.C != null) {
      parameter = new ConditionParameter(Condition.C);
    }
    
    return parameter;
  }
}
