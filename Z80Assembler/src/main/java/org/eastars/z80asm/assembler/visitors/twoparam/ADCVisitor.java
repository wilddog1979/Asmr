package org.eastars.z80asm.assembler.visitors.twoparam;

import org.eastars.z80asm.ast.instructions.twoparam.ADC;
import org.eastars.z80asm.parser.Z80AssemblerParser.ADCContext;
import org.eastars.z80asm.parser.Z80AssemblerParser.InstructionADCSBCparametersContext;

public class ADCVisitor extends ADCSBCVisitor<ADC, ADCContext> {

  @Override
  protected ADC getInstruction() {
    return new ADC();
  }
  
  @Override
  protected InstructionADCSBCparametersContext getInstructionParameters(ADCContext ctx) {
    return ctx.instructionADCSBCparameters();
  }
  
}
