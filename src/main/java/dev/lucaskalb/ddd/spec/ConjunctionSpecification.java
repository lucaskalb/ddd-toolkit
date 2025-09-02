package dev.lucaskalb.ddd.spec;

import java.util.List;

final class ConjunctionSpecification<T> implements Specification<T> {

  private final List<Specification<T>> specifications;

  @SafeVarargs
  ConjunctionSpecification(Specification<T>... specs) {
    this.specifications = List.of(specs);
  }

  @Override
  public boolean satisfiedBy(T candidate) {
    return specifications.stream().allMatch(spec -> (spec.satisfiedBy(candidate)));
  }
}