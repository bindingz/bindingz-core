package io.bindingz.api.client.testclasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.bindingz.api.annotations.jackson.ConfigurationFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomConfigurationFactory implements ConfigurationFactory {

    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public Map<String, String> customTypeMappings() {
        Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put("java.time.LocalDateTime", "datetime-local");
        typeMappings.put("java.time.OffsetDateTime", "datetime");
        typeMappings.put("java.time.LocalDate", "date");
        typeMappings.put("org.joda.time.LocalDate", "date");
        return typeMappings;
    }
}
