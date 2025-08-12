package com.freedom.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WrapperEntityListener<E> {

  private final EntityListenerFactory entityListenerFactory;

  @Autowired
  public WrapperEntityListener(@Lazy EntityListenerFactory entityListenerFactory) {
    this.entityListenerFactory = entityListenerFactory;
  }

  @PrePersist
  public void prePersist(E entity) {
    entityListenerFactory
        .getEntityListener(Enums.EntityType.of(entity.getClass()))
        .prePersist(entity);
  }

  @PreUpdate
  public void preUpdate(E entity) {
    entityListenerFactory
        .getEntityListener(Enums.EntityType.of(entity.getClass()))
        .preUpdate(entity);
  }

  @PostPersist
  public void postPersist(E entity) {
    entityListenerFactory
        .getEntityListener(Enums.EntityType.of(entity.getClass()))
        .postPersist(entity);
  }

  @PostUpdate
  public void postUpdate(E entity) {
    entityListenerFactory
        .getEntityListener(Enums.EntityType.of(entity.getClass()))
        .postUpdate(entity);
  }
}
