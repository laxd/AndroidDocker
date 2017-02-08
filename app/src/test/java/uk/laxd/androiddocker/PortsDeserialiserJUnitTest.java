package uk.laxd.androiddocker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.laxd.androiddocker.dto.DockerContainerDetail;
import uk.laxd.androiddocker.dto.NetworkSettings;
import uk.laxd.androiddocker.dto.PortMapping;
import uk.laxd.androiddocker.dto.Ports;

import static org.junit.Assert.*;

/**
 * Created by lawrence on 26/01/17.
 */
public class PortsDeserialiserJUnitTest {

    private static final String SIMPLE_JSON_PORT_MAPPING = "{\"Ports\": {\n" +
            "            \"9000/tcp\": [\n" +
            "                {\n" +
            "                    \"HostIp\": \"0.0.0.0\",\n" +
            "                    \"HostPort\": \"9000\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }, \"Gateway\":\"123\"" +
            "    }";

    private static final String COMPEX_JSON_PORT_MAPPING = "{\"Ports\": {\n" +
            "    \"6789/tcp\": [\n" +
            "        {\n" +
            "            \"HostIp\": \"0.0.0.0\",\n" +
            "            \"HostPort\": \"2345\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"9000/tcp\": [\n" +
            "        {\n" +
            "            \"HostIp\": \"0.0.0.0\",\n" +
            "            \"HostPort\": \"9001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"HostIp\": \"0.0.0.0\",\n" +
            "            \"HostPort\": \"9000\"\n" +
            "        }\n" +
            "    ]" +
            "}}";

    private String dockerContainerDetailsJson;

    private PortsDeserialiser portsDeserialiser;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        portsDeserialiser = new PortsDeserialiser();

        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Ports.class, portsDeserialiser);
        objectMapper.registerModule(module);

        dockerContainerDetailsJson = IOUtils.toString(this.getClass().getResourceAsStream("/docker_container_detail.json"), "UTF-8");
    }

    @Test
    public void testSourceIsMapped() throws Exception {
        NetworkSettings networkSettings = objectMapper.readValue(SIMPLE_JSON_PORT_MAPPING, NetworkSettings.class);

        assertEquals("9000/tcp", networkSettings.getPorts().getPortMappings().get(0).getSource());
    }

    @Test
    public void testCorrectNumberOfDestinationsReturned() throws Exception {
        NetworkSettings networkSettings = objectMapper.readValue(SIMPLE_JSON_PORT_MAPPING, NetworkSettings.class);

        assertEquals(1, networkSettings.getPorts().getPortMappings().get(0).getDestinations().size());
    }

    @Test
    public void testDestinationIsMapped() throws Exception {
        NetworkSettings networkSettings = objectMapper.readValue(SIMPLE_JSON_PORT_MAPPING, NetworkSettings.class);

        assertEquals("0.0.0.0:9000", networkSettings.getPorts().getPortMappings().get(0).getDestinations().get(0));
    }

    @Test
    public void testComplexJsonSourcesAreMapped() throws Exception {
        NetworkSettings networkSettings = objectMapper.readValue(COMPEX_JSON_PORT_MAPPING, NetworkSettings.class);

        assertEquals(2, networkSettings.getPorts().getPortMappings().size());
    }

    @Test
    public void test() throws Exception {
        NetworkSettings ns = objectMapper.readValue(SIMPLE_JSON_PORT_MAPPING, NetworkSettings.class);

        assertEquals(1, ns.getPorts().getPortMappings().size());
    }

    @Test
    public void testCompleteJsonMapping() throws Exception {
        DockerContainerDetail detail = objectMapper.readValue(dockerContainerDetailsJson, DockerContainerDetail.class);

        assertEquals(2, detail.getNetworkSettings().getPorts().getPortMappings().size());
    }
}