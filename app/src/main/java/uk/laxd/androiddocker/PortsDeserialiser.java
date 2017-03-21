package uk.laxd.androiddocker;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.laxd.androiddocker.dto.PortMapping;

/**
 * Created by lawrence on 26/01/17.
 */

public class PortsDeserialiser extends StdDeserializer<PortMapping[]> {

    public static final String IP_ADDRESS_KEY = "HostIp";
    public static final String PORT_KEY = "HostPort";
    public static final String PORTS_KEY = "Ports";

    protected PortsDeserialiser() {
        super(PortMapping[].class);
    }

    @Override
    public PortMapping[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode ports = p.readValueAsTree();

        if(ports == null) {
            return null;
        }

        Iterator<String> iterator = ports.fieldNames();

        List<PortMapping> portMappings = new ArrayList<>();

        while(iterator.hasNext()) {
            PortMapping portMapping = new PortMapping();
            String source = iterator.next();
            portMapping.setSource(source);

            TreeNode destinations = ports.get(source);

            int destinationCount = destinations.size();

            for(int i=0; i<destinationCount; i++) {
                TreeNode destination = destinations.get(i);
                JsonNode destinationIp = (JsonNode) destination.path(IP_ADDRESS_KEY);

                JsonNode destinationPort = (JsonNode) destination.get(PORT_KEY);
                portMapping.getDestinations().add(destinationIp.asText() + ":" + destinationPort.asText());
            }

            portMappings.add(portMapping);
        }

        return portMappings.toArray(new PortMapping[0]);
    }
}
