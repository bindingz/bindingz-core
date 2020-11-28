package io.bindingz.api.client;

import java.util.List;

public interface TypeScanner {
    <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, String... packages);
    <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, List<String> packages);
    List<Class<?>> getTypesAnnotatedWith(Class<?> type, String... packages);
    List<Class<?>> getTypesAnnotatedWith(Class<?> type, List<String> packages);
}
