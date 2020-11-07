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

package io.bindingz.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractDto {
    private final String namespace;
    private final String owner;
    private final String contractName;
    private final String version;
    private final ContractSchema schema;

    @JsonCreator
    public ContractDto(@JsonProperty("namespace") String namespace,
                       @JsonProperty("owner") String owner,
                       @JsonProperty("contractName") String contractName,
                       @JsonProperty("version") String version,
                       @JsonProperty("schema") ContractSchema schema) {
        this.namespace = namespace;
        this.owner = owner;
        this.contractName = contractName;
        this.version = version;
        this.schema = schema;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getContractName() {
        return contractName;
    }

    public String getOwner() {
        return owner;
    }

    public String getVersion() {
        return version;
    }

    public ContractSchema getSchema() {
        return schema;
    }
}
