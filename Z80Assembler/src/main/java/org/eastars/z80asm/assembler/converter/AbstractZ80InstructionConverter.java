package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.assember.AbstractAssemblyConverter;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.assember.PushbackInputStream;
import org.eastars.asm.ast.Instruction;
import org.eastars.z80asm.ast.expression.ConstantValueExpression;
import org.eastars.z80asm.ast.parameter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractZ80InstructionConverter<T extends Instruction> extends AbstractAssemblyConverter<T> {

  private static final List<RegisterPair> TABLE_PP = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.IX,
      RegisterPair.SP);

  private static final List<RegisterPair> TABLE_QQ = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.HL,
      RegisterPair.AF);

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

  private static final List<RegisterPair> TABLE_SS = Arrays.asList(
      RegisterPair.BC,
      RegisterPair.DE,
      RegisterPair.HL,
      RegisterPair.SP);

  private static int tableLookup(List<RegisterPair> map, Parameter parameter) {
    int result = -1;

    if (parameter instanceof RegisterPairParameter registerPairParameter) {
      result = map.indexOf(registerPairParameter.getRegisterPair());
    }

    return result;
  }

  protected static int getRegisterSSIndex(Parameter parameter) {
    return tableLookup(TABLE_SS, parameter);
  }

  protected static int getRegisterPPIndex(Parameter parameter) {
    return tableLookup(TABLE_PP, parameter);
  }

  protected static int getRegisterQQIndex(Parameter parameter) {
    return tableLookup(TABLE_QQ, parameter);
  }

  protected static int getRegisterRRIndex(Parameter parameter) {
    return tableLookup(TABLE_RR, parameter);
  }

  protected static int getRegisterRIndex(Parameter parameter) {
    int result = -1;

    if (parameter instanceof RegisterParameter registerParameter) {
      result = TABLE_R.indexOf(registerParameter.getRegister());
    }

    return result;
  }

  protected static int getMarkedRegisterRIndex(Parameter parameter) {
    int result = -1;

    if (parameter instanceof RegisterParameter registerParameter) {
      result = TABLE_MARKED_R.indexOf(registerParameter.getRegister());
    }

    return result;
  }

  protected static int getRegisterRHIndex(Parameter parameter) {
    int result = getRegisterRIndex(parameter);

    if (result == -1
        && parameter instanceof RegisterIndirectAddressingParameter
        && ((RegisterIndirectAddressingParameter) parameter).getRegisterPair() == RegisterPair.HL) {
      result = 6;
    }

    return result;
  }


  private static Parameter genericReverseRegister(List<RegisterPair> registerPairs, int index) {
    Parameter result = null;

    if (index >= 0 && index < 4) {
      result = new RegisterPairParameter(registerPairs.get(index));
    }

    return result;
  }

  protected static Parameter reverseRegisterSS(int index) {
    return genericReverseRegister(TABLE_SS, index);
  }

  protected static Parameter reverseRegisterPP(int index) {
    return genericReverseRegister(TABLE_PP, index);
  }

  protected static Parameter reverseRegisterQQ(int index) {
    return genericReverseRegister(TABLE_QQ, index);
  }

  protected static Parameter reverseRegisterRR(int index) {
    return genericReverseRegister(TABLE_RR, index);
  }

  protected static Parameter reverseRegisterRH(int index) {
    Parameter result = null;

    if (index == 6) {
      result = new RegisterIndirectAddressingParameter(RegisterPair.HL);
    } else {
      result = reverseRegisterR(index);
    }

    return result;
  }

  protected static RegisterParameter reverseRegisterR(int index) {
    RegisterParameter result = null;

    if (index >= 0 && index < 8) {
      result = new RegisterParameter(TABLE_R.get(index));
    }

    return result;
  }

  protected static RegisterParameter reverseRegisterRMarked(int index) {
    RegisterParameter result = null;

    if (index >= 0 && index < 8) {
      result = new RegisterParameter(TABLE_MARKED_R.get(index));
    }

    return result;
  }

  protected static Parameter reverseRegisterPPRR(boolean ix, int index) {
    return ix ? reverseRegisterPP(index) : reverseRegisterRR(index);
  }

  protected static Parameter reverseIndexedAddressing(boolean ix, int displacement) {
    return new IndexedAddressingParameter(ix ? RegisterPair.IX : RegisterPair.IY, reverseImmediate8(displacement));
  }

  protected static ExpressionParameter reverseImmediate8(int value) {
    return new ExpressionParameter(
        new ConstantValueExpression(new ConstantValueParameter(value & 0xff)),
        8);
  }

  protected static Parameter reverseImmediate16(byte hi, byte lo) {
    return new ExpressionParameter(
        new ConstantValueExpression(new ConstantValueParameter(((hi << 8) & 0xff00) | (lo & 0x00ff))),
        16);
  }

  protected static Parameter reverseCondition(int index) {
    Parameter result = null;

    for (Condition condition : Condition.values()) {
      if (condition.getOpcode() == index) {
        result = new ConditionParameter(condition);
        break;
      }
    }

    return result;
  }

  protected static Parameter reverseRSTValue(int value) {
    return new ConstantValueParameter(String.format("%02x", value));
  }

  protected static Parameter reverseIXIY(boolean ix) {
    return new RegisterPairParameter(ix ? RegisterPair.IX : RegisterPair.IY);
  }

  protected static byte[] generateIndexedAddressing(
      CompilationContext compilationContext,
      IndexedAddressingParameter indexedAddressingParameter,
      byte[] bytes) {
    byte[] result = null;

    RegisterPair registerPair = indexedAddressingParameter.getRegisterPair();
    if (registerPair == RegisterPair.IX) {
      result = Arrays.copyOf(bytes, bytes.length);
      result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationContext);
    } else if (registerPair == RegisterPair.IY) {
      result = Arrays.copyOf(bytes, bytes.length);
      result[0] |= 0x20;
      result[2] = (byte) indexedAddressingParameter.getDisplacement().getExpressionValue(compilationContext);
    }

    return result;
  }

  protected static byte[] generateIndexRegisters(RegisterPair parameter, byte[] bytes) {
    byte[] result = null;

    if (parameter == RegisterPair.IX) {
      result = Arrays.copyOf(bytes, bytes.length);
    } else if (parameter == RegisterPair.IY) {
      result = Arrays.copyOf(bytes, bytes.length);
      result[0] |= 0x20;
    }

    return result;
  }

  protected static byte[] selectMask(MaskedOpcode<? extends Instruction> mask) {
    return Arrays.copyOf(mask.getValue(), mask.getValue().length);
  }

  protected abstract MaskedOpcodeMap<T> getReverse(int index);

  protected T convertRecursive(PushbackInputStream pushbackInputStream, byte[] buffer) throws IOException {
    T result = null;

    int current = pushbackInputStream.read();
    if (current != -1) {
      byte[] currentBuffer = Arrays.copyOf(buffer, buffer.length + 1);
      currentBuffer[buffer.length] = (byte) current;
      result = Optional.ofNullable(getReverse(currentBuffer.length)).map(
          r -> r.getInstruction(currentBuffer)).orElse(null);
      if (result == null && (result = convertRecursive(pushbackInputStream, currentBuffer)) == null) {
        pushbackInputStream.unread((byte) current);
      }
    }

    return result;
  }

  @Override
  public T convert(PushbackInputStream pushbackInputStream) throws IOException {
    return convertRecursive(pushbackInputStream, new byte[]{});
  }

  protected static byte[] generateWithImmediateValue(
      CompilationContext compilationContext,
      byte[] result,
      Parameter parameter,
      int idx) {
    return generateWithExpressionValue(
        compilationContext, result, ((ImmediateAddressingParameter) parameter).getValue(), idx);
  }

  protected static byte[] generateWithExpressionValue(
      CompilationContext compilationContext,
      byte[] result,
      ExpressionParameter parameter,
      int idx) {
    int value = parameter.getExpressionValue(compilationContext);
    result[idx] = (byte) (value & 0xff);
    result[idx + 1] = (byte) ((value >> 8) & 0xff);
    return result;
  }

}
