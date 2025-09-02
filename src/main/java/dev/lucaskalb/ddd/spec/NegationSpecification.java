package dev.lucaskalb.ddd.spec;

class NegationSpecification<T> implements Specification<T> {

  private final Specification<T> specification;

  NegationSpecification(Specification<T> specification) {
    if (specification == null) throw new IllegalArgumentException("spec cannot be null");
    this.specification = specification;
  }

  @Override
  public boolean satisfiedBy(T candidate) {
    return !specification.satisfiedBy(candidate);
  }
}