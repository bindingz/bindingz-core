package io.bindingz.api.client.credentials;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ApiCredentialsBuilder {
    private final File fileLocation;
    private String hostname = null;
    private String apiKey = null;

    public ApiCredentialsBuilder() {
        this(Paths.get(System.getProperty("user.home"), ".bindingz", "credentials.json").toFile());
    }

    public ApiCredentialsBuilder(File fileLocation) {
        this.fileLocation = fileLocation;
    }

    public ApiCredentialsBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public ApiCredentialsBuilder hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ApiCredentials build() {
        String apiKey = loaders().stream()
                .map(ApiCredentialsLoader::apiKey)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse(null);
        String hostname = loaders().stream()
                .map(ApiCredentialsLoader::hostname)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse(null);
        return new ApiCredentials(apiKey, hostname);
    }

    private List<ApiCredentialsLoader> loaders() {
        return Arrays.asList(
                new SimpleCredentialsLoader(apiKey, hostname),
                new EnvironmentCredentialsLoader(),
                new FileCredentialsLoader(fileLocation),
                new DefaultCredentialsLoader()
        );
    }
}
