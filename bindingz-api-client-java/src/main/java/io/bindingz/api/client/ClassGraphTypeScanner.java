package io.bindingz.api.client;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassGraphTypeScanner implements TypeScanner {

    private final ClassLoader classLoader;

    public ClassGraphTypeScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, String... packages) {
        return getSubTypesOf(type, Arrays.asList(packages));
    }

    @Override
    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> type, List<String> packages) {
        ClassGraph classGraph = new ClassGraph()
                .enableAllInfo()
                .addClassLoader(classLoader)
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
                    result.add((Class<T>)classLoader.loadClass(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public List<Class<?>> getTypesAnnotatedWith(Class<?> type, String... packages) {
        return getTypesAnnotatedWith(type, packages);
    }

    @Override
    public List<Class<?>> getTypesAnnotatedWith(Class<?> type, List<String> packages) {
        ClassGraph classGraph = new ClassGraph()
                .enableAllInfo()
                .addClassLoader(classLoader)
                .acceptPackages(packages.toArray(new String[]{}));

        List<Class<?>> result = new ArrayList<>();
        try (ScanResult scanResult = classGraph.scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(type.getName())) {
                String className = classInfo.getName();
                try {
                    result.add(classLoader.loadClass(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
