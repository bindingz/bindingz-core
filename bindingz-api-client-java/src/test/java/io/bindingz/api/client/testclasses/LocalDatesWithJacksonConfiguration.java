package io.bindingz.api.client.testclasses;

import io.bindingz.api.annotations.Contract;
import io.bindingz.api.annotations.jackson.JacksonConfiguration;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Contract(namespace = "test", contractName = "LocalDatesWithJacksonConfiguration", owner = "bindingz-api-client-java", version = "1")
@JacksonConfiguration(factory = CustomConfigurationFactory.class)
public class LocalDatesWithJacksonConfiguration {
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
}
