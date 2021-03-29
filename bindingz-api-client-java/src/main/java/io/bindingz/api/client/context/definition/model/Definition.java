package io.bindingz.api.client.context.definition.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Definition {
    private ProcessConfiguration process;
    private PublishConfiguration publish;
}
