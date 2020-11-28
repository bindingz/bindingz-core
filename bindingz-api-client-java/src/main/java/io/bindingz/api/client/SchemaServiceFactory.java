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

import io.bindingz.api.annotations.jackson.JacksonConfiguration;
import io.bindingz.api.client.jackson.JacksonSchemaService;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SchemaServiceFactory {

    private TypeScanner typeScanner;
    private Map<Annotation, SchemaService> schemaServices = new HashMap();
    private AtomicReference<JacksonSchemaService> defaultService = new AtomicReference<>();

    public SchemaServiceFactory(TypeScanner typeScanner) {
        this.typeScanner = typeScanner;
    }

    public SchemaService getSchemaService(Class contract) {
        // Only Jackson is supported at the moment
        JacksonConfiguration configuration = (JacksonConfiguration) contract.getAnnotation(JacksonConfiguration.class);
        if (configuration != null) {
            return schemaServices.computeIfAbsent(configuration, (conf) -> new JacksonSchemaService(typeScanner, configuration));
        }

        // Use Jackson as default
        JacksonSchemaService service = defaultService.get();
        if (service == null) {
            defaultService.set(new JacksonSchemaService(typeScanner));
        }

        return defaultService.get();
    }
}
