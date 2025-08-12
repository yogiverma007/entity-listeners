# entity-listeners

A sample Java project using Spring Boot and Maven to demonstrate the use of JPA Entity Listeners.

## Features

- Java 21+
- Spring Boot
- JPA Entity Listeners for auditing and lifecycle events
- Maven build system

## Getting Started

Listeners can be created to respond to entity lifecycle events in a Spring Boot application using JPA. 
Each entity can have its own listeners for events such as `@PrePersist`, `@PostPersist`, etc. However, managing listeners for every entity separately can become cumbersome.

This project demonstrates how to implement and use generic listeners effectively in a Spring Boot application. 
You can create a generic listener and apply it to all entities by annotating them with `@EntityListeners` and specifying a common listener class, such as `WrapperListener`.

All entities that should be listened to must be annotated with `@EntityListeners` and specify the `WrapperListener` class. 
The `WrapperListener` class handles lifecycle events and can perform actions such as logging, auditing, or sending custom events to queues.
### Prerequisites

- Java 21 or higher
- Maven 3.6+

Add enum in the Enums.EntityType to register for the entity listener:
```java

EMPLOYEE(Employee.class);

```

Example of an entity class that uses the `WrapperEntityListener`:


```java

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@EntityListeners(WrapperEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double salary;
}
```
Example of the `WrapperEntityListener` class that handles lifecycle events:

```java

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.freedom.entitylisteners.Enums.EntityType.EMPLOYEE;

@Slf4j
@Component
public class EmployeeEntityListener implements EntityListener<Employee> {

  @Override
  public void postUpdate(Employee entity) {
    handleEvent(entity);
  }

  @Override
  public void postPersist(Employee entity) {
    handleEvent(entity);
  }

  private void handleEvent(Employee entity) {
    TransactionSynchronizationManager.registerSynchronization(
        new TransactionSynchronization() {
          @Override
          public void afterCompletion(int status) {
            if (status == TransactionSynchronization.STATUS_COMMITTED) {
              log.info("Transaction committed for Employee: {}", entity.getId());
            } else {
              log.warn("Transaction rolled back for Employee: {}", entity.getId());
            }
          }
        });
  }

  @Override
  public Enums.EntityType getType() {
    return EMPLOYEE;
  }
}

```


Voila! Now whenever an `Employee` entity is persisted or updated, the `EmployeeEntityListener` will handle the event and log the transaction status.