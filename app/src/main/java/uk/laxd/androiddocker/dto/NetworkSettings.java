package uk.laxd.androiddocker.dto;

import com.android.databinding.library.baseAdapters.BR;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import uk.laxd.androiddocker.PortsDeserialiser;
import uk.laxd.androiddocker.R;

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
    private PortMapping[] ports = new PortMapping[0];

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

    public List<PortMapping> getPorts() {
        return Arrays.asList(ports);
    }

    public void setPorts(PortMapping[] ports) {
        this.ports = ports;
    }

    public ItemBinding<PortMapping> portsItemBinding = ItemBinding.of(BR.port, R.layout.docker_container_port_mapping_row);

}
