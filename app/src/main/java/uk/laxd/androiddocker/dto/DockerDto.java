package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 04/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public abstract class DockerDto {

    @JsonProperty
    private String Id;
}
