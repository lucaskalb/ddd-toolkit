package dev.lucaskalb.ddd;

import java.util.Optional;
import java.util.function.Function;

public final class ValidationResult {

  private final boolean success;
  private final String reason;

  public static ValidationResult success() {
    return new ValidationResult(true, null);
  }

  public static ValidationResult failure(String reason) {
    Arguments.checkIfIsEmpty(reason, "reason");
    return new ValidationResult(false, reason);
  }

  private ValidationResult(boolean success, String reason) {
    this.success = success;
    this.reason = reason;
  }

  public boolean isSuccess() {
    return success;
  }

  public Optional<String> failureReason() {
    return Optional.ofNullable(reason);
  }

  public <X extends Throwable> void throwIfFailure(Function<String, X> function) throws X {
    if (success) return;

    throw function.apply(reason);
  }
}