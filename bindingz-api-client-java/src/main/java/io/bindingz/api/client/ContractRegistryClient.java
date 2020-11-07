/*
 * Copyright (c) 2020 Connor Goulding
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.bindingz.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.api.client.credentials.ApiCredentials;
import io.bindingz.api.client.credentials.ApiCredentialsBuilder;
import io.bindingz.api.model.SourceCodeConfiguration;
import io.bindingz.api.model.ContractDto;
import io.bindingz.api.model.ContractResource;
import io.bindingz.api.model.SourceResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ContractRegistryClient {

    private final String hostname;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public ContractRegistryClient(String hostname, String apiKey, ObjectMapper objectMapper) {
        ApiCredentials credentials = new ApiCredentialsBuilder().apiKey(apiKey).hostname(hostname).build();
        this.hostname = credentials.getHostname();
        this.apiKey = credentials.getApiKey();
        this.objectMapper = objectMapper;
    }

    public ContractResource publishContract(ContractDto contractDto) {
        String url = String.format("%s/api/v1/contracts/namespaces/%s/owners/%s/models/%s?version=%s",
                hostname,
                contractDto.getNamespace(),
                contractDto.getOwner(),
                contractDto.getContractName(),
                contractDto.getVersion());
        try {
            System.out.println(String.format("Bindingz - Requesting %s with %s",
                    url,
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractDto.getSchema())));
            String response = post(url, objectMapper.writeValueAsString(contractDto.getSchema()));
            return objectMapper.readValue(response, ContractResource.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SourceResource generateSources(String namespace,
                                          String owner,
                                          String contractName,
                                          String version,
                                          SourceCodeConfiguration configuration) {
        String url = String.format("%s/api/v1/sources/namespaces/%s/owners/%s/models/%s?version=%s",
                hostname,
                namespace,
                owner,
                contractName,
                version);
        try {
            System.out.println(String.format("Bindingz - Requesting %s with %s",
                    url,
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(configuration)));
            String response = post(url, objectMapper.writeValueAsString(configuration));
            return objectMapper.readValue(response, SourceResource.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String post(String url, String body) throws IOException {
        HttpURLConnection post = (HttpURLConnection) new URL(url).openConnection();
        post.setDoOutput(true);
        post.setRequestProperty("Content-Type", "application/json");
        post.setRequestProperty("X-Api-Key", apiKey);
        post.setRequestMethod("POST");
        post.connect();
        post.getOutputStream().write(body.getBytes("UTF-8"));

        System.out.println("Bindingz Response Code : " + post.getResponseCode());
        return getAsString(post.getInputStream());
    }

    private String getAsString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
    }
}
