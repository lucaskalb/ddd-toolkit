package dev.lucaskalb.ddd.core;

import java.time.LocalDateTime;

/**
 * Marker interface for domain events in Domain-Driven Design.
 * <p>
 * Domain Events represent something important that happened in the domain that domain
 * experts care about. They capture the business significance of state changes and allow
 * for decoupled communication between different parts of the domain.
 * </p>
 * <p>
 * All domain events must provide a timestamp indicating when the event occurred.
 * This enables event sourcing, auditing, and temporal queries on the domain model.
 * </p>
 * <p>
 * Example implementation:
 * <pre>{@code
 * public class CustomerRegisteredEvent implements DomainEvent {
 *     private final CustomerId customerId;
 *     private final String customerName;
 *     private final LocalDateTime occurredOn;
 *     
 *     public CustomerRegisteredEvent(CustomerId customerId, String customerName) {
 *         this.customerId = customerId;
 *         this.customerName = customerName;
 *         this.occurredOn = LocalDateTime.now();
 *     }
 *     
 *     @Override
 *     public LocalDateTime occurredOn() {
 *         return occurredOn;
 *     }
 *     
 *     // getters for event data
 * }
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 * @see DomainEventPublisher
 * @see DomainEventSubscriber
 */
public interface DomainEvent {

  /**
   * Returns the timestamp when this domain event occurred.
   * <p>
   * This timestamp is crucial for event ordering, replay scenarios, and auditing purposes.
   * It should typically be set when the event is created and remain immutable thereafter.
   * </p>
   * 
   * @return the LocalDateTime when this event occurred, never null
   */
  LocalDateTime occurredOn();
}