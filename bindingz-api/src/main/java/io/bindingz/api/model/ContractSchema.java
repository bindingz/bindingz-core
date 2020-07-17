package io.bindingz.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractSchema {
    private final Map<JsonSchemaSpec, JsonNode> specs;

    @JsonCreator
    public ContractSchema(@JsonProperty("specs") Map<JsonSchemaSpec, JsonNode> specs) {
        this.specs = specs;
    }

    public Map<JsonSchemaSpec, JsonNode> getSpecs() {
        return specs;
    }
}
