package dev.lucaskalb.ddd;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Abstract base class for implementing Value Objects in Domain-Driven Design.
 * <p>
 * Value Objects are immutable objects that are distinguished by their attributes rather than
 * their identity. They represent descriptive aspects of the domain with no conceptual identity.
 * This class provides proper equality semantics based on the object's values rather than reference.
 * </p>
 * <p>
 * Subclasses must implement {@link #getEqualityComponents()} to define which attributes
 * contribute to equality and hash code computation. All attributes that affect equality
 * should be included in the returned array.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * public class Money extends ValueObject {
 *     private final BigDecimal amount;
 *     private final Currency currency;
 *     
 *     public Money(BigDecimal amount, Currency currency) {
 *         this.amount = amount;
 *         this.currency = currency;
 *     }
 *     
 *     @Override
 *     protected Object[] getEqualityComponents() {
 *         return new Object[]{amount, currency};
 *     }
 * }
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 */
public abstract class ValueObject implements Serializable {

  @Serial 
  private static final long serialVersionUID = 1L;

  /**
   * Returns the components that define equality for this value object.
   * <p>
   * Subclasses must implement this method to return an array containing all
   * attributes that should be considered when determining if two value objects
   * are equal. The order of elements in the array matters for equality comparison.
   * </p>
   * <p>
   * All attributes that can affect the semantic equality of the value object
   * should be included. Attributes that are derived or computed from other
   * included attributes can be omitted.
   * </p>
   * 
   * @return an array of objects representing the equality components, never null
   */
  protected abstract Object[] getEqualityComponents();

  /**
   * Determines equality based on the value object's components.
   * <p>
   * Two value objects are considered equal if they are of the same type and
   * all their equality components are equal. This implements proper value
   * semantics where objects with the same values are interchangeable.
   * </p>
   * 
   * @param obj the object to compare with
   * @return true if both objects have the same type and equal components, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    var other = (ValueObject) obj;

    return Arrays.equals(getEqualityComponents(), other.getEqualityComponents());
  }

  /**
   * Returns a hash code based on the value object's components.
   * <p>
   * The hash code is computed from all equality components to ensure that
   * equal value objects have equal hash codes, as required by the contract
   * of {@link Object#hashCode()}.
   * </p>
   * 
   * @return a hash code value for this value object
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(getEqualityComponents());
  }
}