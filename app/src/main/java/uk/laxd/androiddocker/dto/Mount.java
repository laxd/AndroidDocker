package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 26/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Mount {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Destination")
    private String destination;
}
