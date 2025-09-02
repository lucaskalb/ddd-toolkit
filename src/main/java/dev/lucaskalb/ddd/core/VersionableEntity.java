package dev.lucaskalb.ddd.core;

import java.io.Serial;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@MappedSuperclass
public abstract class VersionableEntity<T extends EntityId> extends IdentityDomain<T> {

  @Serial private static final long serialVersionUID = 1L;

  @Version
  @Column(nullable = false)
  private long version = 0L;

  public long version() {
    return this.version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    VersionableEntity<?> that = (VersionableEntity<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(version, that.version)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(version).toHashCode();
  }
}