package io.bindingz.api.client.testclasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.bindingz.api.client.jackson.DefaultPublishConfigurationFactory;
import io.bindingz.context.loader.TypeScanner;

import java.util.HashMap;
import java.util.Map;

public class CustomPublishConfigurationFactory extends DefaultPublishConfigurationFactory {

    public ObjectMapper objectMapper(TypeScanner typeScanner) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
