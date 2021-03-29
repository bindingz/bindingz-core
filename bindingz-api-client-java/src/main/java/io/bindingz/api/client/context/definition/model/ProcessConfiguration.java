package io.bindingz.api.client.context.definition.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProcessConfiguration {
    private String sourceCodeProvider = "JSONSCHEMA2POJO";
    private Map<String, String> sourceCodeConfiguration = new HashMap<>();

    private String namespace;
    private String owner;
    private List<ProcessContractDefinition> contracts;
}
