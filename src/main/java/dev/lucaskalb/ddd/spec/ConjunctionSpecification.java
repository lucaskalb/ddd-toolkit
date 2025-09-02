package dev.lucaskalb.ddd.spec;

import java.util.List;

/**
 * A specification that represents the logical AND operation between multiple
 * specifications.
 * <p>
 * This specification is satisfied only when all the constituent specifications
 * are satisfied
 * by the candidate object. It provides a way to combine multiple business rules
 * that must
 * all be true for the overall condition to be met.
 * </p>
 * <p>
 * This class is package-private and is typically created through the
 * {@link Specification#and(Specification)}
 * method rather than being instantiated directly.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>{@code
 * Specification<Product> inStock = product -> product.getQuantity() > 0;
 * Specification<Product> published = product -> product.isPublished();
 * 
 * // This creates a ConjunctionSpecification internally
 * Specification<Product> availableProduct = inStock.and(published);
 * }</pre>
 * </p>
 * 
 * @param <T> the type of object this specification can evaluate
 * @author Lucas Kalb
 * @since 0.0.1
 */
final class ConjunctionSpecification<T> implements Specification<T> {

  private final List<Specification<T>> specifications;

  /**
   * Creates a new conjunction specification from the given specifications.
   * <p>
   * All provided specifications must be satisfied for this conjunction to be
   * satisfied.
   * </p>
   * 
   * @param specs the specifications to combine with logical AND, must not be null
   *              or empty
   */
  @SafeVarargs
  ConjunctionSpecification(Specification<T>... specs) {
    this.specifications = List.of(specs);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns true only if all constituent specifications are satisfied by the
   * candidate.
   * Uses short-circuit evaluation, stopping at the first specification that is
   * not satisfied.
   * </p>
   */
  @Override
  public boolean satisfiedBy(T candidate) {
    return specifications.stream().allMatch(spec -> (spec.satisfiedBy(candidate)));
  }
}
