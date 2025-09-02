package dev.lucaskalb.ddd.core;

import java.util.Collection;
import java.util.Map;

/**
 * Utility class for performing common argument validation in domain objects.
 * <p>
 * This class provides static methods for validating method arguments and object
 * state
 * to ensure domain invariants are maintained. It follows the fail-fast
 * principle,
 * throwing {@link IllegalArgumentException} when validation fails.
 * </p>
 * <p>
 * All validation methods accept a message key parameter that should be used for
 * internationalization or consistent error messaging across the application.
 * </p>
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * public class Email extends ValueObject {
 *   private final String value;
 * 
 *   public Email(String value) {
 *     Arguments.checkIfIsEmpty(value, "Email cannot be empty");
 *     this.value = value;
 *   }
 * }
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 */
public final class Arguments {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private Arguments() {
  }

  /**
   * Validates that the given argument is not null.
   * 
   * @param argument   the object to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the argument is null
   */
  public static void checkIfIsNull(Object argument, String messageKey) {
    if (argument == null)
      throw new IllegalArgumentException(messageKey);
  }

  /**
   * Validates that the given map is not null and not empty.
   * 
   * @param argument   the map to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the argument is null or empty
   */
  public static void checkIfIsEmpty(Map<?, ?> argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  /**
   * Validates that the given collection is not null and not empty.
   * 
   * @param argument   the collection to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the argument is null or empty
   */
  public static void checkIfIsEmpty(Collection<?> argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  /**
   * Validates that the given string is not null and not blank (empty or
   * whitespace-only).
   * 
   * @param argument   the string to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the argument is null or blank
   */
  public static void checkIfIsEmpty(String argument, String messageKey) {
    checkIfArgumentIsEmpty(argument, messageKey);
  }

  /**
   * Common validation logic for checking if an argument is empty.
   * <p>
   * This method first checks if the argument is null, then checks if it's empty
   * according to its type-specific definition of emptiness.
   * </p>
   * 
   * @param argument   the object to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the argument is null or empty
   */
  private static void checkIfArgumentIsEmpty(Object argument, String messageKey) {
    checkIfIsNull(argument, messageKey);
    if (isEmpty(argument))
      throw new IllegalArgumentException(messageKey);
  }

  /**
   * Determines if an object is considered empty based on its type.
   * <p>
   * The emptiness criteria are:
   * <ul>
   * <li>String: blank (empty or contains only whitespace)</li>
   * <li>Collection: contains no elements</li>
   * <li>Map: contains no key-value pairs</li>
   * </ul>
   * </p>
   * 
   * @param argument the object to check for emptiness
   * @return true if the object is considered empty, false otherwise
   */
  private static boolean isEmpty(Object argument) {
    if (argument instanceof String valueString && valueString.isBlank())
      return true;
    if (argument instanceof Collection<?> collection && collection.isEmpty())
      return true;
    return argument instanceof Map<?, ?> map && map.isEmpty();
  }

  /**
   * Validates that the given number is not null and not negative.
   * <p>
   * A number is considered negative if its double value is less than 0.
   * </p>
   * 
   * @param number     the number to validate
   * @param messageKey the error message or message key to use if validation fails
   * @throws IllegalArgumentException if the number is null or negative
   */
  public static void checkIfIsNegative(Number number, String messageKey) {
    if (number == null || number.doubleValue() < 0)
      throw new IllegalArgumentException(messageKey);
  }
}
