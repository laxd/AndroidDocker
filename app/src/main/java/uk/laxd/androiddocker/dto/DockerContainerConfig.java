package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 11/03/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DockerContainerConfig {

    @JsonProperty("Image")
    private String image;

}
