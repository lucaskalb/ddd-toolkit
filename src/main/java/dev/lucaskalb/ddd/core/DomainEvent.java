package dev.lucaskalb.ddd.core;

import java.time.LocalDateTime;

public interface DomainEvent {

  LocalDateTime occurredOn();
}