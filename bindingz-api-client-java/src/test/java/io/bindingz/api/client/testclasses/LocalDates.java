package io.bindingz.api.client.testclasses;

import io.bindingz.api.annotations.Contract;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Contract(namespace = "test", contractName = "LocalDates", owner = "bindingz-api-client-java", version = "1")
public class LocalDates {
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
}
