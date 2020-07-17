package io.bindingz.api.client.credentials;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiCredentials {
    private final String apiKey;
    private final String hostname;

    @JsonCreator
    public ApiCredentials(@JsonProperty("apiKey") String apiKey, @JsonProperty("hostname") String hostname) {
        this.apiKey = apiKey;
        this.hostname = hostname;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getHostname() {
        return hostname;
    }
}
