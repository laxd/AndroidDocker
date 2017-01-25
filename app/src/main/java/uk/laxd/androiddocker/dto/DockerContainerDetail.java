package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerDetail extends DockerDto {

    @JsonProperty("Name")
    private String name;

    public DockerContainerDetail() {
    }

    public DockerContainerDetail(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
