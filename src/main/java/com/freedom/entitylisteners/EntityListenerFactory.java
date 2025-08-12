package com.freedom.entitylisteners;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EntityListenerFactory {

  private Map<Enums.EntityType, EntityListener> CACHE_MAP = new EnumMap<>(Enums.EntityType.class);

  @Autowired(required = false)
  private List<EntityListener> entityListenersList;

  @PostConstruct
  public void init() {
    if (!CollectionUtils.isEmpty(entityListenersList)) {
      CACHE_MAP =
          entityListenersList.stream()
              .collect(Collectors.toMap(EntityListener::getType, Function.identity()));
    }
  }

  public EntityListener getEntityListener(Enums.EntityType entityType) {
    return CACHE_MAP.get(entityType);
  }
}
