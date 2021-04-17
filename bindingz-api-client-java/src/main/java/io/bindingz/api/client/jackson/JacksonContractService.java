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
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import io.bindingz.api.client.ContractService;
import io.bindingz.api.client.context.definition.model.PublishConfiguration;
import io.bindingz.api.client.context.definition.model.PublishContractDefinition;
import io.bindingz.api.model.ContractDto;
import io.bindingz.api.model.ContractSchema;
import io.bindingz.api.model.JsonSchemaSpec;
import io.bindingz.context.jackson.PublishConfigurationFactory;
import io.bindingz.context.loader.TypeScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JacksonContractService implements ContractService {

    private final TypeScanner typeScanner;

    public JacksonContractService(TypeScanner typeScanner) {
        this.typeScanner = typeScanner;
    }

    @Override
    public Collection<ContractDto> create(List<PublishContractDefinition> contracts) {
        return contracts.stream().map(this::createResource).collect(Collectors.toList());
    }

    private ContractDto createResource(PublishContractDefinition contract) {
        try {
            Map<JsonSchemaSpec, JsonNode> schemas = createSchemas(contract);
            return new ContractDto(
                    contract.getNamespace(),
                    contract.getOwner(),
                    contract.getContractName(),
                    contract.getVersion(),
                    new ContractSchema(schemas)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to create schema for " + contract.getClassName(), e);
        }
    }
    
    private Map<JsonSchemaSpec, JsonNode> createSchemas(PublishContractDefinition contract) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<JsonSchemaSpec, JsonNode> schemas = new HashMap<>();
        Class<? extends PublishConfigurationFactory> configurationFactoryClass = getPublishConfigurationFactory(contract);
        PublishConfigurationFactory publishConfigurationFactory = configurationFactoryClass.getConstructor().newInstance();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(
                publishConfigurationFactory.objectMapper(typeScanner),
                createConfig(contract.getTypeMappings())
        );

        String fullClassName = String.format("%s.%s", contract.getPackageName(), contract.getClassName());
        schemas.put(JsonSchemaSpec.DRAFT_04, generator.generateJsonSchema(typeScanner.getClass(fullClassName)));
        return schemas;
    }

    private Class<? extends PublishConfigurationFactory> getPublishConfigurationFactory(PublishContractDefinition contract) {
        Class<? extends PublishConfigurationFactory> configurationFactoryClass;
        if (DefaultPublishConfigurationFactory.class.getName().equals(contract.getConfigurationFactoryClass())) {
            configurationFactoryClass = DefaultPublishConfigurationFactory.class;
        } else {
            configurationFactoryClass = (Class<PublishConfigurationFactory>)typeScanner.getClass(contract.getConfigurationFactoryClass());
        }
        return configurationFactoryClass;
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

    private JsonSchemaDraft version(JsonSchemaSpec spec) {
        return JsonSchemaDraft.valueOf(spec.name());
    }
}
