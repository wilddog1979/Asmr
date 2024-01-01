package org.eastars.z80asm.utilities;

import java.util.function.Function;
import java.util.function.Supplier;

public class Utilities {

  public static <T, R> R defaultNull(T target, Function<T, R> process) {
    return defaultValue(target, process, () -> null);
  }

  public static <T, R> R defaultValue(T target, Function<T, R> process, Supplier<R> defaultValue) {
    if (target != null) {
      return process.apply(target);
    }
    return defaultValue.get();
  }

}
