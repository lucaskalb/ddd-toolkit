package dev.lucaskalb.ddd.core;

import java.io.Serial;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract base class for domain entities that support optimistic locking
 * through versioning.
 * <p>
 * This class extends {@link IdentityDomain} to add version-based concurrency
 * control using
 * JPA's {@code @Version} annotation. This enables optimistic locking to prevent
 * lost updates
 * in concurrent scenarios while maintaining DDD entity semantics.
 * </p>
 * <p>
 * The version field is automatically managed by the JPA provider and should not
 * be modified
 * directly by application code. Each successful update operation increments the
 * version.
 * </p>
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * @Entity
 * public class Account extends VersionableEntity<AccountId> {
 *   private Money balance;
 * 
 *   public void withdraw(Money amount) {
 *     // domain logic here
 *     // version will be automatically incremented on save
 *   }
 * }
 * }</pre>
 * </p>
 * 
 * @param <T> the type of the entity identifier, must extend EntityId
 * @author Lucas Kalb
 * @since 0.0.1
 */
@MappedSuperclass
public abstract class VersionableEntity<T extends EntityId> extends IdentityDomain<T> {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Version field for optimistic locking.
   * This field is automatically managed by JPA and incremented on each update.
   */
  @Version
  @Column(nullable = false)
  private long version = 0L;

  /**
   * Returns the current version of this entity.
   * <p>
   * The version is used for optimistic locking and is automatically incremented
   * by the persistence provider on each successful update operation.
   * </p>
   * 
   * @return the current version number, starting from 0 for new entities
   */
  public long version() {
    return this.version;
  }

  /**
   * Determines equality based on both the entity's identifier and version.
   * <p>
   * Two versionable entities are considered equal if they have the same ID and
   * version.
   * This ensures that different versions of the same logical entity are treated
   * as
   * different objects for equality purposes.
   * </p>
   * 
   * @param o the object to compare with
   * @return true if the entities have the same ID and version, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    VersionableEntity<?> that = (VersionableEntity<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(version, that.version)
        .isEquals();
  }

  /**
   * Returns a hash code based on the entity's identifier and version.
   * <p>
   * The hash code includes both the entity ID and version to ensure consistency
   * with the equals method implementation.
   * </p>
   * 
   * @return the hash code for this versioned entity
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(version).toHashCode();
  }
}
