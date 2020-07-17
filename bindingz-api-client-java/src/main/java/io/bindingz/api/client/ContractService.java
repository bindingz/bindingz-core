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

package io.bindingz.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import io.bindingz.api.annotations.Contract;
import io.bindingz.api.model.ContractDto;
import io.bindingz.api.model.ContractSchema;
import io.bindingz.api.model.JsonSchemaSpec;
import dorkbox.annotation.AnnotationDefaults;
import dorkbox.annotation.AnnotationDetector;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractService {

    private final ObjectMapper mapper;

    public ContractService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Collection<ContractDto> create(ClassLoader classLoader, String... packageNames) throws IOException {
        Collection<Class<?>> contractClasses = AnnotationDetector.
                scanClassPath(classLoader, packageNames).
                forAnnotations(Contract.class).
                on(ElementType.TYPE).
                collect(AnnotationDefaults.getType);

        return contractClasses.stream().map(clazz -> createResource(clazz)).collect(Collectors.toList());
    }

    private ContractDto createResource(Class contract) {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(
                mapper,
                JsonSchemaConfig.vanillaJsonSchemaDraft4().withJsonSchemaDraft(version(JsonSchemaSpec.DRAFT_04))
        );

        Map<JsonSchemaSpec, JsonNode> schemas = new HashMap<>();
        schemas.put(JsonSchemaSpec.DRAFT_04, generator.generateJsonSchema(contract));

        Contract owner = (Contract) contract.getAnnotation(Contract.class);
        return new ContractDto(
                owner.namespace(),
                owner.owner(),
                owner.contractName(),
                owner.version(),
                new ContractSchema(schemas)
        );
    }

    private JsonSchemaDraft version(JsonSchemaSpec spec) {
        return JsonSchemaDraft.valueOf(spec.name());
    }
}
