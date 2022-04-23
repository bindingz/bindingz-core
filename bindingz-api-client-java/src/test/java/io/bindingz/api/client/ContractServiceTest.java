package io.bindingz.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.api.client.context.definition.JsonDefinitionReader;
import io.bindingz.api.client.context.definition.model.Definition;
import io.bindingz.api.client.jackson.JacksonContractService;
import io.bindingz.api.model.ContractDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class ContractServiceTest {

    private ObjectMapper mapper;
    private ContractService service;
    private Collection<ContractDto> dtos;

    @Before
    public void setUp() {
        service = new JacksonContractService(new ClassGraphTypeScanner(this.getClass().getClassLoader()));
        mapper = new ObjectMapper();
        Definition definition = new JsonDefinitionReader().read(
                this.getClass()
                        .getClassLoader()
                        .getResource("definition.json").getFile());
        dtos = service.create(definition.getPublish().getContracts());
    }

    @Test
    public void testPrimitives() throws Exception {
        // Notice that primitives will be marked as required
        Assert.assertEquals(content("Primitives.json"), getContractDto("Primitives"));
    }

    @Test
    public void testObjects() throws Exception {
        Assert.assertEquals(content("Objects.json"), getContractDto("Objects"));
    }

    @Test
    public void testLocalDates() throws Exception {
        Assert.assertEquals(content("LocalDates.json"), getContractDto("LocalDates"));
    }

    @Test
    public void testDates() throws Exception {
        Assert.assertEquals(content("Dates.json"), getContractDto("Dates"));
    }

    @Test
    public void testLocalDatesWithCustomConfiguration() throws Exception {
        System.out.println(getContractDto("LocalDatesWithCustomConfiguration"));
        Assert.assertEquals(content("LocalDatesWithCustomConfiguration.json"), getContractDto("LocalDatesWithCustomConfiguration"));
    }

    private String content(String name) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("schemas/" + name).toURI())));
    }

    private String getContractDto(String name) throws IOException {
        ContractDto result = dtos.stream().filter(dto -> dto.getContractName().equals(name)).findFirst().get();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }
}
