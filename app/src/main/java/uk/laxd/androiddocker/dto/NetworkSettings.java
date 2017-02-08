package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import uk.laxd.androiddocker.PortsDeserialiser;

/**
 * Created by lawrence on 26/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkSettings {

    @JsonProperty("Gateway")
    private String gateway;

    @JsonProperty("IPAddress")
    private String ip;

    @JsonProperty("Ports")
    @JsonDeserialize(using = PortsDeserialiser.class)
    private Ports ports;

    public String getGateway() {
        return gateway;
    }

    public String getIp() {
        return ip;
    }

    public Ports getPorts() {
        return ports;
    }
}
