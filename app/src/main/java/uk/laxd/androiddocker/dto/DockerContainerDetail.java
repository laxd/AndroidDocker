package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by lawrence on 05/01/17.
 */
@Getter
@Setter
public class DockerContainerDetail extends DockerDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Mounts")
    private final List<Mount> mounts = new ArrayList<>();

    @JsonProperty("NetworkSettings")
    private NetworkSettings networkSettings;
}
