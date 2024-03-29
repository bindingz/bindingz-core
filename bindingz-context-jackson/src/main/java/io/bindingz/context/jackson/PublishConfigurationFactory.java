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
package io.bindingz.context.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.context.loader.TypeScanner;

public interface PublishConfigurationFactory {

    /**
     * Create an instance of ObjectMapper used for creating the schema
     *
     * @return
     */
    ObjectMapper objectMapper(TypeScanner typeScanner);
}
