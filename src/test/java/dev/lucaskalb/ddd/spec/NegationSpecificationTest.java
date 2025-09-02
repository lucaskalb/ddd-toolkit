package dev.lucaskalb.ddd.spec;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NegationSpecificationTest {

  @Test
  void conjunctionSpecification() {
    var doesLikeToEatFruitSpecification = new DoesLikeToEatFruitSpecification();
    var isSatisfied = doesLikeToEatFruitSpecification
        .and(new BananaSpecification())
        .satisfiedBy("I like to eat banana");

    assertTrue(isSatisfied);
  }

  @Test
  void negationSpecification() {
    var doesLikeToEatFruitSpecification = new DoesLikeToEatFruitSpecification();
    var isSatisfied = doesLikeToEatFruitSpecification
        .and(new BananaSpecification())
        .and(new SandSpecification().not())
        .satisfiedBy("I like to eat sand");

    assertFalse(isSatisfied);
  }

  @Test
  void disjunctionSpecification() {
    var doesLikeToEatFruitSpecification = new DoesLikeToEatFruitSpecification();
    var isSatisfied = doesLikeToEatFruitSpecification
        .and(new BananaSpecification().or(new PineappleSpecification()))
        .and(new SandSpecification().not())
        .satisfiedBy("I like to eat pineapple");

    assertTrue(isSatisfied);
  }
}

class DoesLikeToEatFruitSpecification implements Specification<String> {

  @Override
  public boolean satisfiedBy(String candidate) {
    return candidate != null && candidate.startsWith("I like to eat");
  }
}

class BananaSpecification implements Specification<String> {
  @Override
  public boolean satisfiedBy(String candidate) {
    return candidate != null && candidate.contains("banana");
  }
}

class PineappleSpecification implements Specification<String> {
  @Override
  public boolean satisfiedBy(String candidate) {
    return candidate != null && candidate.contains("pineapple");
  }
}

class SandSpecification implements Specification<String> {
  @Override
  public boolean satisfiedBy(String candidate) {
    return candidate != null && candidate.contains("sand");
  }
}
