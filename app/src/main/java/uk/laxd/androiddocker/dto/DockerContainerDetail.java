package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lawrence on 05/01/17.
 */
public class DockerContainerDetail extends DockerDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Config")
    private DockerContainerConfig dockerContainerConfig;

    @JsonProperty("Created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdDate;

    @JsonProperty("Mounts")
    private final List<Mount> mounts = new ArrayList<>();

    @JsonProperty("NetworkSettings")
    private NetworkSettings networkSettings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DockerContainerConfig getDockerContainerConfig() {
        return dockerContainerConfig;
    }

    public void setDockerContainerConfig(DockerContainerConfig dockerContainerConfig) {
        this.dockerContainerConfig = dockerContainerConfig;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Mount> getMounts() {
        return mounts;
    }

    public NetworkSettings getNetworkSettings() {
        return networkSettings;
    }

    public void setNetworkSettings(NetworkSettings networkSettings) {
        this.networkSettings = networkSettings;
    }
}
