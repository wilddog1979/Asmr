package org.eastars.z80asm.ast;

import org.eastars.asm.ast.Instruction;
import org.eastars.z80asm.ast.parameter.*;

import java.util.Arrays;
import java.util.List;

public abstract class Z80Instruction implements Instruction {

  private static final List<RegisterPair> TABLE_SS = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.HL,
      RegisterPair.SP);
  
  private static final List<RegisterPair> TABLE_QQ = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.HL,
      RegisterPair.AF);
  
  private static final List<RegisterPair> TABLE_PP = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.IX,
      RegisterPair.SP);
  
  private static final List<RegisterPair> TABLE_RR = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.IY,
      RegisterPair.SP);
  
  private static final List<Register> TABLE_R = Arrays.asList(
      Register.B,
      Register.C,
      Register.D,
      Register.E,
      Register.H,
      Register.L,
      null,
      Register.A);
  
  private static final List<Register> TABLE_MARKED_R = Arrays.asList(
      Register.BMarked,
      Register.CMarked,
      Register.DMarked,
      Register.EMarked,
      Register.HMarked,
      Register.LMarked,
      null,
      Register.AMarked);
  
  protected int getRegisterSSIndex(Parameter parameter) {
    return tableLookup(TABLE_SS, parameter);
  }
  
  protected int getRegisterQQIndex(Parameter parameter) {
    return tableLookup(TABLE_QQ, parameter);
  }
  
  protected int getRegisterPPIndex(Parameter parameter) {
    return tableLookup(TABLE_PP, parameter);
  }
  
  protected int getRegisterRRIndex(Parameter parameter) {
    return tableLookup(TABLE_RR, parameter);
  }
  
  private int tableLookup(List<RegisterPair> map, Parameter parameter) {
    int result = -1;
    
    if (parameter instanceof RegisterPairParameter registerPairParameter) {
      result = map.indexOf(registerPairParameter.getRegisterPair());
    }
    
    return result;
  }
  
  protected int getRegisterRIndex(Parameter parameter) {
    int result = -1;
    
    if (parameter instanceof RegisterParameter registerParameter) {
      result = TABLE_R.indexOf(registerParameter.getRegister());
    }
    
    return result;
  }
  
  protected int getMarkedRegisterRIndex(Parameter parameter) {
    int result = -1;
    
    if (parameter instanceof RegisterParameter registerParameter) {
      result = TABLE_MARKED_R.indexOf(registerParameter.getRegister());
    }
    
    return result;
  }
  
  protected int getRegisterRHIndex(Parameter parameter) {
    int result = getRegisterRIndex(parameter);
    
    if (result == -1
        && parameter instanceof RegisterIndirectAddressingParameter
        && ((RegisterIndirectAddressingParameter) parameter).getRegisterPair() == RegisterPair.HL) {
      result = 6;
    }
    
    return result;
  }
  
  @Override
  public String toString() {
    return getAssembly();
  }
  
}
