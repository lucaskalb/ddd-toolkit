package dev.lucaskalb.ddd.core;

import java.io.Serializable;

/**
 * Represents a domain identity that uniquely identifies an entity within its bounded context.
 * <p>
 * This interface follows the Identity pattern from Domain-Driven Design, providing a contract
 * for all identity objects that must be serializable and provide access to their underlying value.
 * </p>
 * 
 * @param <T> the type of the underlying identity value
 * @author Lucas Kalb
 * @since 0.0.1
 */
public interface Identity<T> extends Serializable {

  /**
   * Returns the underlying value of this identity.
   * 
   * @return the identity value, never null
   */
  T value();
}