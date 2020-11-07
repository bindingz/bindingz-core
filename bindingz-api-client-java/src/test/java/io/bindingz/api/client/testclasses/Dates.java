package io.bindingz.api.client.testclasses;

import io.bindingz.api.annotations.Contract;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Contract(namespace = "test", contractName = "Dates", owner = "bindingz-api-client-java", version = "1")
public class Dates {
    @NotNull
    private Date date;
}
