package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 06/01/17.
 */
@Getter
@Setter
public class DockerVersion extends DockerDto {

    @JsonProperty("Version")
    private String version;

    @JsonProperty("ApiVersion")
    private String apiVersion;

    @JsonProperty("GoVersion")
    private String goVersion;

    @JsonProperty("Os")
    private String os;

    @JsonProperty("Arch")
    private String arch;

    @JsonProperty("KernelVersion")
    private String kernelVersion;
}
