package dev.lucaskalb.ddd;

import java.util.Collection;
import java.util.Map;

public class Arguments {

  private Arguments() {}

  public static void checkIfIsNull(Object argument, String messageKey) {
    if (argument == null) throw new IllegalArgumentException(messageKey);
  }

  public static void checkIfIsEmpty(Map<?, ?> argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  public static void checkIfIsEmpty(Collection<?> argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  public static void checkIfIsEmpty(String argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  private static void checkIfArgumentIsEmpty(Object argument, String messageKey) {
    checkIfIsNull(argument, messageKey);
    if (isEmpty(argument)) throw new IllegalArgumentException(messageKey);
  }

  private static boolean isEmpty(Object argument) {
    if (argument instanceof String valueString && valueString.isBlank()) return true;
    if (argument instanceof Collection<?> collection && collection.isEmpty()) return true;
    return argument instanceof Map<?, ?> map && map.isEmpty();
  }

  public static void checkIfIsNegative(Number number, String messageKey) {
    if (number == null || number.doubleValue() < 0) throw new IllegalArgumentException(messageKey);
  }
}