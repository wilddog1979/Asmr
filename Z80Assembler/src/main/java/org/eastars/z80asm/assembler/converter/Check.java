package org.eastars.z80asm.assembler.converter;

import org.eastars.z80asm.ast.parameter.*;

public class Check {

  public static boolean checkRegisterParameter(Parameter parameter, Register register) {
    return parameter instanceof RegisterParameter
        && register.equals(((RegisterParameter) parameter).getRegister());
  }

  public static boolean checkRegisterPairParameter(Parameter parameter, RegisterPair registerPair) {
    return parameter instanceof RegisterPairParameter
        && registerPair.equals(((RegisterPairParameter) parameter).getRegisterPair());
  }

  public static boolean checkRegisterPairParameterIXorIY(Parameter parameter) {
    return parameter instanceof RegisterPairParameter
        && (RegisterPair.IX.equals(((RegisterPairParameter) parameter).getRegisterPair())
        || RegisterPair.IY.equals(((RegisterPairParameter) parameter).getRegisterPair()));
  }

  public static boolean checkRegisterIndirectAddressing(Parameter parameter, RegisterPair registerPair) {
    return parameter instanceof RegisterIndirectAddressingParameter
        && registerPair.equals(((RegisterIndirectAddressingParameter) parameter).getRegisterPair());
  }

}
