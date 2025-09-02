package dev.lucaskalb.ddd.core;

import java.io.Serial;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

/**
 * Abstract base class for entity identifiers using Long as the underlying value
 * type.
 * <p>
 * This class provides a foundation for creating strongly-typed entity
 * identifiers that follow
 * Domain-Driven Design principles. It uses Long for globally unique
 * identification and includes
 * JPA annotations for persistence support.
 * </p>
 * <p>
 * Subclasses should provide specific identity types for different entities:
 * 
 * <pre>{@code
 * public class UserId extends EntityId {
 *   public UserId() {
 *     super();
 *   }
 * 
 *   public UserId(Long id) {
 *     super(id);
 *   }
 * }
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 */
@Embeddable
@MappedSuperclass
public abstract class EntityId implements Identity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The Long value that uniquely identifies the entity.
   */
  protected Long value;

  /**
   * Protected default constructor for JPA and subclass use.
   */
  protected EntityId() {
    super();
  }

  /**
   * Protected constructor that initializes the entity ID with the given Long.
   * 
   * @param id the Long value for this entity ID, must not be null
   * @throws IllegalArgumentException if the id is null
   */
  protected EntityId(Long id) {
    this();
    setValue(id);
  }

  /**
   * Sets the Long value for this entity ID.
   * 
   * @param value the Long value to set, must not be null
   * @throws IllegalArgumentException if value is null
   */
  protected void setValue(Long value) {
    Arguments.checkIfIsNull(value, "id.cannot_be_null");
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  public Long value() {
    return value;
  }
}
