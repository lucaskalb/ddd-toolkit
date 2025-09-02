package dev.lucaskalb.ddd;

import java.util.Optional;
import java.util.function.Function;

/**
 * Represents the result of a validation operation that can either succeed or fail.
 * <p>
 * This class follows the Result pattern to handle validation outcomes in a functional
 * way without throwing exceptions immediately. It allows for composing validation
 * logic and deferring error handling decisions to the caller.
 * </p>
 * <p>
 * The ValidationResult can be in one of two states:
 * <ul>
 *   <li><strong>Success</strong>: The validation passed, no failure reason is present</li>
 *   <li><strong>Failure</strong>: The validation failed, with an associated failure reason</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * public ValidationResult validateEmail(String email) {
 *     if (email == null || email.isBlank()) {
 *         return ValidationResult.failure("Email cannot be empty");
 *     }
 *     if (!email.contains("@")) {
 *         return ValidationResult.failure("Email must contain @ symbol");
 *     }
 *     return ValidationResult.success();
 * }
 * 
 * ValidationResult result = validateEmail(userInput);
 * if (result.isSuccess()) {
 *     // proceed with valid email
 * } else {
 *     // handle validation failure
 *     result.throwIfFailure(ValidationException::new);
 * }
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 */
public final class ValidationResult {

  private final boolean success;
  private final String reason;

  /**
   * Creates a successful validation result.
   * 
   * @return a ValidationResult representing successful validation
   */
  public static ValidationResult success() {
    return new ValidationResult(true, null);
  }

  /**
   * Creates a failed validation result with the specified reason.
   * 
   * @param reason the reason why validation failed, must not be null or empty
   * @return a ValidationResult representing failed validation
   * @throws IllegalArgumentException if reason is null or empty
   */
  public static ValidationResult failure(String reason) {
    Arguments.checkIfIsEmpty(reason, "reason");
    return new ValidationResult(false, reason);
  }

  /**
   * Private constructor to create a validation result.
   * 
   * @param success whether the validation was successful
   * @param reason the failure reason, should be null for successful results
   */
  private ValidationResult(boolean success, String reason) {
    this.success = success;
    this.reason = reason;
  }

  /**
   * Checks if this validation result represents a successful validation.
   * 
   * @return true if the validation was successful, false otherwise
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * Returns the failure reason if this validation result represents a failure.
   * 
   * @return an Optional containing the failure reason if validation failed,
   *         or an empty Optional if validation was successful
   */
  public Optional<String> failureReason() {
    return Optional.ofNullable(reason);
  }

  /**
   * Throws an exception created by the provided function if this validation result represents a failure.
   * <p>
   * This method allows for flexible error handling by letting the caller decide what type of
   * exception to throw and how to construct it from the failure reason.
   * </p>
   * <p>
   * If the validation was successful, this method does nothing.
   * </p>
   * 
   * @param <X> the type of exception to throw
   * @param function a function that creates an exception from the failure reason
   * @throws X the exception created by the function if validation failed
   * 
   * <p>
   * Example:
   * <pre>{@code
   * result.throwIfFailure(IllegalArgumentException::new);
   * result.throwIfFailure(reason -> new CustomValidationException(reason));
   * }</pre>
   * </p>
   */
  public <X extends Throwable> void throwIfFailure(Function<String, X> function) throws X {
    if (success) return;

    throw function.apply(reason);
  }
}