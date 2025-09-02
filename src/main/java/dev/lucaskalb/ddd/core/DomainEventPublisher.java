package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Arguments;
import dev.lucaskalb.ddd.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DomainEventPublisher {

  private static <T extends DomainEvent>
      Predicate<DomainEventSubscriber<T>> isSubscribedToEventType(T domainEvent) {
    return subscriber -> {
      var subscribedToType = subscriber.subscribedToEvent();
      return domainEvent.getClass() == subscribedToType
          || subscribedToType.isAssignableFrom(domainEvent.getClass())
          || subscribedToType == DomainEvent.class;
    };
  }

  private List subscribers = new ArrayList();

  public <T extends DomainEvent> void publish(Context ctx, T domainEvent) {
    List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();
    allSubscribers.stream()
        .filter(isSubscribedToEventType(domainEvent))
        .forEach(subscriber -> subscriber.handleEvent(ctx, domainEvent));
  }

  private List subscribers() {
    return subscribers;
  }

  public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber) {
    Arguments.checkIfIsNull(subscriber, "Subscriber cannot be null");
    subscribers.add(subscriber);
  }
}