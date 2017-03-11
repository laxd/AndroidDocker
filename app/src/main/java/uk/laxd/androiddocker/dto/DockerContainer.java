package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 04/01/17.
 */
@Getter
@Setter
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names = new String[0];

    @JsonProperty("State")
    private DockerContainerState state;

    @JsonProperty("Image")
    private String image;
}
