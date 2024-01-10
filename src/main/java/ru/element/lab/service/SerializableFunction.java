package ru.element.lab.service;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Расширение функции на сериализуемость.
 */
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {}
