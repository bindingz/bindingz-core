package io.bindingz.api.client.context.definition;

import io.bindingz.api.client.context.definition.model.Definition;

public interface DefinitionReader {
    Definition read(String fileName);
}
