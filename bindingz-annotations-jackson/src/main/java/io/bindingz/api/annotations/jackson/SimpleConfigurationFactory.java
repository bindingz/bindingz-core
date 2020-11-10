package io.bindingz.api.annotations.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.Map;

public class SimpleConfigurationFactory implements ConfigurationFactory {

    @Override
    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Override
    public Map<String, String> customTypeMappings() {
        Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put("java.time.LocalDateTime", "datetime-local");
        typeMappings.put("java.time.OffsetDateTime", "datetime");
        typeMappings.put("java.time.LocalDate", "date");
        typeMappings.put("org.joda.time.LocalDate", "date");
        return typeMappings;
    }
}
