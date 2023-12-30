package org.eastars.z80asm.assembler.visitors;

import org.eastars.z80asm.ast.parameter.Parameter;
import org.eastars.z80asm.ast.parameter.Register;
import org.eastars.z80asm.ast.parameter.RegisterParameter;
import org.eastars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eastars.z80asm.parser.Z80AssemblerParser.RegistersContext;

public class RegistersVisitor extends Z80AssemblerBaseVisitor<Parameter> {

  @Override
  public Parameter visitRegisters(RegistersContext ctx) {
    Parameter parameter = null;

    if (ctx.B != null) {
      parameter = new RegisterParameter(Register.B);
    } else if (ctx.C != null) {
      parameter = new RegisterParameter(Register.C);
    } else if (ctx.D != null) {
      parameter = new RegisterParameter(Register.D);
    } else if (ctx.E != null) {
      parameter = new RegisterParameter(Register.E);
    } else if (ctx.H != null) {
      parameter = new RegisterParameter(Register.H);
    } else if (ctx.L != null) {
      parameter = new RegisterParameter(Register.L);
    } else if (ctx.A != null) {
      parameter = new RegisterParameter(Register.A);
    }

    return parameter;
  }

}
