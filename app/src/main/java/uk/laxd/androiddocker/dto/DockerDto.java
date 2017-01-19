package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DockerDto {

    @JsonProperty
    private String Id;

    public String getId() {
        return Id;
    }
}
