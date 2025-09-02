package dev.lucaskalb.ddd.core;

import dev.lucaskalb.ddd.Arguments;
import java.io.Serializable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@MappedSuperclass
public abstract class IdentityDomain<T extends EntityId> implements Serializable {

  @EmbeddedId
  @AttributeOverride(name = "value", column = @Column(name = "id"))
  private T id;

  protected void setId(T id) {
    Arguments.checkIfIsNull(id, "id.cannot_be_null");
    this.id = id;
  }

  public T getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    IdentityDomain<?> that = (IdentityDomain<?>) o;

    return new EqualsBuilder().append(id, that.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).toHashCode();
  }
}