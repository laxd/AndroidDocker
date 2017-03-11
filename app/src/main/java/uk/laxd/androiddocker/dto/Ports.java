package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uk.laxd.androiddocker.PortsDeserialiser;

/**
 * Created by lawrence on 05/02/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PortsDeserialiser.class)
@Getter
public class Ports {
    private final List<PortMapping> portMappings = new ArrayList<>();
}
