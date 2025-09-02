package dev.lucaskalb.ddd.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class ArgumentsTest {

  @Test
  void checkIfIsNull() {
    assertThrows(
        IllegalArgumentException.class, () -> Arguments.checkIfIsNull(null, "argument cannot be null"));
  }

  @Test
  void checkIfIsEmptyWhenIsMap() {
    var label = "argument";
    var argument = new HashMap<>();

    assertThrows(IllegalArgumentException.class, () -> Arguments.checkIfIsEmpty(argument, label));
  }

  @Test
  void checkIfIsEmptyWhenIsList() {
    var label = "argument";
    var argument = new ArrayList<String>();

    assertThrows(IllegalArgumentException.class, () -> Arguments.checkIfIsEmpty(argument, label));
  }

  @Test
  void checkIfIsEmptyWhenIsString() {
    var label = "argument";
    var argument = "     ";

    assertThrows(IllegalArgumentException.class, () -> Arguments.checkIfIsEmpty(argument, label));
  }
}
