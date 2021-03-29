package io.bindingz.api.client;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bindingz.context.loader.TypeScanner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class ClassGraphTypeScanner implements TypeScanner {

    private final ClassLoader classLoader;

    private Map<SubTypeKey, List> subTypeCache = new HashMap<>();
    private Map<SubTypeKey, List> annotatedWithCache = new HashMap<>();

    public ClassGraphTypeScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Class<?> getClass(String className) {
        try {
            return Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load class", e);
        }
    }

    @Override
    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, String... packages) {
        return getSubTypesOf(type, Arrays.asList(packages));
    }

    @Override
    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, List<String> packages) {
        if (subTypeCache.containsKey(new SubTypeKey(type, packages))) {
            return subTypeCache.get(new SubTypeKey(type, packages));
        }

        ClassGraph classGraph = new ClassGraph()
                .enableAllInfo()
                .overrideClassLoaders(classLoader)
                .acceptPackages(packages.toArray(new String[]{}));

        List<Class<? extends T>> result = new ArrayList<>();
        try (ScanResult scanResult = classGraph.scan()) {
            List<ClassInfo> classInfos = null;
            if (type.isInterface()) {
                classInfos = scanResult.getClassesImplementing(type.getName());
            } else {
                classInfos = scanResult.getSubclasses(type.getName());
            }

            for (ClassInfo classInfo : classInfos) {
                String className = classInfo.getName();
                try {
                    result.add((Class<? extends T>) Class.forName(className, true, classLoader));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("Unable to load class", e);
                }
            }
        }

        subTypeCache.put(new SubTypeKey(type, packages), result);
        return result;
    }

    public List<Class<?>> getTypesAnnotatedWith(Class<?> type, String... packages) {
        return getTypesAnnotatedWith(type, packages);
    }

    @Override
    public List<Class<?>> getTypesAnnotatedWith(Class<?> type, List<String> packages) {
        if (annotatedWithCache.containsKey(new SubTypeKey(type, packages))) {
            return annotatedWithCache.get(new SubTypeKey(type, packages));
        }

        ClassGraph classGraph = new ClassGraph()
                .enableAllInfo()
                .overrideClassLoaders(classLoader)
                .acceptPackages(packages.toArray(new String[]{}));

        List<Class<?>> result = new ArrayList<>();
        try (ScanResult scanResult = classGraph.scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(type.getName())) {
                String className = classInfo.getName();
                try {
                    result.add(Class.forName(className, true, classLoader));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        annotatedWithCache.put(new SubTypeKey(type, packages), result);
        return result;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class SubTypeKey {
        private final Class<?> type;
        private final List<String> packages;
    }
}
