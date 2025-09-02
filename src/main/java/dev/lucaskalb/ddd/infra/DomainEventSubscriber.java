package dev.lucaskalb.ddd.infra;

import dev.lucaskalb.ddd.core.DomainEvent;

/**
 * Interface for subscribing to and handling domain events.
 * <p>
 * Domain Event Subscribers implement the Observer pattern to respond to domain events
 * published by the {@link DomainEventPublisher}. Each subscriber specifies which event
 * types it's interested in and provides the logic to handle those events.
 * </p>
 * <p>
 * Subscribers can handle side effects, integration with external systems, or coordination
 * between different bounded contexts without coupling the domain model to these concerns.
 * </p>
 * <p>
 * Example implementation:
 * <pre>{@code
 * public class CustomerEmailHandler implements DomainEventSubscriber<CustomerRegisteredEvent> {
 *     
 *     @Override
 *     public Class<CustomerRegisteredEvent> subscribedToEvent() {
 *         return CustomerRegisteredEvent.class;
 *     }
 *     
 *     @Override
 *     public void handleEvent(Context ctx, CustomerRegisteredEvent event) {
 *         // Send welcome email to new customer
 *         emailService.sendWelcomeEmail(event.getCustomerEmail());
 *         
 *         // Log the event
 *         logger.info("Welcome email sent to customer {}", event.getCustomerId());
 *     }
 * }
 * }</pre>
 * </p>
 * 
 * @param <T> the type of domain event this subscriber handles
 * @author Lucas Kalb
 * @since 0.0.1
 * @see DomainEvent
 * @see DomainEventPublisher
 */
public interface DomainEventSubscriber<T extends DomainEvent> {

  /**
   * Returns the class of domain event this subscriber is interested in.
   * <p>
   * This method enables type-safe event subscription. The publisher uses this
   * information to determine which subscribers should receive which events.
   * Polymorphic subscription is supported, so subscribers to parent event types
   * will also receive child event types.
   * </p>
   * 
   * @return the Class object representing the event type this subscriber handles
   */
  Class<T> subscribedToEvent();

  /**
   * Handles a domain event of the subscribed type.
   * <p>
   * This method is called by the {@link DomainEventPublisher} when an event of the
   * subscribed type is published. Implementations should contain the business logic
   * for responding to the event.
   * </p>
   * <p>
   * The context parameter provides additional information that may be needed for
   * event processing, such as user information, transaction details, or cross-cutting concerns.
   * </p>
   * 
   * @param ctx the context containing additional information for event processing
   * @param event the domain event to handle, guaranteed to be of type T
   */
  void handleEvent(Context ctx, T event);
}
