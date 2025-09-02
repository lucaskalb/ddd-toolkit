package dev.lucaskalb.ddd.spec;

/**
 * Interface representing the Specification pattern for encapsulating business rules.
 * <p>
 * The Specification pattern allows business rules to be recombined by chaining business 
 * logic in a boolean fashion. This interface provides the foundation for creating 
 * composable specifications that can be combined using logical operations.
 * </p>
 * <p>
 * Specifications are useful for:
 * <ul>
 *   <li>Validation of objects against business rules</li>
 *   <li>Selection/filtering of objects from collections</li>
 *   <li>Building complex queries in a readable and maintainable way</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * Specification<Customer> vipCustomer = customer -> customer.getTotalSpent().compareTo(new BigDecimal("10000")) > 0;
 * Specification<Customer> activeCustomer = customer -> customer.getLastOrderDate().isAfter(LocalDate.now().minusMonths(6));
 * 
 * Specification<Customer> vipAndActive = vipCustomer.and(activeCustomer);
 * 
 * if (vipAndActive.satisfiedBy(customer)) {
 *     // Apply VIP active customer benefits
 * }
 * }</pre>
 * </p>
 * 
 * @param <T> the type of object this specification can evaluate
 * @author Lucas Kalb
 * @since 0.0.1
 */
public interface Specification<T> {

  /**
   * Checks whether the given candidate satisfies this specification.
   * 
   * @param candidate the object to evaluate against this specification
   * @return true if the candidate satisfies this specification, false otherwise
   */
  boolean satisfiedBy(T candidate);

  /**
   * Creates a new specification that represents the logical AND of this specification and another.
   * <p>
   * The resulting specification is satisfied only when both this specification and the other 
   * specification are satisfied by the same candidate.
   * </p>
   * 
   * @param other the other specification to combine with this one
   * @return a new specification representing the logical AND operation
   * @throws IllegalArgumentException if other is null
   */
  default Specification<T> and(Specification<T> other) {
    return new ConjunctionSpecification<>(this, other);
  }

  /**
   * Creates a new specification that represents the logical OR of this specification and another.
   * <p>
   * The resulting specification is satisfied when either this specification or the other 
   * specification (or both) are satisfied by the candidate.
   * </p>
   * 
   * @param other the other specification to combine with this one
   * @return a new specification representing the logical OR operation
   * @throws IllegalArgumentException if other is null
   */
  default Specification<T> or(Specification<T> other) {
    return new DisjunctionSpecification<>(this, other);
  }

  /**
   * Creates a new specification that represents the logical NOT of this specification.
   * <p>
   * The resulting specification is satisfied when this specification is not satisfied 
   * by the candidate.
   * </p>
   * 
   * @return a new specification representing the logical NOT operation
   */
  default Specification<T> not() {
    return new NegationSpecification<>(this);
  }
}