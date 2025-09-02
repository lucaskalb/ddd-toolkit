# DDD Toolkit

[![CI](https://github.com/lucaskalb/ddd-toolkit/workflows/CI/badge.svg)](https://github.com/lucaskalb/ddd-toolkit/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/dev.lucaskalb/ddd-toolkit.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/dev.lucaskalb/ddd-toolkit)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/projects/jdk/17/)

A comprehensive Domain-Driven Design (DDD) toolkit library for Java applications.

## Overview

This library provides essential building blocks for implementing Domain-Driven Design patterns in Java applications, including value objects, entities, domain events, specifications, and validation utilities.

## Features

- **Core DDD Building Blocks**: Identity, Entity, Value Objects
- **Domain Events**: Publisher/Subscriber pattern implementation
- **Specification Pattern**: Composable business rules
- **Validation Utilities**: Argument validation and result handling
- **JPA Integration**: Ready-to-use JPA annotations for persistence
- **JSON Support**: Jackson annotations for serialization

## Installation

### Maven

```xml
<dependency>
    <groupId>dev.lucaskalb</groupId>
    <artifactId>ddd-toolkit</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Gradle

```groovy
implementation 'dev.lucaskalb:ddd-toolkit:0.0.1'
```

## Core Components

### Identity and Entities

```java
// Custom entity ID
public class UserId extends EntityId {
    public UserId() { super(); }
    public UserId(UUID id) { super(id); }
}

// Domain entity
@Entity
public class User extends VersionableEntity<UserId> {
    // Entity implementation
}
```

### Value Objects

```java
public class Email extends ValueObject {
    private final String value;
    
    public Email(String value) {
        Arguments.checkIfIsEmpty(value, "Email cannot be empty");
        this.value = value;
    }
    
    @Override
    protected Object[] getEqualityComponents() {
        return new Object[]{value};
    }
}
```

### Domain Events

```java
// Define domain event
public class UserCreatedEvent implements DomainEvent {
    private final UserId userId;
    private final Instant occurredOn;
    
    // Implementation
}

// Publish events
DomainEventPublisher.instance().publish(new UserCreatedEvent(userId));

// Subscribe to events
DomainEventPublisher.instance().subscribe(
    UserCreatedEvent.class, 
    event -> System.out.println("User created: " + event.getUserId())
);
```

### Specifications

```java
// Create specifications
Specification<User> activeUsers = user -> user.isActive();
Specification<User> premiumUsers = user -> user.isPremium();

// Compose specifications
Specification<User> activePremiumUsers = activeUsers.and(premiumUsers);

// Use specifications
if (activePremiumUsers.isSatisfiedBy(user)) {
    // Business logic for active premium users
}
```

### Validation

```java
// Argument validation
Arguments.checkIfIsNull(user, "User cannot be null");
Arguments.checkIfIsEmpty(email, "Email cannot be empty");

// Result validation
ValidationResult result = validateUser(user);
if (result.isSuccess()) {
    // Process valid user
} else {
    result.throwIfFailure(IllegalArgumentException::new);
}
```

## Requirements

- Java 17 or higher
- Spring Framework (optional, for enhanced features)
- JPA implementation (optional, for entity persistence)

## Dependencies

The library uses minimal dependencies:

- **Jakarta Persistence API** (provided scope)
- **Apache Commons Lang3** (provided scope)  
- **Spring Security Core** (provided scope)
- **Jackson Annotations** (provided scope)

All dependencies are marked as `provided` scope, giving you flexibility to use your preferred versions.

## JSON Serialization

The library includes custom JSON formatters for date/time handling:

```java
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IsoLocalDateJsonFormat.PATTERN)
private LocalDate createdDate;

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IsoLocalDateTimeJsonFormat.PATTERN)
private LocalDateTime createdDateTime;
```

## Building from Source

```bash
# Clone the repository
git clone https://github.com/lucaskalb/ddd-toolkit.git
cd ddd-toolkit

# Build with Maven
mvn clean install

# Run tests
mvn test

# Generate coverage report
mvn jacoco:report
```

## CI/CD

This project uses GitHub Actions for continuous integration and deployment:

- **CI Pipeline**: Runs tests on Java 17 and 21 for every push and pull request
- **Release Pipeline**: Automatically deploys to Maven Central when a version tag is pushed
- **Snapshot Pipeline**: Deploys snapshot versions to Maven Central on main branch commits

### Release Process

1. Update version in `pom.xml` (remove `-SNAPSHOT`)
2. Create and push a git tag:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. GitHub Actions will automatically:
   - Run tests
   - Deploy to Maven Central
   - Create a GitHub release

### Required Secrets

For deployment to work, the following secrets must be configured in GitHub:

- `OSSRH_USERNAME`: Sonatype OSSRH username
- `OSSRH_TOKEN`: Sonatype OSSRH token
- `GPG_PRIVATE_KEY`: GPG private key for signing artifacts
- `GPG_PASSPHRASE`: GPG key passphrase

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Lucas Kalb**
- Email: site@lucaskalb.dev
- GitHub: [@lucaskalb](https://github.com/lucaskalb)

## Support

If you encounter any issues or have questions, please [create an issue](https://github.com/lucaskalb/ddd-toolkit/issues) on GitHub.