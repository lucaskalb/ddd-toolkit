package dev.lucaskalb.ddd.core;

import java.io.Serializable;

public interface Identity<T> extends Serializable {

  T value();
}