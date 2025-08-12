package com.freedom.entitylisteners;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public class Enums {

  @Getter
  public enum EntityType {

    /*EMPLOYEE(Employee.class);*/
    EMPLOYEE(Object.class);

    private static final Map<Class, EntityType> MAP =
        Stream.of(EntityType.values()).collect(Collectors.toMap(e -> e.type, Function.identity()));

    public final Class type;

    EntityType(Class type) {
      this.type = type;
    }

    public static EntityType of(Class classType) {
      return MAP.get(classType);
    }
  }
}
