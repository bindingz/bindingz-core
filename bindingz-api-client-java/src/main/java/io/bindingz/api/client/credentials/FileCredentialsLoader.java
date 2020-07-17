package io.bindingz.api.client.credentials;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileCredentialsLoader implements ApiCredentialsLoader {

    private final String location;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public FileCredentialsLoader(String location) {
        this.location = location;
    }

    @Override
    public Optional<String> apiKey() {
        File location = new File(this.location);
        if (location.exists()) {
            try {
                ApiCredentials credentials = OBJECT_MAPPER.readValue(location, ApiCredentials.class);
                return Optional.ofNullable(credentials.getApiKey());
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> hostname() {
        File location = new File(this.location);
        if (location.exists()) {
            try {
                ApiCredentials credentials = OBJECT_MAPPER.readValue(location, ApiCredentials.class);
                return Optional.ofNullable(credentials.getHostname());
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
