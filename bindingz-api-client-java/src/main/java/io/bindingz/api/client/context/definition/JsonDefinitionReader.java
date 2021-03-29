package io.bindingz.api.client.context.definition;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.api.client.context.definition.model.Definition;
import io.bindingz.api.client.context.definition.model.ProcessConfiguration;
import io.bindingz.api.client.context.definition.model.ProcessContractDefinition;
import io.bindingz.api.client.context.definition.model.PublishConfiguration;
import io.bindingz.api.client.context.definition.model.PublishContractDefinition;
import io.bindingz.api.client.jackson.DefaultPublishConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonDefinitionReader implements DefinitionReader {
    private ObjectMapper mapper = new ObjectMapper();

    private static final String DEFAULT_PUBLISH_CONFIGURATION = DefaultPublishConfigurationFactory.class.getName();
    private static Map<String, String> DEFAULT_TYPE_MAPPINGS = new HashMap<>();

    private static final String DEFAULT_SOURCE_CODE_PROVIDER = "JSONSCHEMA2POJO";
    private static Map<String, String> DEFAULT_SOURCE_CODE_CONFIGURATION = new HashMap<>();

    static {
        DEFAULT_TYPE_MAPPINGS.put("java.time.LocalDateTime", "datetime-local");
        DEFAULT_TYPE_MAPPINGS.put("java.time.OffsetDateTime", "datetime");
        DEFAULT_TYPE_MAPPINGS.put("java.time.LocalDate", "date");
        DEFAULT_TYPE_MAPPINGS.put("org.joda.time.LocalDate", "date");
    }

    @Override
    public Definition read(String fileName) {
        try {
            return enrich(mapper.readValue(new File(fileName), Definition.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read definition file: " + fileName, e);
        }
    }

    private Definition enrich(Definition definition) {
        Definition.DefinitionBuilder builder = Definition.builder();
        builder = definition.getProcess() != null
                ? builder.process(transform(definition, definition.getProcess()))
                : builder;
        builder = definition.getPublish() != null
                ? builder.publish(transform(definition, definition.getPublish()))
                : builder;
        return builder.build();
    }

    private PublishConfiguration transform(Definition definition, PublishConfiguration publishConfiguration) {
        PublishConfiguration defaulted = publishConfiguration
                .withConfigurationFactoryClass(override(
                        DEFAULT_PUBLISH_CONFIGURATION,
                        publishConfiguration.getConfigurationFactoryClass()
                ))
                .withTypeMappings(override(
                        DEFAULT_TYPE_MAPPINGS,
                        publishConfiguration.getTypeMappings()
                ));

        return defaulted.withContracts(publishConfiguration.getContracts().stream()
                        .map(contract -> transform(defaulted, contract))
                        .collect(Collectors.toList()));
    }

    private PublishContractDefinition transform(PublishConfiguration parent, PublishContractDefinition contract) {
        return contract
                .withNamespace(override(parent.getNamespace(), contract.getNamespace()))
                .withOwner(override(parent.getOwner(), contract.getOwner()))
                .withPackageName(override(parent.getPackageName(), contract.getPackageName()))
                .withConfigurationFactoryClass(override(
                        parent.getConfigurationFactoryClass(),
                        contract.getConfigurationFactoryClass()
                ))
                .withTypeMappings(override(
                        parent.getTypeMappings(),
                        contract.getTypeMappings()
                ));
    }

    private ProcessConfiguration transform(Definition definition, ProcessConfiguration processConfiguration) {
        ProcessConfiguration defaulted = processConfiguration
                .withSourceCodeConfiguration(override(
                        DEFAULT_SOURCE_CODE_CONFIGURATION,
                        processConfiguration.getSourceCodeConfiguration()
                ))
                .withSourceCodeProvider(override(
                        DEFAULT_SOURCE_CODE_PROVIDER,
                        processConfiguration.getSourceCodeProvider()
                ));

        return defaulted.withContracts(processConfiguration.getContracts().stream()
                        .map(contract -> transform(defaulted, contract))
                        .collect(Collectors.toList()));
    }

    private ProcessContractDefinition transform(ProcessConfiguration parent, ProcessContractDefinition contract) {
        return contract
                .withNamespace(override(parent.getNamespace(), contract.getNamespace()))
                .withOwner(override(parent.getOwner(), contract.getOwner()))
                .withSourceCodeProvider(override(
                        parent.getSourceCodeProvider(),
                        contract.getSourceCodeProvider()
                ))
                .withSourceCodeConfiguration(override(
                        parent.getSourceCodeConfiguration(),
                        contract.getSourceCodeConfiguration()
                ));
    }

    private String override(String... value) {
        List<String> values = Arrays.asList(value).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return values.size() > 0 ? values.get(values.size() - 1) : null;
    }

    private Map<String, String> override(Map<String, String>... targets) {
        List<Map<String, String>> values = Arrays.asList(targets).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (values.size() > 0) {
            Map<String, String> merged = new HashMap<>();
            for (Map<String, String> target: values) {
                merged.putAll(target);
            }
            return merged;
        } else {
            return Collections.emptyMap();
        }
    }
}
