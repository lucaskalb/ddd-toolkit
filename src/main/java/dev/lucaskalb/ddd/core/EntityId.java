package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Arguments;
import java.io.Serial;
import java.util.UUID;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public abstract class EntityId implements Identity<UUID> {

  @Serial private static final long serialVersionUID = 1L;

  protected UUID value;

  protected EntityId() {
    super();
  }

  protected EntityId(UUID id) {
    this();
    setValue(id);
  }

  protected void setValue(UUID value) {
    Arguments.checkIfIsNull(value, "id.cannot_be_null");
    this.value = value;
  }

  public UUID value() {
    return value;
  }
}