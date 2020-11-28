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
import io.bindingz.api.annotations.Contract;
import io.bindingz.api.model.ContractDto;
import io.bindingz.api.model.ContractSchema;
import io.bindingz.api.model.JsonSchemaSpec;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractService {

    private final TypeScanner typeScanner;
    private final SchemaServiceFactory schemaServiceFactory;

    public ContractService(TypeScanner typeScanner) {
        this.typeScanner = typeScanner;
        this.schemaServiceFactory = new SchemaServiceFactory(typeScanner);
    }

    public Collection<ContractDto> create(String... packageNames) throws IOException {
        return create(Arrays.asList(packageNames));
    }

    public Collection<ContractDto> create(List<String> packageNames) throws IOException {
        Collection<Class<?>> contractClasses = typeScanner.getTypesAnnotatedWith(Contract.class, packageNames);
        return contractClasses.stream().map(clazz -> createResource(clazz)).collect(Collectors.toList());
    }

    private ContractDto createResource(Class contract) {
        SchemaService schemaService = schemaServiceFactory.getSchemaService(contract);
        Map<JsonSchemaSpec, JsonNode> schemas = schemaService.createSchemas(contract);

        Contract owner = (Contract) contract.getAnnotation(Contract.class);
        return new ContractDto(
                owner.namespace(),
                owner.owner(),
                owner.contractName(),
                owner.version(),
                new ContractSchema(schemas)
        );
    }
}
