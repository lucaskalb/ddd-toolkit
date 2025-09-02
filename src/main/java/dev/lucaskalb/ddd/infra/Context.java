package dev.lucaskalb.ddd.infra;

import java.util.Map;

/**
 * Interface representing an execution context that carries cross-cutting
 * information.
 * <p>
 * Context provides a way to pass additional information through domain
 * operations
 * without polluting method signatures. This is particularly useful for concerns
 * like:
 * <ul>
 * <li>User authentication and authorization information</li>
 * <li>Transaction boundaries and correlation IDs</li>
 * <li>Audit trails and logging context</li>
 * <li>Feature flags and configuration</li>
 * <li>Localization and tenant information</li>
 * </ul>
 * </p>
 * <p>
 * The Context follows the pattern of providing a metadata map for extensibility
 * while maintaining type safety through well-known keys and accessor methods.
 * </p>
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * public class ExecutionContext implements Context {
 *   private final Map<String, Object> metadata = new HashMap<>();
 *   private final UserId userId;
 * 
 *   public ExecutionContext(UserId userId) {
 *     this.userId = userId;
 *     metadata.put("userId", userId);
 *     metadata.put("timestamp", Instant.now());
 *   }
 * 
 *   @Override
 *   public Map<String, Object> metadata() {
 *     return metadata;
 *   }
 * 
 *   public UserId getUserId() {
 *     return userId;
 *   }
 * }
 * 
 * // Usage in domain operations
 * Context ctx = new ExecutionContext(currentUser.getId());
 * ctx.addMetadata("operation", "customerRegistration");
 * domainService.registerCustomer(ctx, customerData);
 * }</pre>
 * </p>
 * 
 * @author Lucas Kalb
 * @since 0.0.1
 */
public interface Context {

  /**
   * Returns the metadata map containing context information.
   * <p>
   * The metadata map provides a flexible way to store and retrieve context
   * information
   * using string keys. Implementations should ensure this map is mutable to
   * support
   * the {@link #addMetadata(String, Object)} method.
   * </p>
   * 
   * @return a map containing context metadata, may be null if no metadata is
   *         supported
   */
  Map<String, Object> metadata();

  /**
   * Adds a key-value pair to the context metadata.
   * <p>
   * This is a convenience method that adds information to the metadata map if it
   * exists.
   * If the metadata map is null, this method does nothing and still returns the
   * context
   * for method chaining.
   * </p>
   * <p>
   * This method supports fluent usage patterns:
   * 
   * <pre>{@code
   * context.addMetadata("correlationId", UUID.randomUUID().toString())
   *     .addMetadata("operation", "userRegistration")
   *     .addMetadata("timestamp", Instant.now());
   * }</pre>
   * </p>
   * 
   * @param key    the metadata key, should not be null
   * @param object the metadata value, may be null
   * @return this context instance for method chaining
   */
  default Context addMetadata(String key, Object object) {
    if (metadata() != null)
      metadata().put(key, object);

    return this;
  }
}
