/*
 * Copyright (c) 2020 Connor Goulding
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.bindingz.api.client.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import io.bindingz.api.annotations.jackson.ConfigurationFactory;
import io.bindingz.api.annotations.jackson.JacksonConfiguration;
import io.bindingz.api.client.SchemaService;
import io.bindingz.api.client.TypeScanner;
import io.bindingz.api.model.JsonSchemaSpec;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JacksonSchemaService implements SchemaService {

    private final JsonSchemaGenerator generator;

    public JacksonSchemaService(TypeScanner typeScanner) {
        this.generator = createDefaultGenerator(typeScanner);
    }

    public JacksonSchemaService(TypeScanner typeScanner, JacksonConfiguration configuration) {
        if (!configuration.factory().equals(ConfigurationFactory.class)) {
            try {
                ConfigurationFactory factory = configuration.factory().getConstructor().newInstance();
                generator = new JsonSchemaGenerator(
                        factory.createObjectMapper(),
                        createConfig(factory.customTypeMappings())
                );
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to create ConfigurationFactory");
            }
        } else {
            generator = createDefaultGenerator(typeScanner);
        }
    }

    @Override
    public Map<JsonSchemaSpec, JsonNode> createSchemas(Class contract) {
        Map<JsonSchemaSpec, JsonNode> schemas = new HashMap<>();
        schemas.put(JsonSchemaSpec.DRAFT_04, generator.generateJsonSchema(contract));
        return schemas;
    }

    private JsonSchemaGenerator createDefaultGenerator(TypeScanner typeScanner) {
        Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put("java.time.LocalDateTime", "datetime-local");
        typeMappings.put("java.time.OffsetDateTime", "datetime");
        typeMappings.put("java.time.LocalDate", "date");
        typeMappings.put("org.joda.time.LocalDate", "date");
        return new JsonSchemaGenerator(
                createDefaultObjectMapper(typeScanner),
                createConfig(typeMappings)
        );
    }

    private JsonSchemaConfig createConfig(Map<String, String> customTypesToFormats) {
        JsonSchemaConfig config = JsonSchemaConfig.create(
                false,
                Optional.empty(),
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                customTypesToFormats,
                false,
                new HashSet<>(Arrays.asList(
                        scala.collection.immutable.Set.class,
                        scala.collection.mutable.Set.class,
                        java.util.Set.class
                )),
                Collections.emptyMap(),
                Collections.emptyMap(),
                null,
                true,
                null
        );

        return config.withJsonSchemaDraft(version(JsonSchemaSpec.DRAFT_04));
    }

    private ObjectMapper createDefaultObjectMapper(TypeScanner typeScanner) {
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

    private JsonSchemaDraft version(JsonSchemaSpec spec) {
        return JsonSchemaDraft.valueOf(spec.name());
    }
}
