package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

import uk.laxd.androiddocker.PortsDeserialiser;

/**
 * Created by lawrence on 05/02/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PortsDeserialiser.class)
public class Ports {

    private List<PortMapping> portMappings = new ArrayList<>();

    public List<PortMapping> getPortMappings() {
        return portMappings;
    }
}
