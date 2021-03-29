package io.bindingz.api.client.testclasses;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Dates {
    @NotNull
    private Date date;
}
