package org.eastars.z80asm.assembler.converter;

import org.eastars.asm.ast.Instruction;

import java.io.Serial;
import java.util.HashMap;

public class MaskedOpcodeMap<T1 extends Instruction> extends HashMap<OpcodeMask<T1>, Class<? extends T1>> {

  @Serial
  private static final long serialVersionUID = -3275047983128208919L;

  private T1 initialize(Class<? extends T1> clazz, ParameterExtractor<T1> extractor, byte[] values) {
    try {
      T1 result =  clazz.newInstance();

      if (extractor != null) {
        result = extractor.extract(result, values);
      }
      return result;
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
  }

  public T1 getInstruction(byte[] values) {
    return entrySet().stream().filter(e -> e.getKey().mask.length == values.length && checkMask(values, e.getKey()))
        .findFirst().map(e -> initialize(e.getValue(), e.getKey().parameterExtractor, values)).orElse(null);
  }

  private boolean checkMask(byte[] values, OpcodeMask<T1> mask) {
    boolean result = true;
    for (int i = 0; i < values.length; ++i) {
      if (!(result = (byte) (values[i] & mask.mask[i]) == mask.value[i])) {
        break;
      }
    }
    return result;
  }

}
