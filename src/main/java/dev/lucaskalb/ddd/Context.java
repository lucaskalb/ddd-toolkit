package dev.lucaskalb.ddd;

import java.util.Map;

public interface Context {

  Map<String, Object> metadata();

  default Context addMetadata(String key, Object object) {
    if (metadata() != null) metadata().put(key, object);

    return this;
  }
}