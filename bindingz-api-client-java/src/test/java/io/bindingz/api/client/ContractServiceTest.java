package io.bindingz.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.api.model.ContractDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ContractServiceTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void test() throws Exception {
        ContractService service = new ContractService(new ObjectMapper());
        Collection<ContractDto> dtos = service.create(
                this.getClass().getClassLoader(),
                "io.bindingz.contract.registry.client");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtos));
    }
}
