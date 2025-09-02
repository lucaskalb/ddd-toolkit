package dev.lucaskalb.ddd.spec;

/**
 * A specification that represents the logical NOT operation of another
 * specification.
 * <p>
 * This specification is satisfied when the wrapped specification is not
 * satisfied by
 * the candidate object. It provides a way to invert the logic of existing
 * business rules.
 * </p>
 * <p>
 * This class is package-private and is typically created through the
 * {@link Specification#not()}
 * method rather than being instantiated directly.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>{@code
 * Specification<Product> expiredProduct = product -> product.getExpirationDate().isBefore(LocalDate.now());
 * 
 * // This creates a NegationSpecification internally
 * Specification<Product> freshProduct = expiredProduct.not();
 * }</pre>
 * </p>
 * 
 * @param <T> the type of object this specification can evaluate
 * @author Lucas Kalb
 * @since 0.0.1
 */
final class NegationSpecification<T> implements Specification<T> {

  private final Specification<T> specification;

  /**
   * Creates a new negation specification that inverts the given specification.
   * 
   * @param specification the specification to negate, must not be null
   * @throws IllegalArgumentException if specification is null
   */
  NegationSpecification(Specification<T> specification) {
    if (specification == null)
      throw new IllegalArgumentException("spec cannot be null");
    this.specification = specification;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns true if the wrapped specification is not satisfied by the candidate,
   * and false if the wrapped specification is satisfied.
   * </p>
   */
  @Override
  public boolean satisfiedBy(T candidate) {
    return !specification.satisfiedBy(candidate);
  }
}
