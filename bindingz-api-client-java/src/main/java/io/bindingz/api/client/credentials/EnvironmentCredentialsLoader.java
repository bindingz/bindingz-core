package io.bindingz.api.client.credentials;

import java.util.Optional;

public class EnvironmentCredentialsLoader implements ApiCredentialsLoader {

    @Override
    public Optional<String> apiKey() {
        return Optional.ofNullable(System.getProperty("BINDINGZ_API_KEY"));
    }

    @Override
    public Optional<String> hostname() {
        return Optional.ofNullable(System.getProperty("BINDINGZ_HOSTNAME"));
    }
}
