package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerDetail extends DockerDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Mounts")
    private List<Mount> mounts;

    @JsonProperty("NetworkSettings")
    private NetworkSettings networkSettings;

    public DockerContainerDetail() {
    }

    public DockerContainerDetail(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Mount> getMounts() {
        return mounts;
    }

    public NetworkSettings getNetworkSettings() {
        return networkSettings;
    }
}
