package io.bindingz.api.client.testclasses;

import io.bindingz.api.annotations.Contract;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Contract(namespace = "test", contractName = "Objects", owner = "bindingz-api-client-java", version = "1")
public class Objects {
    private String stringValue;
    private Integer integerValue;
    private Long longValue;
    private BigDecimal decimalValue;
    private Float floatValue;
    private Double doubleValue;
    private Character characterValue;
    private Byte byteValue;
}
