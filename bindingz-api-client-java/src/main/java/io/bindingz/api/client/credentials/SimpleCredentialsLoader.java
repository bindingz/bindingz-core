package io.bindingz.api.client.credentials;

import java.util.Optional;

public class SimpleCredentialsLoader implements ApiCredentialsLoader {
    private final String apiKey;
    private final String hostname;

    public SimpleCredentialsLoader(String apiKey, String hostname) {
        this.apiKey = apiKey;
        this.hostname = hostname;
    }

    @Override
    public Optional<String> apiKey() {
        return Optional.ofNullable(apiKey);
    }

    @Override
    public Optional<String> hostname() {
        return Optional.ofNullable(hostname);
    }
}
