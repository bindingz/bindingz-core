package io.bindingz.api.client.testclasses;

import io.bindingz.api.annotations.Contract;
import lombok.Data;

@Data
@Contract(namespace = "test", contractName = "Primitives", owner = "bindingz-api-client-java", version = "1")
public class Primitives {
    private int intValue;
    private char charValue;
    private byte byteValue;
    private long longValue;
    private double doubleValue;
    private short shortValue;
    private float floatValue;
}
