package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 06/01/17.
 */

public class DockerVersion extends DockerDto {

    @JsonProperty("Version")
    private String version;

    public String getVersion() {
        return version;
    }
}
