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

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Ports getPorts() {
        return ports;
    }

    public void setPorts(Ports ports) {
        this.ports = ports;
    }
}
