package com.alevel.lesson10.shop.context;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.LaptopRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;
import com.alevel.lesson10.shop.service.BallService;
import com.alevel.lesson10.shop.service.LaptopService;
import com.alevel.lesson10.shop.service.PhoneService;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    Map<Class<?>, Object> singletonCache;

    public ApplicationContext() {
        singletonCache = new HashMap<>();
    }

    public Map<Class<?>, Object> cacheSingletons() {
        Reflections reflections = new Reflections("com");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Singleton.class);
        cacheRepositories(typesAnnotatedWith);
        cacheServices(typesAnnotatedWith);
        return singletonCache;
    }

    private void cacheRepositories(Set<Class<?>> typesAnnotatedWith) {
        typesAnnotatedWith.forEach(clazz -> {
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if (declaredConstructor.isAnnotationPresent(Autowired.class) && declaredConstructor.getParameterCount() == 0) {
                    try {
                        Object o = clazz.getDeclaredConstructor().newInstance();
                        singletonCache.put(clazz, o);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void cacheServices(Set<Class<?>> typesAnnotatedWith) {
        typesAnnotatedWith.forEach(clazz -> {
            for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
                if (declaredConstructor.isAnnotationPresent(Autowired.class) && declaredConstructor.getParameterCount() == 1) {
                    try {
                        if (LaptopService.class.equals(clazz)) {
                            Object o = declaredConstructor.newInstance(singletonCache.get(LaptopRepositoryListImpl.class));
                            singletonCache.put(clazz, o);
                        }
                        if (BallService.class.equals(clazz)) {
                            Object o = declaredConstructor.newInstance(singletonCache.get(BallRepositoryListImpl.class));
                            singletonCache.put(clazz, o);
                        }
                        if (PhoneService.class.equals(clazz)) {
                            Object o = declaredConstructor.newInstance(singletonCache.get(PhoneRepositoryListImpl.class));
                            singletonCache.put(clazz, o);
                        }

                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
