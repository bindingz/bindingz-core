package io.bindingz.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bindingz.api.model.ContractDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

public class ContractServiceTest {

    private ObjectMapper mapper;
    private ContractService service;

    @Before
    public void setUp() {
        service = new ContractService(Arrays.asList(this.getClass().getClassLoader()));
        mapper = new ObjectMapper();
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
    public void testLocalDatesWithJacksonConfiguration() throws Exception {
        Assert.assertEquals(content("LocalDatesWithJacksonConfiguration.json"), getContractDto("LocalDatesWithJacksonConfiguration"));
    }

    private String content(String name) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("fixtures/" + name).toURI())));
    }

    private String getContractDto(String name) throws IOException {
        Collection<ContractDto> dtos = service.create("io.bindingz.api.client.testclasses");
        ContractDto result = dtos.stream().filter(dto -> dto.getContractName().equals(name)).findFirst().get();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }
}
