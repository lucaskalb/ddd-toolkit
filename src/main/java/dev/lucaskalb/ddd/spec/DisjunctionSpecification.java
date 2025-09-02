package dev.lucaskalb.ddd.spec;

import java.util.List;

/**
 * A specification that represents the logical OR operation between multiple specifications.
 * <p>
 * This specification is satisfied when at least one of the constituent specifications is
 * satisfied by the candidate object. It provides a way to combine multiple business rules
 * where any one condition being true makes the overall condition true.
 * </p>
 * <p>
 * This class is package-private and is typically created through the {@link Specification#or(Specification)}
 * method rather than being instantiated directly.
 * </p>
 * <p>
 * Example:
 * <pre>{@code
 * Specification<Customer> vipCustomer = customer -> customer.isVip();
 * Specification<Customer> loyalCustomer = customer -> customer.getYearsActive() > 5;
 * 
 * // This creates a DisjunctionSpecification internally
 * Specification<Customer> preferredCustomer = vipCustomer.or(loyalCustomer);
 * }</pre>
 * </p>
 * 
 * @param <T> the type of object this specification can evaluate
 * @author Lucas Kalb
 * @since 0.0.1
 */
final class DisjunctionSpecification<T> implements Specification<T> {

  private final List<Specification<T>> specifications;

  /**
   * Creates a new disjunction specification from the given specifications.
   * <p>
   * At least one of the provided specifications must be satisfied for this 
   * disjunction to be satisfied.
   * </p>
   * 
   * @param specifications the specifications to combine with logical OR, must not be null or empty
   */
  @SafeVarargs
  DisjunctionSpecification(Specification<T>... specifications) {
    this.specifications = List.of(specifications);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns true if at least one of the constituent specifications is satisfied by the candidate.
   * Uses short-circuit evaluation, returning true as soon as the first satisfied specification is found.
   * </p>
   */
  @Override
  public boolean satisfiedBy(T candidate) {
    return specifications.stream().anyMatch(spec -> spec.satisfiedBy(candidate));
  }
}