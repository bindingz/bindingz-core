package io.bindingz.api.client.credentials;

import java.util.Optional;

public interface ApiCredentialsLoader {
    Optional<String> apiKey();
    Optional<String> hostname();
}
