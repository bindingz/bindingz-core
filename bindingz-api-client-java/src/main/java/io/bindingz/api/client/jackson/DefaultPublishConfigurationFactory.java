package io.bindingz.api.client.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.bindingz.context.jackson.PublishConfigurationFactory;
import io.bindingz.context.loader.TypeScanner;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiredArgsConstructor
public class DefaultPublishConfigurationFactory implements PublishConfigurationFactory {

    @Override
    public ObjectMapper objectMapper(TypeScanner typeScanner) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        List<Class<? extends Module>> modules = typeScanner.getSubTypesOf(Module.class);
        for (Class<? extends Module> moduleClass : modules) {
            try {
                Constructor[] constructors = moduleClass.getConstructors();
                for (Constructor<Module> constructor : constructors) {
                    if (constructor.getParameterCount() == 0) {
                        Module module = constructor.newInstance();
                        mapper.registerModule(module);
                        System.out.println("Registered module " + moduleClass);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return mapper;
    }
}
