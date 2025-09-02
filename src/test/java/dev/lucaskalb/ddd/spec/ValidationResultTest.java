package dev.lucaskalb.ddd.spec;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValidationResultTest {

  @Test
  void success() {
    var result = ValidationResult.success();

    assertThat(result.isSuccess()).isTrue();
  }

  @Test
  void failure() {
    var result = ValidationResult.failure("an error");

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.failureReason()).contains("an error");
  }

  @Test
  void throwIfFailure() {
    var result = ValidationResult.failure("an error");

    var exception = assertThrows(RuntimeException.class, () -> result.throwIfFailure(RuntimeException::new));
    assertThat(exception.getMessage()).isEqualTo("an error");
  }
}
