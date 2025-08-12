package com.freedom.entitylisteners;

public interface EntityListener<E> {

  default void prePersist(E entity) {}

  default void preUpdate(E entity) {}

  default void postPersist(E entity) {}

  default void postUpdate(E entity) {}

  Enums.EntityType getType();
}
