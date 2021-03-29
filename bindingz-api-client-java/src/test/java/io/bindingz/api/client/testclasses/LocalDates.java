package io.bindingz.api.client.testclasses;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class LocalDates {
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
}
