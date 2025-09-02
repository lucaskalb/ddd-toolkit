package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Context;

public interface DomainEventSubscriber<T extends DomainEvent> {

  Class<T> subscribedToEvent();

  void handleEvent(Context ctx, T event);
}