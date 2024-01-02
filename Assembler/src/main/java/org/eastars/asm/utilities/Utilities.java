package org.eastars.asm.utilities;

import java.util.function.Consumer;

public class Utilities {

  public static <V> boolean ifNotNull(V value, Consumer<V> consumer) {
    if (value != null) {
      consumer.accept(value);
      return true;
    }
    return false;
  }

}
