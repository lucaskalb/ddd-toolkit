package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Arguments;
import java.io.Serializable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract base class for domain entities that are identified by a strongly-typed ID.
 * <p>
 * This class implements the Entity pattern from Domain-Driven Design, where entities are
 * distinguished by their identity rather than their attributes. It provides a foundation
 * for all domain entities that need persistent identity and proper equality semantics.
 * </p>
 * <p>
 * The class uses JPA annotations for persistence mapping and ensures that entity equality
 * is based solely on the entity's ID, following DDD best practices.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * @Entity
 * public class User extends IdentityDomain<UserId> {
 *     // domain logic here
 * }
 * }</pre>
 * </p>
 * 
 * @param <T> the type of the entity identifier, must extend EntityId
 * @author Lucas Kalb
 * @since 0.0.1
 */
@MappedSuperclass
public abstract class IdentityDomain<T extends EntityId> implements Serializable {

  /**
   * The unique identifier for this domain entity.
   */
  @EmbeddedId
  @AttributeOverride(name = "value", column = @Column(name = "id"))
  private T id;

  /**
   * Sets the unique identifier for this entity.
   * <p>
   * This method is protected to allow subclasses to set the ID during construction
   * while preventing external modification of the entity's identity.
   * </p>
   * 
   * @param id the entity identifier, must not be null
   * @throws IllegalArgumentException if id is null
   */
  protected void setId(T id) {
    Arguments.checkIfIsNull(id, "id.cannot_be_null");
    this.id = id;
  }

  /**
   * Returns the unique identifier for this entity.
   * 
   * @return the entity identifier, may be null if not yet assigned
   */
  public T getId() {
    return id;
  }

  /**
   * Determines equality based on the entity's identifier.
   * <p>
   * Two entities are considered equal if they have the same type and the same ID.
   * This follows DDD principles where entity identity is the sole determinant of equality.
   * </p>
   * 
   * @param o the object to compare with
   * @return true if the entities have the same ID and type, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    IdentityDomain<?> that = (IdentityDomain<?>) o;

    return new EqualsBuilder().append(id, that.id).isEquals();
  }

  /**
   * Returns a hash code based on the entity's identifier.
   * <p>
   * The hash code is computed using only the entity's ID to ensure consistency
   * with the equals method implementation.
   * </p>
   * 
   * @return the hash code for this entity
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).toHashCode();
  }
}