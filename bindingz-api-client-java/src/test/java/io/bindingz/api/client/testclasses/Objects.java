package io.bindingz.api.client.testclasses;

import lombok.Data;

import java.math.BigDecimal;

@Data
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
