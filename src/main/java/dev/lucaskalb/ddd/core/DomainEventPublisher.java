package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Arguments;
import dev.lucaskalb.ddd.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Publisher for domain events implementing the Observer pattern.
 * <p>
 * The DomainEventPublisher acts as a mediator between domain entities that raise events
 * and the subscribers that handle them. It maintains a registry of subscribers and
 * dispatches events to all interested parties based on event type matching.
 * </p>
 * <p>
 * This implementation supports:
 * <ul>
 *   <li>Type-safe event subscription and publishing</li>
 *   <li>Hierarchical event type matching (subclass events match superclass subscribers)</li>
 *   <li>Context passing for cross-cutting concerns</li>
 *   <li>Multiple subscribers per event type</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * DomainEventPublisher publisher = new DomainEventPublisher();
 * 
 * // Subscribe to specific event types
 * publisher.subscribe(new CustomerEmailHandler());
 * publisher.subscribe(new AuditLogHandler());
 * 
 * // Publish events from domain entities
 * CustomerRegisteredEvent event = new CustomerRegisteredEvent(customerId, customerName);
 * publisher.publish(context, event);
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 * @see DomainEvent
 * @see DomainEventSubscriber
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DomainEventPublisher {

  /**
   * Creates a predicate that checks if a subscriber is interested in a specific domain event.
   * <p>
   * The matching logic supports:
   * <ul>
   *   <li>Exact type matching</li>
   *   <li>Superclass/interface matching (polymorphic subscription)</li>
   *   <li>Universal subscription (subscribers to DomainEvent.class receive all events)</li>
   * </ul>
   * </p>
   * 
   * @param <T> the type of domain event
   * @param domainEvent the event to match subscribers for
   * @return a predicate that returns true if the subscriber should handle this event
   */
  private static <T extends DomainEvent>
      Predicate<DomainEventSubscriber<T>> isSubscribedToEventType(T domainEvent) {
    return subscriber -> {
      var subscribedToType = subscriber.subscribedToEvent();
      return domainEvent.getClass() == subscribedToType
          || subscribedToType.isAssignableFrom(domainEvent.getClass())
          || subscribedToType == DomainEvent.class;
    };
  }

  /**
   * List of all registered event subscribers.
   */
  private List subscribers = new ArrayList();

  /**
   * Publishes a domain event to all interested subscribers.
   * <p>
   * This method finds all subscribers that are interested in the given event type
   * (including parent types) and notifies them by calling their handleEvent method.
   * The context is passed along to provide additional information for event processing.
   * </p>
   * 
   * @param <T> the type of domain event
   * @param ctx the context containing additional information for event processing
   * @param domainEvent the domain event to publish, must not be null
   * @throws IllegalArgumentException if domainEvent is null
   */
  public <T extends DomainEvent> void publish(Context ctx, T domainEvent) {
    List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();
    allSubscribers.stream()
        .filter(isSubscribedToEventType(domainEvent))
        .forEach(subscriber -> subscriber.handleEvent(ctx, domainEvent));
  }

  /**
   * Returns the list of all registered subscribers.
   * 
   * @return the list of subscribers
   */
  private List subscribers() {
    return subscribers;
  }

  /**
   * Registers a domain event subscriber.
   * <p>
   * The subscriber will be notified of all events that match its subscribed event type,
   * including events of subtypes due to polymorphic matching.
   * </p>
   * 
   * @param <T> the type of domain event the subscriber handles
   * @param subscriber the event subscriber to register, must not be null
   * @throws IllegalArgumentException if subscriber is null
   */
  public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber) {
    Arguments.checkIfIsNull(subscriber, "Subscriber cannot be null");
    subscribers.add(subscriber);
  }
}