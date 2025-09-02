package dev.lucaskalb.ddd.spec;

import java.util.List;

final class DisjunctionSpecification<T> implements Specification<T> {

  private final List<Specification<T>> specifications;

  @SafeVarargs
  DisjunctionSpecification(Specification<T>... specifications) {
    this.specifications = List.of(specifications);
  }

  @Override
  public boolean satisfiedBy(T candidate) {
    return specifications.stream().anyMatch(spec -> spec.satisfiedBy(candidate));
  }
}