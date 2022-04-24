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

package io.bindingz.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SourceCodeConfiguration {

    private String packageName;
    private String className;

    private String sourceCodeProvider = "JSONSCHEMA2POJO";
    private String language = "JAVA";

    private String participantNamespace;
    private String participantName;

    private Map<String, Serializable> providerConfiguration = new HashMap<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSourceCodeProvider() {
        return sourceCodeProvider;
    }

    public void setSourceCodeProvider(String sourceCodeProvider) {
        this.sourceCodeProvider = sourceCodeProvider;
    }

    public Map<String, Serializable> getProviderConfiguration() {
        return providerConfiguration;
    }

    public void setProviderConfiguration(Map<String, Serializable> providerConfiguration) {
        this.providerConfiguration = providerConfiguration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getParticipantNamespace() {
        return participantNamespace;
    }

    public void setParticipantNamespace(String participantNamespace) {
        this.participantNamespace = participantNamespace;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
}
